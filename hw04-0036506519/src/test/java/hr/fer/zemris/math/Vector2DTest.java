package hr.fer.zemris.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {

    Vector2D v;
    Vector2D vector45;


    @BeforeEach
    public void createTestingVectors() {
        v = new Vector2D(2, 8);
        vector45 = new Vector2D(Math.sqrt(2), Math.sqrt(2));
    }


    @Test
    public void constructorTest() {
        assertNotNull(v);
    }

    @Test
    public void getXTest() {
        assertEquals(2, v.getX());
    }

    @Test
    public void getYTest() {
        assertEquals(8, v.getY());
    }


    @Test
    public void translateTestThrowsForNull() {
        assertThrows(NullPointerException.class, () -> v.translate(null));
    }

    @Test
    public void translateTest() {
        assertEquals(2, v.getX());
        assertEquals(8, v.getY());

        v.translate(new Vector2D(2, -4));
        assertEquals(new Vector2D(4, 4), v);

    }

    @Test
    public void translatedTest() {
        Vector2D vector = v.translated(new Vector2D(2, -4));
        assertNotNull(vector);
        assertEquals(new Vector2D(4, 4), vector);
    }

    @Test
    public void rotateTest() {
        vector45.rotate(Math.PI / 4);
        assertEquals(new Vector2D(0, 2), vector45);
    }

    @Test
    public void rotatedTest() {
        Vector2D testvec = vector45.rotated(Math.PI / 4);
        assertNotNull(testvec);
        assertEquals(new Vector2D(0, 2), testvec);
    }

    @Test
    public void scaleTest() {
        vector45.scale(Math.sqrt(2));
        assertEquals(new Vector2D(2, 2), vector45);
    }

    @Test
    public void scaledTest() {
        Vector2D testvec = vector45.scaled(Math.sqrt(2));
        assertNotNull(testvec);
        assertEquals(new Vector2D(2, 2), testvec);
    }

    @Test
    public void copyTest() {
        Vector2D copy = v.copy();
        assertEquals(copy, v);

        copy.rotate(10);
        assertNotEquals(copy, v);
    }


}
