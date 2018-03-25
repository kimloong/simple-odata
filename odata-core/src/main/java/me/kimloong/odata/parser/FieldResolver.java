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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import me.kimloong.odata.annotation.FieldMapping;
import me.kimloong.odata.model.ODataField;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * 字段名称解析器
 *
 * @author KimLoong
 */
public class FieldResolver {

    private final LoadingCache<Pair<Class, String>, ODataField> fieldNameCache;

    public FieldResolver(FieldNameConverter fieldNameConverter) {
        fieldNameCache = CacheBuilder.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)
                .build(new FieldCacheLoader(fieldNameConverter));
    }

    /**
     * 解析外部字段对应的内部字段信息
     *
     * @param entityClass    字段对应的实体类
     * @param outerFieldName 外部字段名
     * @return 内部字段信息
     */
    public ODataField resolve(Class entityClass, String outerFieldName) {
        try {
            return fieldNameCache.getUnchecked(Pair.of(entityClass, outerFieldName));
        } catch (UncheckedExecutionException exception) {
            throw (RuntimeException) exception.getCause();
        }
    }

    private static class FieldCacheLoader extends CacheLoader<Pair<Class, String>, ODataField> {

        private final FieldNameConverter fieldNameConverter;

        public FieldCacheLoader(FieldNameConverter fieldNameConverter) {
            this.fieldNameConverter = fieldNameConverter;
        }

        @Override
        public ODataField load(Pair<Class, String> key) throws Exception {
            Class entityClass = key.getLeft();
            String outerFieldName = key.getRight();
            String innerFieldName = fieldNameConverter.convert(outerFieldName);
            return innerResolve(entityClass, outerFieldName, innerFieldName);
        }

        private ODataField innerResolve(Class entityClass, String outerFieldName, String innerFieldName) {

            if (Object.class == entityClass) {
                throw new IllegalArgumentException("field not found:" + outerFieldName);
            }

            ODataField odataField = getMappingFieldName(entityClass, innerFieldName);
            if (null != odataField) {
                return odataField;
            }

            Field field = FieldUtils.getField(entityClass, innerFieldName, true);

            if (null == field) {
                return innerResolve(entityClass.getSuperclass(), outerFieldName, innerFieldName);
            }
            return new ODataField(field.getName(), field.getType());
        }

        private ODataField getMappingFieldName(Class entityClass, String innerFieldName) {
            FieldMapping[] fieldMappings = (FieldMapping[]) entityClass.getAnnotationsByType(FieldMapping.class);
            if (null == fieldMappings) {
                return null;
            }
            for (FieldMapping fieldMapping : fieldMappings) {
                if (innerFieldName.equals(fieldMapping.field())) {
                    return new ODataField(fieldMapping.mapping(), fieldMapping.type());
                }
            }
            return null;
        }

    }
}
