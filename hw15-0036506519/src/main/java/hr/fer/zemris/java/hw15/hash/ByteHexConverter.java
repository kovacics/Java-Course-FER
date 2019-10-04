package hr.fer.zemris.java.hw15.hash;


import java.util.Objects;

/**
 * Helping class with some conversion methods.
 *
 * @author Stjepan Kovačić
 */
public class ByteHexConverter {

    /**
     * String of hexes used for easy conversion of decimal->hexadecimal.
     */
    private static final String HEXES = "0123456789abcdef";

    /**
     * Base of the number conversion.
     */
    private static final int BASE = 16;

    /**
     * Mask for the last byte.
     */
    private static final byte LAST_BYTE_MASK = 0xf;

    /**
     * Mask for the second last byte.
     */
    private static final int SECOND_LAST_BYTE_MASK = 0xf0;


    /**
     * Method converts hexadecimal string into byte array.
     *
     * @param keyText hexadecimal number stored as string
     * @return array of bytes
     */
    public static byte[] hextobyte(String keyText) {
        Objects.requireNonNull(keyText);

        if (keyText.length() % 2 == 1) {
            throw new IllegalArgumentException("Cannot convert odd-sized hex string to byte array.");
        }

        byte[] array = new byte[keyText.length() / 2];
        keyText = keyText.toUpperCase();

        for (int i = 0; i < keyText.length(); i += 2) {
            String number = keyText.substring(i, i + 2);

            try {
                array[i / 2] = (byte) Integer.parseInt(number, BASE);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Not valid string");
            }
        }
        return array;
    }


    /**
     * Method converts byte array into hexadecimal string.
     *
     * @param byteArray array of bytes which should be converted into hexadecimal form
     * @return hexadecimal string
     */
    public static String bytetohex(byte[] byteArray) {
        Objects.requireNonNull(byteArray);

        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            int hex1 = (b & SECOND_LAST_BYTE_MASK) >> 4;
            int hex2 = b & LAST_BYTE_MASK;

            sb.append(HEXES.charAt(hex1)).append(HEXES.charAt(hex2));
        }
        return sb.toString();
    }
}

