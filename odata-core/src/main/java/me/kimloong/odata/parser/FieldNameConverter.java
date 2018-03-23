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

/**
 * 字段名转换器
 *
 * @author KimLoong
 */
public interface FieldNameConverter {

    /**
     * 将外部字段名转换为内部字段名
     *
     * @param outerFieldName 外部字段名
     * @return 内部字段名
     */
    String convert(String outerFieldName);
}
