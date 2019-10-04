package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw05.db.ComparisonOperators.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparisonOperatorsTest {

    @Test
    public void testLESS() {
        assertTrue(
                LESS.satisfied("123", "124") &&
                        LESS.satisfied("ana", "ivan") &&
                        LESS.satisfied("Test01", "test01")
        );
        assertFalse(LESS.satisfied("100", "000"));
    }

    @Test
    public void testLESS_OR_EQUALS() {
        assertTrue(
                LESS_OR_EQUALS.satisfied("baba", "deda") &&
                        LESS_OR_EQUALS.satisfied("bla", "bla") &&
                        LESS_OR_EQUALS.satisfied("123abcžčć.*", "123abcžčć.*")
        );
        assertFalse(LESS_OR_EQUALS.satisfied("2", "1"));
    }


    @Test
    public void testGREATER() {
        assertTrue(
                GREATER.satisfied("124", "123") &&
                        GREATER.satisfied("ivan", "ana") &&
                        GREATER.satisfied("test01", "Test01")
        );
        assertFalse(GREATER.satisfied("cro", "cro"));
    }

    @Test
    public void testGREATER_OR_EQUALS() {
        assertTrue(
                GREATER_OR_EQUALS.satisfied("deda", "baba") &&
                        GREATER_OR_EQUALS.satisfied("bla", "bla") &&
                        GREATER_OR_EQUALS.satisfied("123456789", "123456789")
        );
        assertFalse(GREATER_OR_EQUALS.satisfied("ivan", "marija"));
    }

    @Test
    public void testEQUALS() {
        assertTrue(
                EQUALS.satisfied("123", "123") &&
                        EQUALS.satisfied("Java", "Java")
        );
        assertFalse(EQUALS.satisfied("da", "ne"));
    }

    @Test
    public void testNOT_EQUALS() {
        assertTrue(
                NOT_EQUALS.satisfied("Java", "Javascript") &&
                        NOT_EQUALS.satisfied("FER", "TVZ")
        );
        assertFalse(NOT_EQUALS.satisfied("isto", "isto"));
    }

    @Test
    public void testLIKE() {
        assertTrue(LIKE.satisfied("Marko", "M*"));
        assertTrue(LIKE.satisfied("Marko", "*o"));
        assertTrue(LIKE.satisfied("Marko", "M*ko"));
        assertTrue(LIKE.satisfied("AABAA", "AA*AA"));
        assertTrue(LIKE.satisfied("AAAA", "AA*AA"));
        assertTrue(LIKE.satisfied("Test", "Te*st"));

        assertFalse(LIKE.satisfied("Škafiškafnjak", "škaf*fiškavnjak") &&
                LIKE.satisfied("testTooMuch", "*test*") &&
                LIKE.satisfied("AAA", "AA*AA"));
    }
}