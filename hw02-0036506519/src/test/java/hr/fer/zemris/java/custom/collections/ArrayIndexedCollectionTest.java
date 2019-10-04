package hr.fer.zemris.java.custom.collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIndexedCollectionTest {

    public static ArrayIndexedCollection other;

    @BeforeEach
    public void testingData() {
        other = new ArrayIndexedCollection();
        other.add(1);
        other.add("a");
        other.add(2.2);
        other.add("bla");
    }

    @Test
    public void ConstructorLegalArgument() {
        ArrayIndexedCollection col = new ArrayIndexedCollection();

        assertEquals(16, col.getCapacity());
        assertNotNull(col.getElements());
    }

    @Test
    public void Constructor2LegalArgument() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(10);

        assertEquals(10, col.getCapacity());
        assertNotNull(col.getElements());
    }

    @Test
    public void Constructor2BadArgument() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
    }

    @Test
    public void Constructor3BadArgument() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    public void Constructor3LegalArgument() {

        ArrayIndexedCollection col = new ArrayIndexedCollection(other);
        assertEquals(4, col.getCapacity());
        assertEquals(4, col.size());
    }

    @Test
    public void Constructor4BadArgument() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 10));
    }

    @Test
    public void Constructor4InitialCapacityGood() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(other, 10);
        assertEquals(10, col.getCapacity());
        assertEquals(4, col.size());
    }

    @Test
    public void Constructor4InitialCapacityTooSmall() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(other, 3);
        assertEquals(4, col.getCapacity());
        assertEquals(4, col.size());
    }


    @Test
    public void getCapacityTest() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(10);
        assertEquals(10, col.getCapacity());
    }


    @Test
    public void sizeTest() {

        assertEquals(4, other.size());
    }

    @Test
    public void addNullThrows() {
        assertThrows(NullPointerException.class, () -> other.add(null));
    }

    @Test
    public void addInFullCollection() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);
        col.add(1);
        col.add("a");

        col.add("bla");

        assertEquals(4, col.getCapacity());
    }

    @Test
    public void getMethodTooSmallIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> other.get(-1));
    }

    @Test
    public void getMethodTooBigIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> other.get(4));
    }

    @Test
    public void getMethodLegalArgument() {
        assertEquals("a", other.get(1));
    }


    @Test
    public void containsWorksGoodTest() {
        assertTrue(other.contains("a"));
        assertTrue(other.contains(2.2));
        assertFalse(other.contains("ab"));
    }

    @Test
    public void clearTestIfCleared() {
        ArrayIndexedCollection col = new ArrayIndexedCollection();
        col.add(1);
        col.add("a");

        col.clear();

        assertEquals(0, col.size());
        assertThrows(IndexOutOfBoundsException.class, () -> col.get(0));
    }

    @Test
    public void insertForLegalArgument() {
        other.insert("test", 1);
        assertEquals("test", other.get(1));
        assertEquals(1, other.get(0));
        assertEquals("a", other.get(2));
        assertEquals(2.2, other.get(3));
    }

    @Test
    public void insertNegativePositionThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> other.insert("test", -1));
    }

    @Test
    public void insertTooBigPositionThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> other.insert("test", 5));
    }

    @Test
    public void indexOfForNull() {
        assertEquals(-1, other.indexOf(null));
    }

    @Test
    public void indexOfForElementInCollection() {
        assertEquals(2, other.indexOf(2.2));
    }

    @Test
    public void removeForNegativeIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> other.remove(-1));
    }

    @Test
    public void removeForTooBigIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> other.remove(4));
    }

    @Test
    public void removeForLegalIndex() {
        other.remove(2);
        assertEquals("bla", other.get(2));
    }

    @Test
    public void toArrayTest() {
        Object[] array = other.toArray();
        assertEquals(other.size(), array.length);
        assertEquals("bla", array[3]);
    }

    @Test
    public void isEmptyTest() {
        assertFalse(other.isEmpty());
        other.clear();
        assertTrue(other.isEmpty());
    }

    @Test
    public void removeValueTest() {
        assertTrue(other.contains(1));
        other.remove((Object) 1);
        assertFalse(other.contains(1));
    }

    @Test
    public void addAllTest() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(other);
        other.clear();
        assertEquals(0, other.size());

        other.addAll(col);

        assertEquals(4, other.size());
    }
}
