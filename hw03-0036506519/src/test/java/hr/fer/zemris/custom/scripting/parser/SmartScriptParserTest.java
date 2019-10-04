package hr.fer.zemris.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw03.SmartScriptTester.createOriginalDocumentBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit tester class for {@code SmartScriptParser} class.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptParserTest {

    @Test
    public void testParsing() {

        String s = "This is sample text.\r\n" +
                "{$ FOR i 1 10 1 $}\r\n" +
                "This is {$= i $}-th time this message is generated.\r\n" +
                "{$END$}\r\n" +
                "{$FOR i 0 10 2 $}\r\n" +
                "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" +
                "{$END$}";

        SmartScriptParser parser = new SmartScriptParser(s);

        DocumentNode document = parser.getDocumentNode();

        String original = createOriginalDocumentBody(document);
        parser = new SmartScriptParser(original);
        DocumentNode document2 = parser.getDocumentNode();
        String original2 = createOriginalDocumentBody(document2);

        assertEquals(original, original2);
    }

    @Test
    public void testThrows() {
        String s = "This is sample text.\\n" +
                "{$ FOR i 1 10 1 $}\r\n" +
                "This is {$= i $}-th time this message is generated.\r\n" +
                "{$END$}\r\n" + "{$END$}" + "{$END$}" +
                "{$FOR i 0 10 2 $}\r\n" +
                "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" +
                "{$END$}";

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(s));
    }

    @Test
    public void testWrongTagNameThrows() {
        String s = "TEst {$ WRONG bla bla $}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(s));
    }
}
