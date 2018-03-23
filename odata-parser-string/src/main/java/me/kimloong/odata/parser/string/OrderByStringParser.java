/*
 *    Copyright 2018 KimLoong
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package me.kimloong.odata.parser.string;

import com.google.common.base.Strings;
import me.kimloong.odata.model.ODataField;
import me.kimloong.odata.parser.FieldResolver;
import org.springframework.data.domain.Sort;

/**
 * 排序解析器
 *
 * @author KimLoong
 */
public class OrderByStringParser {

    private static final int SORT_VALUE_LENGTH = 2;
    private static final int SORT_DEFAULT_DIRECTION_VALUE_LENGTH = 1;

    private FieldResolver fieldResolver;

    public OrderByStringParser(FieldResolver fieldResolver) {
        this.fieldResolver = fieldResolver;
    }

    public Sort parse(Class entityClass, String orderBys) {
        if (Strings.isNullOrEmpty(orderBys)) {
            return null;
        }
        String[] orderByArray = orderBys.split("\\s+,\\s+");
        int length = orderByArray.length;
        Sort.Order[] orders = new Sort.Order[length];
        for (int i = 0; i < length; i++) {
            orders[i] = parseOne(entityClass, orderByArray[i]);
        }
        return new Sort(orders);
    }

    private Sort.Order parseOne(Class entityClass, String orderBy) {
        String[] sortValues = orderBy.split("\\s+");
        String sortField;
        Sort.Direction direction;

        if (sortValues.length == SORT_VALUE_LENGTH) {
            sortField = sortValues[0];
            direction = Sort.Direction.fromStringOrNull(sortValues[1]);
            if (direction == null) {
                throw new IllegalArgumentException("invalid order direction " + sortValues[1]);
            }
        } else if (sortValues.length == SORT_DEFAULT_DIRECTION_VALUE_LENGTH) {
            sortField = sortValues[0];
            direction = Sort.Direction.ASC;
        } else {
            throw new IllegalArgumentException("invalid order string " + orderBy);
        }

        ODataField odataField = fieldResolver.resolve(entityClass, sortField);
        return new Sort.Order(direction, odataField.getName());
    }
}
