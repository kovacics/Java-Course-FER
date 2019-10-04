package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {


    SimpleHashtable<Integer, Integer> testTable;

    @BeforeEach
    public void makeTestingTable() {
        testTable = new SimpleHashtable<>(8);
        testTable.put(0, 1);
        testTable.put(1, 2);
        testTable.put(2, 4);
        testTable.put(3, 8);
        testTable.put(4, 16);

    }


    @Test
    public void defaultConstructorTest() {
        var table = new SimpleHashtable<>();
        assertNotNull(table);
        assertEquals(16, table.getCapacity());
    }

    @Test
    public void capacityConstructorTest() {
        var table = new SimpleHashtable<>(1);
        assertNotNull(table);
        assertEquals(1, table.getCapacity());

        table = new SimpleHashtable<>(2);
        assertNotNull(table);
        assertEquals(2, table.getCapacity());

        table = new SimpleHashtable<>(129);
        assertNotNull(table);
        assertEquals(256, table.getCapacity());

        table = new SimpleHashtable<>(64);
        assertNotNull(table);
        assertEquals(64, table.getCapacity());
    }

    @Test
    public void capacityConstructorTestThrowsForNegative() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(-1));
    }

    @Test
    public void getCapacityTest() {
        assertEquals(8, testTable.getCapacity());
    }

    @Test
    public void putTestThrows() {
        assertThrows(NullPointerException.class, () -> testTable.put(null, 100));
    }

    @Test
    public void putTest() {
        assertFalse(testTable.containsKey(5));
        assertEquals(testTable.size(), 5);

        testTable.put(5, 32);
        assertTrue(testTable.containsKey(5));
        assertEquals(6, testTable.size());

        testTable.put(5, null);
        assertTrue(testTable.containsKey(5));
        assertEquals(6, testTable.size());
    }

    @Test
    public void putForTooMuchLoad() {
        // current load 5/8 = 0,625
        assertEquals(8, testTable.getCapacity());

        testTable.put(5, 32);  // load = 0,75
        assertEquals(16, testTable.getCapacity());  // capacity doubled
    }

    @Test
    public void getTestThrowForNull() {
        assertThrows(NullPointerException.class, () -> testTable.get(null));
    }

    @Test
    public void getTest() {
        assertEquals(16, testTable.get(4));

        assertNull(testTable.get(5));
        testTable.put(5, 32);
        assertEquals(32, testTable.get(5));

        testTable.put(5, 100);
        assertEquals(100, testTable.get(5));
    }

    @Test
    public void sizeTest() {
        assertEquals(5, testTable.size());
        testTable.put(5, 32);
        assertEquals(6, testTable.size());
        testTable.remove(5);
        assertEquals(5, testTable.size());
    }

    @Test
    public void containsKeyTestThrows() {
        assertThrows(NullPointerException.class, () -> testTable.containsKey(null));
    }

    @Test
    public void containsKeyTest() {
        assertTrue(testTable.containsKey(2));
        assertFalse(testTable.containsKey(5));

        testTable.put(5, 32);
        assertTrue(testTable.containsKey(5));

        testTable.remove(5);
        assertFalse(testTable.containsKey(5));
    }

    @Test
    public void containsValueTest() {
        assertTrue(testTable.containsValue(8));
        assertFalse(testTable.containsValue(32));

        testTable.put(5, 32);
        assertTrue(testTable.containsValue(32));
        assertFalse(testTable.containsValue(null));

        testTable.put(5, null);
        assertTrue(testTable.containsValue(null));
        assertFalse(testTable.containsValue(32));
    }

    @Test
    public void removeThrowsForNull() {
        assertThrows(NullPointerException.class, () -> testTable.remove(null));
    }

    @Test
    public void removeTest() {
        assertTrue(testTable.containsKey(4));
        assertEquals(5, testTable.size());

        testTable.remove(4);
        assertFalse(testTable.containsKey(4));
        assertEquals(4, testTable.size());

        testTable.remove(4);
        testTable.remove(4);
        testTable.remove(4);
        assertEquals(4, testTable.size());
    }

    @Test
    public void isEmptyTest() {
        assertFalse(testTable.isEmpty());
        testTable.clear();
        assertTrue(testTable.isEmpty());
    }


}
