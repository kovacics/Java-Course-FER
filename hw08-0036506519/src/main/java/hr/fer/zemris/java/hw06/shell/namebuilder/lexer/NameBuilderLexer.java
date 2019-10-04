package hr.fer.zemris.java.hw06.shell.namebuilder.lexer;

import hr.fer.zemris.java.hw06.shell.namebuilder.NameBuilder;

import static hr.fer.zemris.java.hw06.shell.namebuilder.lexer.NameBuilderTokenType.*;

/**
 * Class represents lexer which produces tokens from the input string which can than be processed by
 * {@link NameBuilder} class.
 *
 * @author Stjepan Kovačić
 */
public class NameBuilderLexer {

    /**
     * Input data.
     */
    private char[] data;

    /**
     * Last generated token.
     */
    private NameBuilderToken token;

    /**
     * Index of the current char being processed.
     */
    private int currentIndex;

    /**
     * Current state of the lexer.
     */
    private NameBuilderLexerState state = NameBuilderLexerState.TEXT;


    /**
     * Constructs lexer with specified input data.
     *
     * @param data data for lexer
     */
    public NameBuilderLexer(String data) {
        this.data = data.toCharArray();
    }

    /**
     * Returns last generated token, doesn't produce any new tokens.
     *
     * @return current token
     */
    public NameBuilderToken getToken() {
        return token;
    }

    /**
     * Generates and returns next token.
     *
     * @return generated token
     */
    public NameBuilderToken nextToken() {
        if (token != null && token.getType() == EOF) {
            throw new RuntimeException("No more tokens.");
        }
        skipBlanks();
        if (!inRange()) {
            token = new NameBuilderToken(EOF, null);
            return token;
        }
        if (state == NameBuilderLexerState.TEXT) {
            return nextTextToken();
        } else {
            return nextGroupToken();
        }
    }

    /**
     * Private method for producing next text token.
     *
     * @return generated token
     */
    private NameBuilderToken nextTextToken() {
        if (inRange() && currentCharIs('$')) {
            currentIndex++;
            if (inRange() && currentCharIs('{')) {
                token = new NameBuilderToken(OPEN_GROUP, "${");
                currentIndex++;
                return token;
            }
            throw new RuntimeException("Illegal input, '$' should be followed by '{'.");
        } else {
            StringBuilder text = new StringBuilder();
            while (inRange() && !currentCharIs('$')) {
                text.append(data[currentIndex++]);
            }
            token = new NameBuilderToken(TEXT, text.toString());
            return token;
        }
    }

    /**
     * Private method for producing next group token.
     *
     * @return generated token
     */
    public NameBuilderToken nextGroupToken() {
        if (inRange() && Character.isDigit(data[currentIndex])) {
            StringBuilder number = new StringBuilder();

            while (inRange() && Character.isDigit(data[currentIndex]) && !currentCharIs('}')) {
                number.append(data[currentIndex++]);
            }
            skipBlanks();

            if (inRange() && currentCharIs(',')) {
                currentIndex++;
            }
            if (number.toString().startsWith("0")) {
                token = new NameBuilderToken(PADDING_NUMBER, Integer.parseInt(number.toString()));
            } else {
                token = new NameBuilderToken(NUMBER, Integer.parseInt(number.toString()));
            }
            return token;
        } else if (inRange() && currentCharIs('}')) {
            currentIndex++;
            token = new NameBuilderToken(CLOSE_GROUP, "}");
            return token;
        } else {
            throw new RuntimeException("Illegal input, (group part is not defined good).");
        }
    }

    /**
     * Method skips all blanks.
     */
    private void skipBlanks() {
        if (inRange() && Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
    }

    /**
     * Checks if current index is in range.
     *
     * @return true if in range, false otherwise
     */
    private boolean inRange() {
        return currentIndex < data.length;
    }

    /**
     * Checks equality between current and given char.
     *
     * @param c given char for testing
     * @return true if equal, false otherwise
     */
    private boolean currentCharIs(char c) {
        return data[currentIndex] == c;
    }

    /**
     * Changes state of the lexer.
     *
     * @param state state to be set
     */
    public void setState(NameBuilderLexerState state) {
        this.state = state;
    }
}
