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

package me.kimloong.odata.annotation;

import java.lang.annotation.*;

/**
 * 用于ListParam自定义字段映射，一个类允许同时有多具注解
 * 如下会将demoName映射至name字段
 * <pre>
 * <code>@FieldMapping(field="demoName",mapping="name",type=String.class)}
 * public class Demo{
 * ...
 * }</code>
 * </pre>
 *
 * @author KimLoong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(FieldMappings.class)
public @interface FieldMapping {

    /**
     * 需要进行映射的字段名，字段名为内部字段名，
     * 如输入为user_id，json反序化后的字段名为userId,则此处填写userId
     */
    String field();

    /**
     * 映射后的字段名
     * 这里映射后的字段名可以不存在于实体中
     */
    String mapping();

    /**
     * 字段的类型
     */
    Class type();
}
