package hr.fer.zemris.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit tester class for {@code SmartScriptLexer} class.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptLexerTest {

    @Test
    public void testNoMoreTokens() {
        SmartScriptLexer lexer = new SmartScriptLexer("bla bla");
        lexer.nextToken();

        assertEquals(EOF, lexer.nextToken().getType());
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
    }

    @Test
    public void testTextState() {
        SmartScriptLexer lexer = new SmartScriptLexer("bla \\\\ bla");
        assertEquals("bla \\ bla", lexer.nextToken().getValue());
    }


    @Test
    public void testTagState() {
        SmartScriptLexer lexer = new SmartScriptLexer("bla  @bla 3.15 \" \\\" \" +");
        lexer.setState(SmartScriptLexerState.TAG);

        assertEquals("bla", lexer.nextToken().getValue());
        assertEquals(VARIABLE, lexer.getToken().getType());

        assertEquals("bla", lexer.nextToken().getValue());
        assertEquals(FUNCTION, lexer.getToken().getType());

        assertEquals(3.15, lexer.nextToken().getValue());
        assertEquals(DOUBLE_NUMBER, lexer.getToken().getType());

        assertEquals(" \" ", lexer.nextToken().getValue());
        assertEquals(TAG_TEXT, lexer.getToken().getType());

        assertEquals("+", lexer.nextToken().getValue());
        assertEquals(OPERATOR, lexer.getToken().getType());
    }

    @Test
    public void testMixedStates() {
        SmartScriptLexer lexer = new SmartScriptLexer("tekst prije taga {$ @bla 3.15 \" \\\" \" + $} tekst poslije taga");

        assertEquals("tekst prije taga ", lexer.nextToken().getValue());
        assertEquals(TEXT, lexer.getToken().getType());

        assertEquals(null, lexer.nextToken().getValue());
        assertEquals(OPENING_TAG, lexer.getToken().getType());

        lexer.setState(SmartScriptLexerState.TAG);

        assertEquals("bla", lexer.nextToken().getValue());
        assertEquals(FUNCTION, lexer.getToken().getType());

        assertEquals(3.15, lexer.nextToken().getValue());
        assertEquals(DOUBLE_NUMBER, lexer.getToken().getType());

        assertEquals(" \" ", lexer.nextToken().getValue());
        assertEquals(TAG_TEXT, lexer.getToken().getType());

        assertEquals("+", lexer.nextToken().getValue());
        assertEquals(OPERATOR, lexer.getToken().getType());

        assertEquals(null, lexer.nextToken().getValue());
        assertEquals(CLOSING_TAG, lexer.getToken().getType());

        lexer.setState(SmartScriptLexerState.TEXT);

        assertEquals(" tekst poslije taga", lexer.nextToken().getValue());
        assertEquals(TEXT, lexer.getToken().getType());
    }

    @Test
    public void InvalidInputs() {

        SmartScriptLexer lexer = new SmartScriptLexer("@@@");
        lexer.setState(SmartScriptLexerState.TAG);
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

        SmartScriptLexer lexer2 = new SmartScriptLexer("2.");
        lexer2.setState(SmartScriptLexerState.TAG);
        assertThrows(SmartScriptLexerException.class, () -> lexer2.nextToken());

        SmartScriptLexer lexer3 = new SmartScriptLexer("\\");
        lexer3.setState(SmartScriptLexerState.TAG);
        assertThrows(SmartScriptLexerException.class, () -> lexer3.nextToken());

        SmartScriptLexer lexer4 = new SmartScriptLexer("??");
        lexer4.setState(SmartScriptLexerState.TAG);
        assertThrows(SmartScriptLexerException.class, () -> lexer4.nextToken());
    }
}
