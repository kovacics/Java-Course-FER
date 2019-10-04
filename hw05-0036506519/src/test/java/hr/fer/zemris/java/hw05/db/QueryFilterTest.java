package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class QueryFilterTest {

    private static List<ConditionalExpression> list;
    private static QueryFilter filter;
    private static StudentRecord modric1;
    private static StudentRecord modric2;

    @BeforeAll
    static void setUp() {
        list = new ArrayList<>();
        list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME,
                "Modrić", ComparisonOperators.EQUALS));
        list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME,
                "Lu*a", ComparisonOperators.LIKE));

        modric1 = new StudentRecord("10", "Modrić", "Luka", 5);
        modric2 = new StudentRecord("10", "Modrić", "Vanja", 5);

        filter = new QueryFilter(list);
    }

    @Test
    void constructorTest() {
        assertNotNull(filter);
    }

    @Test
    void acceptsTest() {
        assertTrue(filter.accepts(modric1));
        assertFalse(filter.accepts(modric2));

        list = list.subList(0, 1);
        filter = new QueryFilter(list);
        assertTrue(filter.accepts(modric2));
    }
}