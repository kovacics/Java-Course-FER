package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.parser.QueryParser;
import hr.fer.zemris.java.hw05.db.parser.QueryParserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class QueryParserTest {

    private static QueryParser parser;
    private static String[] tests;

    @BeforeAll
    static void setUp() {
        tests = new String[]{
                "jmbag = \"123\"",
                "jmbg = 123", //throws
                "jmbag = 123", //throws
                "jmbag = \"stodvajstri\"",
                "jmbag LIKE \"1*3\"",
                "jmbag LIKE = \"12*23\"", //throws
                "firstName lastName",//throws
                "lastName < \" Kovač\"",
                "firstName LIKE ", //throws
                "First name = \" Horvat \"" //throws
        };
    }

    @Test
    void isDirectQuery() {
        parser = new QueryParser(tests[0]);
        assertTrue(parser.isDirectQuery());

        parser = new QueryParser(tests[4]);
        assertFalse(parser.isDirectQuery());
    }

    @Test
    void getQueriedJMBAG() {
        parser = new QueryParser(tests[0]);
        assertEquals("123", parser.getQueriedJMBAG());
    }

    @Test
    void getQueriedJMBAGThrowsIfNotDirect() {
        parser = new QueryParser(tests[4]);
        assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
    }

    @Test
    void getQuery() {
        parser = new QueryParser(tests[0]);
        var list = new ArrayList<>();
        list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "123", ComparisonOperators.EQUALS));
        assertEquals(list.size(), parser.getQuery().size());
        assertEquals(list.get(0), parser.getQuery().get(0));
    }

    @Test
    void constructorTest() {
        assertDoesNotThrow(() -> new QueryParser(tests[0]));
        assertDoesNotThrow(() -> new QueryParser(tests[4]));
        assertDoesNotThrow(() -> new QueryParser(tests[7]));
        assertDoesNotThrow(() -> new QueryParser(tests[3]));

        assertThrows(QueryParserException.class, () -> new QueryParser(tests[1]));
        assertThrows(QueryParserException.class, () -> new QueryParser(tests[2]));
        assertThrows(QueryParserException.class, () -> new QueryParser(tests[5]));
        assertThrows(QueryParserException.class, () -> new QueryParser(tests[6]));
        assertThrows(QueryParserException.class, () -> new QueryParser(tests[8]));
        assertThrows(QueryParserException.class, () -> new QueryParser(tests[9]));
    }
}