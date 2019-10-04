package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class FieldValueGettersTest {

    private static StudentRecord record;

    @BeforeAll
    public static void setUp() {
        record = new StudentRecord("17", "Mandžukić", "Mario", 5);
    }

    @Test
    public void testFIRST_NAME() {
        assertEquals("Mario", FieldValueGetters.FIRST_NAME.get(record));
        assertNotEquals("Luka", FieldValueGetters.FIRST_NAME.get(record));
    }

    @Test
    public void testLAST_NAME() {
        assertEquals("Mandžukić", FieldValueGetters.LAST_NAME.get(record));
        assertNotEquals("Modrić", FieldValueGetters.LAST_NAME.get(record));
    }

    @Test
    public void testJMBAG_NAME() {
        assertEquals("17", FieldValueGetters.JMBAG.get(record));
        assertNotEquals("10", FieldValueGetters.JMBAG.get(record));
    }
}