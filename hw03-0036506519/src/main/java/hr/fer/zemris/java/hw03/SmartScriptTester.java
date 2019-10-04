package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tester class for {@link SmartScriptParser} class.
 * Program accepts path to the file to parse as single command-line argument
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptTester {

    /**
     * Main method of the program, being called first.
     *
     * @param args command-line arguments,
     *             should accept single command-line argument which is a path to a file
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Program should accept exactly one argument, "
                    + "a path to the file which should get parsed");
            System.exit(1);
        }

        String filepath = args[0];
        String docBody = null;

        try {
            docBody = Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document! " + e.getMessage());
            System.exit(-1);
        }

        DocumentNode document = parser.getDocumentNode();
        System.out.println(document);
        System.out.printf("%nPARSING AGAIN:%n");

        String originalDocumentBody = createOriginalDocumentBody(document);
        parser = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser.getDocumentNode();
        System.out.println(document2);
    }

    /**
     * Creates original document body from parsed document node.
     *
     * @param document DocumentNode of some parsed data
     * @return string representation of original document body
     */
    public static String createOriginalDocumentBody(DocumentNode document) {
        return document.toString();
    }
}
