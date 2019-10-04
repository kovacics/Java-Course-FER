package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Stjepan Kovačić
 */
class ObjectMultistackTest {
    ObjectMultistack multistack;

    @BeforeEach
    void setAll() {
        multistack = new ObjectMultistack();
    }

    @Test
    void push() {
        multistack.push("test", new ValueWrapper(5));
        assertEquals(Integer.valueOf(5), multistack.peek("test").getValue());
    }

    @Test
    void pop() {
        multistack.push("test", new ValueWrapper(5));
        multistack.push("test", new ValueWrapper(3));
        assertEquals(Integer.valueOf(3), multistack.peek("test").getValue());
        assertEquals(Integer.valueOf(3), multistack.pop("test").getValue());
        assertEquals(Integer.valueOf(5), multistack.peek("test").getValue());
        assertEquals(Integer.valueOf(5), multistack.pop("test").getValue());
    }

    @Test
    void peek() {
        multistack.push("test", new ValueWrapper(5));
        multistack.push("test", new ValueWrapper(3));
        assertEquals(Integer.valueOf(3), multistack.peek("test").getValue());
    }

    @Test
    void isEmpty() {
        assertTrue(multistack.isEmpty("test"));
        multistack.push("test", new ValueWrapper(5));
        assertFalse(multistack.isEmpty("test"));
    }
}