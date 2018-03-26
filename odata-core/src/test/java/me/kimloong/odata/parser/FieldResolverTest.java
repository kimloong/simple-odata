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


import me.kimloong.odata.model.ODataField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

/**
 * FieldResolver Test
 *
 * @author Zhang JinLong(150429)
 */
@RunWith(MockitoJUnitRunner.class)
public class FieldResolverTest {

    @Mock
    private FieldNameConverter fieldNameConverter;

    @InjectMocks
    private FieldResolver fieldResolver;

    @Before
    public void before() {
        doAnswer(invocationOnMock -> invocationOnMock.<String>getArgument(0))
                .when(fieldNameConverter).convert(anyString());
    }

    @Test
    public void parseShouldAllowExistedField() {
        ODataField expected = new ODataField("mapping", String.class);

        ODataField actual = fieldResolver.resolve(Foo.class, "mapping");

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowMappingOnExistedField() {
        ODataField expected = new ODataField("mapping", String.class);

        ODataField actual = fieldResolver.resolve(Foo.class, "field");

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowMappingOnNotExistedFieldButWithDeclaration() {
        ODataField expected = new ODataField("notInEntityMapping", String.class);

        ODataField actual = fieldResolver.resolve(Foo.class, "notInEntityField");

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseShouldNotAllowMappingOnNotExistedFieldAndWithoutDeclaration() {

        fieldResolver.resolve(Foo.class, "notExistedField");

        fail();
    }

    public void parseShouldAllowSuperExistedField() {
        ODataField expected = new ODataField("baseMapping", String.class);

        ODataField actual = fieldResolver.resolve(Foo.class, "baseMapping");

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowSuperMappingOnExistedField() {
        ODataField expected = new ODataField("baseMapping", Integer.class);

        ODataField actual = fieldResolver.resolve(Foo.class, "baseField");

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowSuperMappingOnNotExistedFieldButWithDeclaration() {
        ODataField expected = new ODataField("notInBaseEntityMapping", String.class);

        ODataField actual = fieldResolver.resolve(Foo.class, "notInBaseEntityField");

        assertEquals(expected, actual);
    }
}