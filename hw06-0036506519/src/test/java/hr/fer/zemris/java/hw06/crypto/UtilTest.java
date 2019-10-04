package hr.fer.zemris.java.hw06.crypto;

import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw06.crypto.Util.bytetohex;
import static hr.fer.zemris.java.hw06.crypto.Util.hextobyte;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void hextobyteTest() {
        byte[] array = hextobyte("01aE22");
        byte[] expected = {1, -82, 34};

        assertEquals(3, array.length);
        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i], array[i]);
        }
    }

    @Test
    void hextobyteEmptyTest() {
        byte[] array = hextobyte("");
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    void hextobyteNullTest() {
        assertThrows(NullPointerException.class, () -> hextobyte(null));
    }

    @Test
    void hextobyteThrowsForIllegalInputs() {
        assertThrows(IllegalArgumentException.class, () -> hextobyte("123"));
        assertThrows(IllegalArgumentException.class, () -> hextobyte("01aE2"));
        assertThrows(IllegalArgumentException.class, () -> hextobyte("G"));
        assertThrows(IllegalArgumentException.class, () -> hextobyte("1234g"));
        assertThrows(IllegalArgumentException.class, () -> hextobyte("zzz"));
    }

    @Test
    void hextobyteWorksGoodForBase16() {
        assertEquals(10, hextobyte("0a")[0]);
        assertEquals(10, hextobyte("0A")[0]);
        assertEquals(12, hextobyte("0C")[0]);
        assertEquals(15, hextobyte("0F")[0]);
    }


    @Test
    void bytetohexTest() {
        assertEquals("01ae22", bytetohex(new byte[]{1, -82, 34}));
    }

    @Test
    void bytetohexNullTest() {
        assertThrows(NullPointerException.class, () -> bytetohex(null));
    }

    @Test
    void bytetohexEmptyTest() {
        assertEquals("", bytetohex(new byte[]{}));
    }
}