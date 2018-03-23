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

package me.kimloong.odata.model;

import java.util.Arrays;

/**
 * OData 查询工具类
 *
 * @author KimLoong
 */
public class OQueries {

    private OQueries() {
    }

    public static Condition and(Condition... conditions) {
        return compound(CompoundOperator.AND, conditions);
    }

    public static Condition or(Condition... conditions) {
        return compound(CompoundOperator.OR, conditions);
    }

    public static Condition compound(CompoundOperator operator, Condition... conditions) {
        conditions = Arrays.stream(conditions)
                .filter(e -> e != Condition.EMPTY)
                .toArray(Condition[]::new);
        if (conditions.length == 0) {
            return Condition.EMPTY;
        } else if (conditions.length == 1) {
            return conditions[0];
        } else {
            return new CompoundCondition(operator, conditions);
        }
    }
}
