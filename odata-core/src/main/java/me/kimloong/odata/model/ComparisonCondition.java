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

import java.util.Objects;

/**
 * 关系(比较)运算过滤条件
 * 如: 大于、小于
 *
 * @author KimLoong
 */
public class ComparisonCondition implements Condition {

    private String field;

    private ComparisonOperator operator;

    private Object value;

    private Class valueType;

    public ComparisonCondition(String field, ComparisonOperator operator, Object value) {
        this(field, operator, value, value.getClass());
    }

    public ComparisonCondition(String field, ComparisonOperator operator, Object value, Class valueType) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return field + " " + operator + " " + value;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || getClass() != that.getClass()) {
            return false;
        }
        ComparisonCondition condition = (ComparisonCondition) that;
        return Objects.equals(field, condition.field) &&
                operator == condition.operator &&
                Objects.equals(value, condition.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, operator, value);
    }

    public String getField() {
        return field;
    }

    @Override
    public ComparisonOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public Class getValueType() {
        return valueType;
    }
}
