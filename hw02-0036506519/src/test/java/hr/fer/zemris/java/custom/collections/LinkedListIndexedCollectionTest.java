package hr.fer.zemris.java.custom.collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LinkedListIndexedCollectionTest {

    LinkedListIndexedCollection testingCol = new LinkedListIndexedCollection();

    @BeforeEach
    public void createTestingCollection() {

        testingCol.add("a");
        testingCol.add(2.2);
        testingCol.add("bla");
        testingCol.add(100);
        testingCol.add("java");
    }

    @Test
    public void ConstructorTest() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();
        assertNull(col.getFirst());
        assertNull(col.getLast());
        assertEquals(0, col.size());
    }

    @Test
    public void otherCollectionConstructorTest() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection(testingCol);
        assertEquals(5, col.size());
        assertEquals(true, col.contains(100));
    }

    @Test
    public void addNullValueThrowsException() {
        assertThrows(NullPointerException.class, () -> testingCol.add(null));
    }

    @Test
    public void addContainsNewElement() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();
        col.add("test");
        assertEquals(true, col.contains("test"));
    }

    @Test
    public void addChangesSize() {
        assertEquals(5, testingCol.size());
        testingCol.add("test");
        assertEquals(6, testingCol.size());
    }

    @Test
    public void getForTooBigIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testingCol.get(testingCol.size()));
    }

    @Test
    public void getForTooSmallIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testingCol.get(-1));
    }

    @Test
    public void getReturnsGoodElement() {
        assertEquals(2.2, testingCol.get(1));
    }

    @Test
    public void clearResetsAll() {
        testingCol.clear();

        assertNull(testingCol.getFirst());
        assertNull(testingCol.getLast());
        assertEquals(0, testingCol.size());
    }

    @Test
    public void insertTooBigPositionThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testingCol.insert("test", testingCol.size() + 1));
    }

    @Test
    public void insertTooSmallPositionThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testingCol.insert("test", -1));
    }

    @Test
    public void insertNullThrowsException() {
        assertThrows(NullPointerException.class, () -> testingCol.insert(null, 0));
    }

    @Test
    public void insertWorksGoodTest() {
        testingCol.insert("test", 0);
        assertEquals("test", testingCol.get(0));
        assertEquals("java", testingCol.get(5));
        assertEquals(6, testingCol.size());
    }

    @Test
    public void indexOfReturnsIndex() {
        assertEquals(3, testingCol.indexOf(100));
    }

    @Test
    public void indexOfIfNotContains() {
        assertEquals(-1, testingCol.indexOf("test"));
    }

    @Test
    public void removeForTooBigIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testingCol.remove(testingCol.size()));
    }

    @Test
    public void removeForTooSmallIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> testingCol.remove(-1));
    }

    @Test
    public void removeWorksGoodTest() {
        assertEquals(5, testingCol.size());
        testingCol.remove(0);
        assertEquals(2.2, testingCol.get(0));
        assertEquals(4, testingCol.size());
    }

    @Test
    public void sizeTest() {
        assertEquals(5, testingCol.size());
    }


    @Test
    public void containsWorksGoodTest() {
        assertTrue(testingCol.contains("java"));
        assertFalse(testingCol.contains("JS"));
    }


    @Test
    public void removesGivenElement() {
        assertTrue(testingCol.contains("java"));
        testingCol.remove("java");
        assertFalse(testingCol.contains("java"));
    }

    @Test
    public void toArrayTest() {
        Object[] array = testingCol.toArray();
        assertEquals(testingCol.size(), array.length);
        assertEquals("bla", array[2]);
    }


    @Test
    public void isEmptyTest() {
        assertFalse(testingCol.isEmpty());
        testingCol.clear();
        assertTrue(testingCol.isEmpty());
    }

    @Test
    public void addAllTest() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection(testingCol);
        testingCol.clear();
        assertEquals(0, testingCol.size());

        testingCol.addAll(col);

        assertEquals(5, testingCol.size());
    }
}
