package hr.fer.zemris.java.custom.scripting.lexer.demo;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;

/**
 * Demo class for {@code SmartScriptLexer} class.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptLexerDemo {

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        String s = "This is sample text.\r\n" +
                "{$ FOR i 1 10 1 $}\r\n" +
                "This is {$= i $}-th time this message is generated.\r\n" +
                "{$END$}\r\n" +
                "{$FOR i 0 10 2 $}\r\n" +
                "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" +
                "{$END$}";

        SmartScriptLexer lexer = new SmartScriptLexer(s);
        SmartScriptToken token;

        while (true) {
            token = lexer.nextToken();
            System.out.println(lexer.getToken().getValue() + "-" + lexer.getToken().getType());

            if (token.getType() == SmartScriptTokenType.EOF) {
                break;
            } else if (token.getType() == SmartScriptTokenType.OPENING_TAG) {
                lexer.setState(SmartScriptLexerState.TAG);
                System.out.printf("%nChanged lexer state to TAG %n");
            } else if (token.getType() == SmartScriptTokenType.CLOSING_TAG) {
                lexer.setState(SmartScriptLexerState.TEXT);
                System.out.printf("%nChanged lexer state to TEXT %n");
            }
        }
    }
}
