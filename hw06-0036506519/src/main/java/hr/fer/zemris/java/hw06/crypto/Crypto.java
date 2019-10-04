package hr.fer.zemris.java.hw06.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import static hr.fer.zemris.java.hw06.crypto.Util.bytetohex;
import static hr.fer.zemris.java.hw06.crypto.Util.hextobyte;

/**
 * Program allows the user these functions :
 * <li><b>encryption</b> of the given file using the AES crypto-algorithm and 128-bit encription key</li>
 * <li><b>decryption</b> of the given file using the AES crypto-algorithm and 128-bit encription key</li>
 * <li><b>checking</b> the SHA-256 <b>file digest</b></li>
 *
 * @author Stjepan Kovačić
 */
public class Crypto {

    /**
     * Main method of the program, being called first.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        if (args.length == 3) {
            String inputFileName = args[1];
            String outputFileName = args[2];
            boolean encrypt;

            if (args[0].equals("encrypt")) {
                encrypt = true;
            } else if (args[0].equals("decrypt")) {
                encrypt = false;
            } else {
                throw new IllegalArgumentException("Illegal command");
            }
            cryptOp(encrypt, inputFileName, outputFileName);
        } else if (args.length == 2) {

            if (args[0].equals("checksha")) {
                checkshaOp(args);
            } else {
                throw new IllegalArgumentException("Illegal command.");
            }
        } else {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
    }

    /**
     * Method process encryption and decryption operation.
     *
     * @param encrypt        which operation to do
     * @param inputFileName  input file
     * @param outputFileName output file
     */
    private static void cryptOp(boolean encrypt, String inputFileName, String outputFileName) {

        try (Scanner sc = new Scanner(System.in);
             InputStream input = Files.newInputStream(Paths.get(inputFileName));
             OutputStream output = Files.newOutputStream(Paths.get(outputFileName))) {

            System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
            System.out.print("> ");
            String keyText = sc.nextLine();

            System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
            System.out.print("> ");
            String ivText = sc.nextLine();

            SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

            byte[] buffer = new byte[4096];

            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(cipher.update(buffer, 0, bytesRead));
            }
            output.write(cipher.doFinal());

            System.out.print(encrypt ? "Encryption " : "Decryption ");
            System.out.println("completed. Generated file " + outputFileName + " based on file " + inputFileName);
        } catch (IOException | GeneralSecurityException e) {
            System.out.print("Error happened while ");
            System.out.println(encrypt ? "encryption" : "decryption");
        }
    }

    /**
     * Method for processing 'checksha' operation.
     *
     * @param args command arguments
     */
    private static void checkshaOp(String[] args) {

        try (Scanner sc = new Scanner(System.in);
             InputStream input = Files.newInputStream(Paths.get(args[1]))) {

            System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
            System.out.print("> ");
            String expectedDigest = sc.nextLine();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[4096];

            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            String actualDigest = bytetohex(digest.digest());

            if (actualDigest.equals(expectedDigest)) {
                System.out.println("Digesting completed. Digest of " + args[0] + " matches expected digest.");
            } else {
                System.out.println("Digesting completed. Digest of " + args[1] + " does not match the expected digest. " +
                        "Digest was " + actualDigest);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println("Error happened while checking the digest.");
        }
    }
}
