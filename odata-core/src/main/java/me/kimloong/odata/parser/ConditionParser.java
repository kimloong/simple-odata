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

package me.kimloong.odata.parser;

import me.kimloong.odata.model.Condition;

/**
 * 条件解析器接口
 *
 * @author KimLoong
 */
public interface ConditionParser {

    /**
     * 通过实体与filter解析条件
     *
     * @param entityClass 对应用实体
     * @param filters     filter文本
     * @return 条件
     */
    Condition parse(Class entityClass, String filters);
}
