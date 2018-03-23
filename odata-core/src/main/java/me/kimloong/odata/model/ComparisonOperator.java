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
 * 过滤条件比较运算符
 *
 * @author KimLoong
 */
public enum ComparisonOperator implements Operator {

    /**
     * 相等(equals)
     */
    EQ,

    /**
     * 不相等(not equals)
     */
    NE,

    /**
     * 大于(greater than)
     */
    GT,

    /**
     * 大于等于(greater than or equals)
     */
    GE,

    /**
     * 小于(less than)
     */
    LT,

    /**
     * 小于等于(less than or equals)
     */
    LE;
}
