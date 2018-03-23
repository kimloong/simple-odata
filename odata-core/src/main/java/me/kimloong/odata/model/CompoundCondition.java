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

/**
 * 联合(逻辑)运算过滤条件
 * 如: 与、或、非
 *
 * @author KimLoong
 */
public class CompoundCondition implements Condition {

    private CompoundOperator operator;

    private Condition[] conditions;

    public CompoundCondition(CompoundOperator operator, Condition... conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    @Override
    public CompoundOperator getOperator() {
        return operator;
    }

    public Condition[] getConditions() {
        return conditions;
    }
}
