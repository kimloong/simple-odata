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

import com.google.common.primitives.Primitives;
import me.kimloong.odata.model.*;
import me.kimloong.odata.parser.ConditionParser;
import me.kimloong.odata.parser.FieldResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * 条件解析器
 *
 * @author KimLoong
 */
public class ConditionStringParser implements ConditionParser {

    private static final int FILTER_VALUE_LENGTH = 3;

    /**
     * 字段解析器
     */
    private FieldResolver fieldResolver;

    /**
     * 日期格式
     */
    private FastDateFormat dateFormat = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT;

    /**
     * 是否支持使用
     */
    private boolean supportTimestamp = true;

    private boolean supportStringEnum = true;

    private boolean supportOriginEnum = true;

    public ConditionStringParser(
            FieldResolver fieldResolver) {
        this.fieldResolver = fieldResolver;
    }

    public ConditionStringParser(
            FieldResolver fieldResolver,
            FastDateFormat dateFormat,
            boolean supportTimestamp,
            boolean supportStringEnum,
            boolean supportOriginEnum) {
        this(fieldResolver);
        this.dateFormat = dateFormat;
        this.supportTimestamp = dateFormat == null || supportTimestamp;
        this.supportStringEnum = supportStringEnum;
        this.supportOriginEnum = (!supportStringEnum) || supportOriginEnum;
    }

    @Override
    public Condition parse(Class entityClass, String filters) {
        if (StringUtils.isBlank(filters)) {
            return Condition.EMPTY;
        }
        return parseOr(entityClass, filters);
    }

    private Condition parseOr(Class entityClass, String filters) {
        String[] filterArray = filters.split("\\s+or\\s+");

        int filterCount = filterArray.length;
        Condition[] conditions = new Condition[filterCount];
        for (int i = 0; i < filterCount; i++) {
            conditions[i] = parseAnd(entityClass, filterArray[i]);
        }
        return OQueries.or(conditions);
    }

    private Condition parseAnd(Class entityClass, String filters) {
        String[] filterArray = filters.split("\\s+and\\s+");

        int filterCount = filterArray.length;
        Condition[] conditions = new Condition[filterCount];
        for (int i = 0; i < filterCount; i++) {
            conditions[i] = parseOne(entityClass, filterArray[i]);
        }
        return OQueries.and(conditions);
    }


    private Condition parseOne(Class entityClass, String filter) {
        return parseComparison(entityClass, filter);
    }

    private Condition parseComparison(Class entityClass, String filter) {
        String[] filterValues = filter.split("\\s+", FILTER_VALUE_LENGTH);
        if (filterValues.length != FILTER_VALUE_LENGTH) {
            throw new IllegalArgumentException("invalid filter string:" + filter);
        }

        ComparisonOperator operator = ComparisonOperator.valueOf(filterValues[1].toUpperCase());

        String outerFieldName = filterValues[0];
        ODataField odataField = fieldResolver.resolve(entityClass, outerFieldName);
        String fieldName = odataField.getName();
        Class fieldType = odataField.getType();
        Object fieldValue = getValue(operator, filterValues[2], fieldType);
        return new ComparisonCondition(fieldName, operator, fieldValue, fieldType);
    }

    private Object getValue(Operator operator, String filterValue, Class fieldType) {
        Object result = null;
        Class fieldWrapType = Primitives.wrap(fieldType);
        if (String.class == fieldWrapType) {
            result = StringUtils.unwrap(filterValue, STRING_TYPE_WRAP_CHAR);
        } else if (Integer.class == fieldWrapType) {
            result = Integer.parseInt(filterValue);
        } else if (Boolean.class == fieldWrapType) {
            result = Boolean.parseBoolean(filterValue);
        } else if (Long.class == fieldWrapType) {
            result = Long.parseLong(filterValue);
        } else if (Date.class.isAssignableFrom(fieldWrapType)) {
            result = parseDateValue(filterValue);
        } else if (fieldWrapType.isEnum()) {
            result = parseEnumValue(filterValue, fieldWrapType);
        } else if (Float.class == fieldWrapType) {
            result = Float.parseFloat(filterValue);
        } else if (Double.class == fieldWrapType) {
            result = Double.parseDouble(filterValue);
        } else if (Short.class == fieldWrapType) {
            result = Short.parseShort(filterValue);
        } else if (Byte.class == fieldWrapType) {
            result = Byte.parseByte(filterValue);
        }
        return result;
    }

    private Object parseEnumValue(String filterValue, Class fieldType) {
        if (supportOriginEnum && NumberUtils.isDigits(filterValue)) {
            return fieldType.getEnumConstants()[Integer.parseInt(filterValue)];
        }
        return Enum.valueOf(fieldType, StringUtils.unwrap(filterValue, STRING_TYPE_WRAP_CHAR));
    }

    private Date parseDateValue(String filterValue) {
        try {
            if (supportTimestamp && NumberUtils.isDigits(filterValue)) {
                return new Date(Long.parseLong(filterValue));
            }
            if (null == dateFormat) {
                throw new IllegalArgumentException("can't parse date from "
                        + filterValue);
            }
            return dateFormat.parse(StringUtils.unwrap(filterValue, STRING_TYPE_WRAP_CHAR));
        } catch (ParseException e) {
            throw new IllegalArgumentException("can't parse date from "
                    + filterValue, e);
        }
    }
}
