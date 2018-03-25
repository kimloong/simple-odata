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
        Sort expected = new Sort(Sort.Direction.ASC, "mapping");
        String orderBy = "mapping asc";

        Sort actual = parser.parse(Foo.class, orderBy);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowAbridge() {
        Sort expected = new Sort(Sort.Direction.ASC, "mapping");
        String orderBy = "mapping";

        Sort actual = parser.parse(Foo.class, orderBy);

        assertEquals(expected, actual);
    }

    @Test
    public void parseShouldAllowCompound() {
        Sort expected = new Sort(
                new Sort.Order(Sort.Direction.ASC, "field1"),
                new Sort.Order(Sort.Direction.DESC, "field2"));
        String orderBys = "field1 asc, field2 desc";

        Sort actual = parser.parse(Foo.class, orderBys);

        assertEquals(expected, actual);
    }
}