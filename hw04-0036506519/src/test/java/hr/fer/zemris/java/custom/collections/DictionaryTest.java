package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DictionaryTest {

    @Test
    public void constructorTest() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();
        assertEquals(16, dictionary.getCapacity());
    }

    @Test
    public void testIsEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();
        assertTrue(dictionary.isEmpty());

        dictionary.put("bla", 2);
        assertFalse(dictionary.isEmpty());
    }

    @Test
    public void sizeTest() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();
        assertEquals(0, dictionary.size());

        dictionary.put("bla", 2);
        assertEquals(1, dictionary.size());

        dictionary.put("bla bla ", 3);
        assertEquals(2, dictionary.size());
    }

    @Test
    public void clearTest() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(2, 4);
        dictionary.put(3, 6);
        dictionary.put(4, 8);
        dictionary.put(5, 10);
        assertEquals(4, dictionary.size());
        dictionary.clear();
        assertTrue(dictionary.isEmpty());
        assertEquals(0, dictionary.size());
    }

    @Test
    public void putTest() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(1, 2);
        dictionary.put(20, 22);
        assertEquals(2, dictionary.size());
        assertEquals(2, dictionary.get(1));
        assertEquals(22, dictionary.get(20));

        dictionary.put(20, 21);
        assertEquals(21, dictionary.get(20));
    }

    @Test
    public void getTestThrowForNullKey() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        assertThrows(NullPointerException.class, () -> dictionary.get(null));
    }

    @Test
    public void getTest() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(10, 100);
        dictionary.put(20, 200);
        dictionary.put(30, 300);
        assertEquals(200, dictionary.get(20));

        dictionary.put(20, 220);
        assertEquals(220, dictionary.get(20));
    }
}
