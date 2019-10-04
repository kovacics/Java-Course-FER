package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Class is used for converting input string into tokens.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptLexer {

    /**
     * Input data.
     */
    private char[] data;

    /**
     * Current index in the process.
     */
    private int currentIndex;

    /**
     * Last generated token.
     */
    private SmartScriptToken token;

    /**
     * Current lexer state.
     */
    private SmartScriptLexerState state;


    /**
     * Constructs lexer with input string which should be tokenized.
     *
     * @param input input data which should be tokenized.
     */
    public SmartScriptLexer(String input) {
        data = input.toCharArray();
        state = SmartScriptLexerState.TEXT;
    }

    /**
     * Generates next token based on the state in which lexer currently works.
     *
     * @return next token
     * @throws SmartScriptLexerException if there is no more tokens
     */
    public SmartScriptToken nextToken() {

        if (token != null && token.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptLexerException("No more tokens available.");
        }

        if (!inRange()) {
            token = new SmartScriptToken(SmartScriptTokenType.EOF, "null");
            return token;
        }

        if (state == SmartScriptLexerState.TEXT) {
            return nextTextToken();
        } else {
            return nextTagToken();
        }
    }


    /**
     * Generates next tag token.
     *
     * @return next token
     */
    private SmartScriptToken nextTagToken() {
        skipBlanks();

        if (!inRange()) {
            throw new SmartScriptLexerException("Invalid tag input.");
        }
        if (Character.isLetter(currentChar())) {
            return getTagNameOrVarToken();
        } else if (currentCharIs('\"')) {
            return getStringInTagsToken();
        } else if (isFunction()) {
            return getFunctionToken();
        } else if (isEchoTagName()) {
            return getEchoTagNameToken();
        } else if (isOperator()) {
            return getOperatorToken();
        } else if (Character.isDigit(currentChar())) {
            return getNumberToken();
        } else if (isClosingTag()) {
            return getClosingTagToken();
        }

        // invalid input
        else {
            throw new SmartScriptLexerException("Invalid input.");
        }
    }

    /**
     * Generates next text token.
     *
     * @return next token
     */
    private SmartScriptToken nextTextToken() {
        StringBuilder text = new StringBuilder();

        if (isOpeningTag()) {
            token = new SmartScriptToken(SmartScriptTokenType.OPENING_TAG, null);
            currentIndex += 2;
            return token;
        }

        while (inRange() && !isOpeningTag()) {
            if (currentCharIs('\\')) {
                if (isEscapingSequence()) {
                    text.append(returnEscaping());
                    currentIndex += 2;
                } else throw new SmartScriptLexerException("Not a valid escaping sequence.");
            } else {
                text.append(currentChar());
                currentIndex++;
            }
        }
        token = new SmartScriptToken(SmartScriptTokenType.TEXT, text.toString());
        return token;
    }

    //********************************
    //  CONCRETE TOKEN GETTER METHODS
    //********************************


    /**
     * Generates next tag name or variable token.
     *
     * @return next tag name or variable token
     */
    private SmartScriptToken getTagNameOrVarToken() {
        StringBuilder text = new StringBuilder();
        bufferVariableOrTagName(text);

        // if first after opening tag, than it is tag name
        if (getToken() != null && getToken().getType() == SmartScriptTokenType.OPENING_TAG) {
            token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, text.toString());
        } else {
            token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, text.toString());
        }
        return token;
    }

    /**
     * Generates next function token.
     *
     * @return function token
     */
    private SmartScriptToken getFunctionToken() {
        StringBuilder text = new StringBuilder();

        currentIndex++;

        while (inRange() &&
                (Character.isLetterOrDigit(currentChar()) || currentCharIs('_'))) {
            text.append(currentChar());
            currentIndex++;
        }

        token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, text.toString());
        return token;
    }

    /**
     * Generates next string token.
     *
     * @return next string token
     */
    private SmartScriptToken getStringInTagsToken() {
        StringBuilder text = new StringBuilder();

        currentIndex++;
        while (inRange() && !currentCharIs('\"')) {
            if (currentCharIs('\\')) {
                if (isEscapingSequence()) {
                    text.append(returnEscaping());
                    currentIndex += 2;
                } else throw new SmartScriptLexerException("Not a valid escaping sequence.");
            } else {
                text.append(currentChar());
                currentIndex++;
            }
        }
        if (currentCharIs('\"')) {
            currentIndex++;
            token = new SmartScriptToken(SmartScriptTokenType.TAG_TEXT, text.toString());
            return token;
        } else {
            throw new SmartScriptLexerException("String is not closed, second \" is missing.");
        }
    }

    /**
     * Generates next number token.
     *
     * @return next number token
     */
    private SmartScriptToken getNumberToken() {
        StringBuilder text = new StringBuilder();

        while (inRange() && Character.isDigit(currentChar())) {
            text.append(currentChar());
            currentIndex++;
        }

        if (inRange() && currentCharIs('.')) {
            if (currentIndex < data.length - 1 && Character.isDigit(nextChar())) {
                text.append(currentChar());
                currentIndex++;
                while (inRange() && Character.isDigit(currentChar())) {
                    text.append(currentChar());
                    currentIndex++;
                }
                if (inRange() && currentCharIs('.')) {
                    throw new SmartScriptLexerException("Invalid input");
                }
            } else throw new SmartScriptLexerException("Invalid input");
        }

        try {
            int result = Integer.parseInt(text.toString());
            token = new SmartScriptToken(SmartScriptTokenType.INT_NUMBER, result);
            return token;
        } catch (NumberFormatException e) {
            System.out.println(text.toString());
            double result = Double.parseDouble(text.toString());
            token = new SmartScriptToken(SmartScriptTokenType.DOUBLE_NUMBER, result);
            return token;
        }
    }

    /**
     * Generates next closing-tag token.
     *
     * @return next closing tag token
     */
    private SmartScriptToken getClosingTagToken() {
        token = new SmartScriptToken(SmartScriptTokenType.CLOSING_TAG, null);
        currentIndex += 2;
        return token;
    }

    /**
     * Generates next echo tag name token
     *
     * @return next echo tag name token
     */
    private SmartScriptToken getEchoTagNameToken() {
        token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, String.valueOf(currentChar()));
        currentIndex++;
        return token;
    }

    /**
     * Generates next operator token.
     *
     * @return next operator token
     */
    private SmartScriptToken getOperatorToken() {
        StringBuilder text = new StringBuilder();

        if (currentCharIs('-')) {
            text.append(currentChar());
            currentIndex++;

            if (Character.isDigit(currentChar())) {
                token = getNumberToken();
            } else {
                token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, text.toString());
            }
            return token;
        }

        token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, String.valueOf(currentChar()));

        currentIndex++;
        return token;
    }

    //********************************
    //    OTHER HELPING METHODS
    //********************************


    /**
     * Helping method that skips all blank spaces.
     */
    private void skipBlanks() {
        while (inRange() && Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
    }

    /**
     * Checks if current index is in range.
     *
     * @return true if in range
     */
    private boolean inRange() {
        return currentIndex < data.length;
    }

    /**
     * Checks if current and next character form escaping sequence for
     * current lexer state.
     *
     * @return true if they form escaping sequence, false if not
     */
    private boolean isEscapingSequence() {
        if (state == SmartScriptLexerState.TEXT) {
            return currentIndex < data.length - 1 && (currentCharIs('\\') &&
                    (nextCharIs('\\') || nextCharIs('{')));
        } else {
            return currentIndex < data.length - 1 && currentCharIs('\\') &&
                    (nextCharIs('\\') || nextCharIs('r') || nextCharIs('n') ||
                            nextCharIs('t') || nextCharIs('\"'));
        }
    }

    /**
     * Returns char representation of the escaping sequence.
     *
     * @return char formed from escaping sequence.
     */
    private char returnEscaping() {
        switch (nextChar()) {

        case '\\':
            return '\\';
        case 'n':
            return '\n';
        case 't':
            return '\t';
        case 'r':
            return '\r';
        case '\"':
            return '\"';
        case '{':
            return '{';
        default:
            throw new SmartScriptLexerException("Invalid escaping sequence.");
        }
    }

    /**
     * Checks if current and next char form opening tag.
     *
     * @return true if next character sequence is opening tag, false if it's not
     */
    private boolean isOpeningTag() {
        return currentIndex < data.length - 1 && currentCharIs('{') && nextCharIs('$');
    }


    /**
     * Checks if current char is echo tag name
     *
     * @return true if it is, false if it isn't
     */
    private boolean isEchoTagName() {
        return currentCharIs('=');
    }


    /**
     * Checks if current and next character form closing tag.
     *
     * @return true if next char sequence is closing tag, false if isn't
     */
    private boolean isClosingTag() {
        return currentIndex < data.length - 1 && currentCharIs('$') && nextCharIs('}');
    }


    /**
     * Checks if current char is operator.
     *
     * @return true if current char is operator, otherwise false
     */
    private boolean isOperator() {
        return currentCharIs('+') || currentCharIs('*') || currentCharIs('/')
                || currentCharIs('^') || currentCharIs('-');
    }

    /**
     * Checks if next character sequence for method.
     *
     * @return true if next character sequence is method, otherwise false
     */
    private boolean isFunction() {
        return currentIndex < data.length - 1 && currentCharIs('@') &&
                Character.isLetter(nextChar());
    }

    /**
     * Buffer next variable or tag name in the {@code StringBuilder} which will then
     * later be analyzed if it is variable or tag name.
     *
     * @param text StringBuilder buffer
     */
    private void bufferVariableOrTagName(StringBuilder text) {
        text.append(currentChar());
        currentIndex++;

        while (inRange() &&
                (Character.isLetterOrDigit(currentChar()) || currentCharIs('_'))) {
            text.append(currentChar());
            currentIndex++;
        }
    }

    /**
     * Checks if current char is equal to given char.
     *
     * @param c char to check equality with current char
     * @return true if they are equal, false if not
     */
    private boolean currentCharIs(char c) {
        return data[currentIndex] == c;
    }

    /**
     * @return current char
     */
    private char currentChar() {
        return data[currentIndex];
    }

    /**
     * Checks if next char is equal to given char.
     *
     * @param c char to check equality with next char
     * @return true if they are equal, false if not
     */
    private boolean nextCharIs(char c) {
        return data[currentIndex + 1] == c;
    }

    /**
     * @return next char
     */
    private char nextChar() {
        return data[currentIndex + 1];
    }

    //********************************
    //    GETTERS AND SETTERS
    //********************************


    /**
     * Getter of the last generated token.
     *
     * @return current token
     */
    public SmartScriptToken getToken() {
        return token;
    }

    /**
     * Sets the state of the lexer.
     *
     * @param state state to be set
     */
    public void setState(SmartScriptLexerState state) {
        Objects.requireNonNull(state, "State cannot be null");
        this.state = state;
    }
}
