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

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;

/**
 * 字符格式字段名转换器
 *
 * @author KimLoong
 */
public class CaseFormatFieldNameConverter implements FieldNameConverter {

    private final CaseFormat innerCaseFormat;

    private final CaseFormat outerCaseFormat;

    private Converter<String, String> caseFormatConverter;

    public CaseFormatFieldNameConverter(CaseFormat innerCaseFormat, CaseFormat outerCaseFormat) {
        this.innerCaseFormat = innerCaseFormat;
        this.outerCaseFormat = outerCaseFormat;
        caseFormatConverter = outerCaseFormat.converterTo(innerCaseFormat);
    }

    public CaseFormatFieldNameConverter() {
        this(CaseFormat.LOWER_CAMEL, CaseFormat.LOWER_CAMEL);
    }

    @Override
    public String convert(String outerFieldName) {
        return caseFormatConverter.convert(outerFieldName);
    }

    public CaseFormat getInnerCaseFormat() {
        return innerCaseFormat;
    }

    public CaseFormat getOuterCaseFormat() {
        return outerCaseFormat;
    }
}
