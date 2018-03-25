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

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 联合(逻辑)运算过滤条件
 * 如: 与、或、非
 *
 * @author KimLoong
 */
public class CompoundCondition implements Condition {

    private CompoundOperator operator;

    private Set<Condition> conditions;

    public CompoundCondition(CompoundOperator operator, Condition... conditions) {
        this.operator = operator;

        Set<Condition> conditionSet = new HashSet<>(conditions.length);
        Collections.addAll(conditionSet, conditions);
        this.conditions = Collections.unmodifiableSet(conditionSet);
    }

    @Override
    public CompoundOperator getOperator() {
        return operator;
    }

    public Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public String toString() {
        return StringUtils.join(conditions, " " + operator.name() + " ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompoundCondition that = (CompoundCondition) o;
        return operator == that.operator &&
                Objects.equals(conditions, that.conditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, conditions);
    }
}
