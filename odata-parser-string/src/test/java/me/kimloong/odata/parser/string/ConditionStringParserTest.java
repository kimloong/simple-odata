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

package me.kimloong.odata.parser.string;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import me.kimloong.odata.model.ComparisonCondition;
import me.kimloong.odata.model.ComparisonOperator;
import me.kimloong.odata.model.Condition;
import me.kimloong.odata.model.OQueries;
import me.kimloong.odata.parser.CaseFormatFieldNameConverter;
import me.kimloong.odata.parser.ConditionParser;
import me.kimloong.odata.parser.FieldNameConverter;
import me.kimloong.odata.parser.FieldResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * ConditionStringParser Test
 *
 * @author KimLoong
 */
@RunWith(JUnitParamsRunner.class)
public class ConditionStringParserTest {

    private ConditionParser parser;

    @Before
    public void before() {
        FieldNameConverter fieldNameConverter = new CaseFormatFieldNameConverter();
        FieldResolver fieldResolver = new FieldResolver(fieldNameConverter);
        parser = new ConditionStringParser(fieldResolver);
    }

    @Test
    public void parseShouldAllowNullFilter() {
        Condition expected = Condition.EMPTY;

        Condition actual = parser.parse(Foo.class, null);

        assertEquals(expected, actual);
    }

    @Parameters({"", " "})
    @Test
    public void parseShouldAllowEmptyFilter(String filter) {
        Condition expected = Condition.EMPTY;

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsPrimitiveBooleanType() {
        Condition expected = new ComparisonCondition("primitiveBoolean", ComparisonOperator.EQ, true);
        String filter = "primitiveBoolean eq true";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsBoxBooleanType() {
        Condition expected = new ComparisonCondition("boxBoolean", ComparisonOperator.EQ, Boolean.TRUE);
        String filter = "boxBoolean eq true";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsPrimitiveIntType() {
        Condition expected = new ComparisonCondition("primitiveInt", ComparisonOperator.EQ, 1);
        String filter = "primitiveInt eq 1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsBoxIntType() {
        Condition expected = new ComparisonCondition("boxInt", ComparisonOperator.EQ, 1);
        String filter = "boxInt eq 1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsPrimitiveFloatType() {
        Condition expected = new ComparisonCondition("primitiveFloat", ComparisonOperator.EQ, 0.1f);
        String filter = "primitiveFloat eq 0.1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsBoxFloatType() {
        Condition expected = new ComparisonCondition("boxFloat", ComparisonOperator.EQ, 0.1f);
        String filter = "boxFloat eq 0.1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsPrimitiveDoubleType() {
        Condition expected = new ComparisonCondition("primitiveDouble", ComparisonOperator.EQ, 0.1d);
        String filter = "primitiveDouble eq 0.1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsBoxDoubleType() {
        Condition expected = new ComparisonCondition("boxLong", ComparisonOperator.EQ, 1L);
        String filter = "boxLong eq 1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsBoxLongType() {
        Condition expected = new ComparisonCondition("boxLong", ComparisonOperator.EQ, 1L);
        String filter = "boxLong eq 1";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsStringType() {
        Condition expected = new ComparisonCondition("string", ComparisonOperator.EQ, "hello world");
        String filter = "string eq 'hello world'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsDateTypeAndValueIsStringFormat() {
        Date date = new Date(1514736000000L);
        Condition expected = new ComparisonCondition("date", ComparisonOperator.EQ, date);
        String filter = "date eq '2018-01-01T00:00:00+08:00'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsDateTypeAndValueIsTimestamp() {
        Date date = new Date(1514736000000L);
        Condition expected = new ComparisonCondition("date", ComparisonOperator.EQ, date);
        String filter = "date eq 1514736000000";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsEnumTypeAndValueIsString() {
        Condition expected = new ComparisonCondition("enumField", ComparisonOperator.EQ, Foo.Type.A);
        String filter = "enumField eq 'A'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowFieldValueIsEnumTypeAndValueIsOrigin() {
        Condition expected = new ComparisonCondition("enumField", ComparisonOperator.EQ, Foo.Type.A);
        String filter = "enumField eq 0";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowExistedField() {
        Condition expected = new ComparisonCondition("mapping", ComparisonOperator.EQ, "hello world");
        String filter = "mapping eq 'hello world'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowMappingOnExistedField() {
        Condition expected = new ComparisonCondition("mapping", ComparisonOperator.EQ, "hello world");
        String filter = "field eq 'hello world'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowMappingOnNotExistedFieldButWithDeclaration() {
        Condition expected = new ComparisonCondition("notInEntityMapping", ComparisonOperator.EQ, "hello world");
        String filter = "notInEntityField eq 'hello world'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseShouldNotAllowMappingOnNotExistedFieldAndWithoutDeclaration() {
        String filter = "notExistedField eq 'hello world'";

        parser.parse(Foo.class, filter);

        fail();
    }

    public void parseShouldAllowSuperExistedField() {
        Condition expected = new ComparisonCondition("baseMapping", ComparisonOperator.EQ, 0);
        String filter = "baseMapping eq 0";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowSuperMappingOnExistedField() {
        Condition expected = new ComparisonCondition("baseMapping", ComparisonOperator.EQ, 0);
        String filter = "baseField eq 0";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowSuperMappingOnNotExistedFieldButWithDeclaration() {
        Condition expected = new ComparisonCondition("notInBaseEntityMapping", ComparisonOperator.EQ, "hello world");
        String filter = "notInBaseEntityField eq 'hello world'";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowSimpleAndCompound() {
        Condition expected = OQueries.and(
                new ComparisonCondition("primitiveInt", ComparisonOperator.GT, 0),
                new ComparisonCondition("primitiveInt", ComparisonOperator.LT, 10));
        String filter = "primitiveInt gt 0 and primitiveInt lt 10";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowSimpleOrCompound() {
        Condition expected = OQueries.or(
                new ComparisonCondition("primitiveInt", ComparisonOperator.LT, 0),
                new ComparisonCondition("primitiveInt", ComparisonOperator.GT, 10));
        String filter = "primitiveInt lt 0 or primitiveInt gt 10";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }


    @Test
    public void parseShouldAllowSimpleOrAndCompound() {
        Condition expected = OQueries.or(
                new ComparisonCondition("primitiveInt", ComparisonOperator.LT, 0),
                OQueries.and(
                        new ComparisonCondition("primitiveInt", ComparisonOperator.GT, 10),
                        new ComparisonCondition("primitiveInt", ComparisonOperator.LT, 20)
                )
        );
        String filter = "primitiveInt lt 0 or primitiveInt gt 10 and primitiveInt lt 20";

        Condition actual = parser.parse(Foo.class, filter);

        assertEquals(expected, actual);
    }
}