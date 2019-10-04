package hr.fer.zemris.java.hw15.hash;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 * Util class of sha-1 hashing methods.
 *
 * @author Stjepan Kovačić
 */
public class SHA1Util {

    /**
     * Method generates hash string of the input string
     * using sha-1 hashing algorithm.
     *
     * @param input input string
     * @return hash value of the input string
     */
    public static String getHash(String input) {
        InputStream inputBytes = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[4096];

            while (true) {
                int bytesRead = inputBytes.read(buffer);
                if (bytesRead < 1) break;
                digest.update(buffer, 0, bytesRead);
            }
        } catch (Exception ignore) {
        }
        return digest != null ? ByteHexConverter.bytetohex(digest.digest()) : null;
    }
}
