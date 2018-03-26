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

import me.kimloong.odata.parser.CaseFormatFieldNameConverter;
import me.kimloong.odata.parser.FieldNameConverter;
import me.kimloong.odata.parser.FieldResolver;
import me.kimloong.odata.parser.OrderByParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.assertEquals;

/**
 * OrderByStringParser Test
 *
 * @author KimLoong
 */
public class OrderByStringParserTest {

    private OrderByParser parser;

    @Before
    public void before() {
        FieldNameConverter fieldNameConverter = new CaseFormatFieldNameConverter();
        FieldResolver fieldResolver = new FieldResolver(fieldNameConverter);
        parser = new OrderByStringParser(fieldResolver);
    }

    @Test
    public void parseShouldAllowExistedField() {
        Sort expected = new Sort(Sort.Direction.ASC, "sortField1");
        String orderBy = "sortField1 asc";

        Sort actual = parser.parse(Foo.class, orderBy);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowAbridge() {
        Sort expected = new Sort(Sort.Direction.ASC, "sortField1");
        String orderBy = "sortField1";

        Sort actual = parser.parse(Foo.class, orderBy);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowCompound() {
        Sort expected = new Sort(
                new Sort.Order(Sort.Direction.ASC, "sortField1"),
                new Sort.Order(Sort.Direction.DESC, "sortField2"));
        String orderBys = "sortField1 asc, sortField2 desc";

        Sort actual = parser.parse(Foo.class, orderBys);

        assertEquals(expected, actual);
    }
}