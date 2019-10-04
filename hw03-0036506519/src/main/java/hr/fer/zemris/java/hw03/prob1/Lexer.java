package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Class is used for converting input string into tokens.
 * It can work in two states, BASIC and EXTENDED, that
 * determine different rules for tokenization.
 *
 * @author Stjepan Kovačić
 */
public class Lexer {

    /**
     * Array for storing all input's characters
     */
    private char[] data;

    /**
     * Last generated token
     */
    private Token token;

    /**
     * Index of the current character
     */
    private int currentIndex;

    /**
     * Current state of the lexer
     */
    private LexerState state;

    /**
     * Sets the state of the lexer.
     *
     * @param state State to set
     * @throws NullPointerException if given state is null
     */
    public void setState(LexerState state) {
        Objects.requireNonNull(state, "Lexer state cannot be null.");
        this.state = state;
    }

    /**
     * Constructs a lexer, and converts given string to the char array.
     * Sets lexer's state to BASIC.
     *
     * @param text Input string for lexer.
     * @throws NullPointerException if given string is null.
     */
    public Lexer(String text) {
        Objects.requireNonNull(text, "Input string cannot be null.");

        data = text.toCharArray();
        state = LexerState.BASIC;
    }

    /**
     * Generate next token based on current lexer state.
     *
     * @return next token from the lexer.
     * @throws LexerException if no more tokens are available or if input is not valid
     */
    public Token nextToken() {
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("No more tokens.");
        }

        if (state == LexerState.BASIC) {
            return getBasicToken();
        } else {
            return getExtendedToken();
        }
    }

    /**
     * @return last generated token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Generate next token when in extended state.
     *
     * @return generated token
     */
    private Token getExtendedToken() {
        while (currentIndex < data.length) {

            if (isSpace()) {
                currentIndex++;
            } else if (currentCharIs('#')) {
                token = new Token(TokenType.SYMBOL, '#');
                currentIndex++;
                return token;
            } else {
                StringBuilder text = new StringBuilder();

                while (currentIndex < data.length && !currentCharIs(' ') &&
                        !currentCharIs('#')) {
                    text.append(currentChar());
                    currentIndex++;
                }
                token = new Token(TokenType.WORD, text.toString());
                return token;
            }
        }

        token = new Token(TokenType.EOF, null);
        return token;
    }

    /**
     * Generate next token when in basic state.
     *
     * @return generated token
     */
    private Token getBasicToken() {

        while (currentIndex < data.length) {
            char c = data[currentIndex];

            if (isSpace()) {
                currentIndex++;
            } else if (Character.isLetter(c) || currentCharIs('\\')) {
                return getWordToken();
            } else if (Character.isDigit(c)) {
                return getNumberToken();
            } else {
                token = new Token(TokenType.SYMBOL, currentChar());
                currentIndex++;
                return token;
            }
        }

        this.token = new Token(TokenType.EOF, null);
        currentIndex++;
        return token;
    }

    /**
     * Generate number token.
     *
     * @return generated token
     */
    private Token getNumberToken() {
        StringBuilder text = new StringBuilder();

        while (currentIndex < data.length && Character.isDigit(currentChar())) {
            text.append(currentChar());
            currentIndex++;
        }

        try {
            long result = Long.parseLong(text.toString());
            token = new Token(TokenType.NUMBER, result);
            return token;
        } catch (NumberFormatException e) {
            throw new LexerException("Input is not valid.");
        }
    }

    /**
     * Generate word token.
     *
     * @return generated token
     */
    private Token getWordToken() {
        StringBuilder text = new StringBuilder();

        if (currentCharIs('\\') && !isEscapingSequence()) {
            throw new LexerException("Invalid escaping sequence");
        }

        while (currentIndex < data.length && (isEscapingSequence() ||
                Character.isLetter(currentChar()))) {

            if (isEscapingSequence()) {
                text.append(nextChar());
                currentIndex += 2;
            } else {
                text.append(currentChar());
                currentIndex++;
            }
        }

        token = new Token(TokenType.WORD, text.toString());
        return token;
    }

    /**
     * Checks if current char is space.
     *
     * @return true if it is, false if not
     */
    private boolean isSpace() {
        char c = currentChar();
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    /**
     * @return current char from array
     */
    private char currentChar() {
        return data[currentIndex];
    }

    /**
     * Checks if current and next char form escaping sequence.
     *
     * @return true if they do, false if don't
     */
    private boolean isEscapingSequence() {
        return currentIndex < data.length - 1 && currentCharIs('\\') &&
                (Character.isDigit(nextChar()) || nextCharIs('\\'));
    }

    /**
     * Checks if next char equals given char
     *
     * @param c char for which to check equality with next char
     * @return true if chars are equal, false if they are not
     */
    private boolean nextCharIs(char c) {
        return nextChar() == c;
    }

    /**
     * @return next char from array
     */
    private char nextChar() {
        return data[currentIndex + 1];
    }

    /**
     * Checks if current char equals given char
     *
     * @param c char for which to check equality with current char
     * @return true if chars are equal, false if they are not
     */
    private boolean currentCharIs(char c) {
        return currentChar() == c;
    }
}
