package hr.fer.zemris.java.hw05.db.parser;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;

import java.util.NoSuchElementException;

import static hr.fer.zemris.java.hw05.db.parser.QueryTokenType.*;

/**
 * Class represent lexer of the {@link ConditionalExpression} class.
 * Lexer's task is creating {@code QueryToken} tokens, which will
 * later be processed by the {@link QueryParser} class.
 * Lexer only supply tokens representing:
 * <li><b>word</b> - word that is not inside quotes</li>
 * <li><b>string literal</b> - word inside quotes (e.g. "Zagreb")</li>
 * <li><b>operator</b> - character representing comparing operator (e.g. '>')</li>
 * <li><b>EOF</b> - this token represents end of file</li>
 */
public class QueryLexer {

    /**
     * Char array representation of the query.
     * Source for the token extraction.
     */
    private char[] query;

    /**
     * Last generated token.
     */
    private QueryToken token;

    /**
     * Index of the current character being processed.
     */
    private int currentIndex;

    /**
     * Constructs class with given source string for token extraction.
     *
     * @param query query which should be split into tokens.
     */
    public QueryLexer(String query) {
        this.query = query.trim().toCharArray();
    }

    /**
     * Getter for the last generated token.
     * <p>Does <b>not</b> generate next token.</p>
     *
     * @return last generated token
     */
    public QueryToken getToken() {
        return token;
    }

    /**
     * Extracts and returns next token from the query.
     *
     * @return extracted token
     * @throws QueryLexerException    if query is not valid, and next token cannot be extracted
     * @throws NoSuchElementException if there is no more tokens(positioned at the end of the query)
     */
    public QueryToken nextToken() {
        if (token != null && token.getType() == EOF) {
            throw new NoSuchElementException("No more tokens.");
        }

        if (!inRange()) {
            token = new QueryToken(EOF, "null");
            return token;
        }

        skipSpaces();

        if (Character.isLetter(getCurrentChar())) {
            return nextWordToken();
        } else if (isCompareOperator()) {
            return nextCompareOperatorToken();
        } else if (currentCharIs('\"')) {
            return nextStringLiteralToken();
        } else throw new QueryLexerException("Invalid query.");
    }

    /**
     * Private method that extracts next word token.
     *
     * @return next word token
     */
    private QueryToken nextWordToken() {
        StringBuilder word = new StringBuilder();
        while (inRange() && Character.isLetter(getCurrentChar())) {
            word.append(getCurrentChar());
            currentIndex++;
        }
        token = new QueryToken(WORD, word.toString());
        return token;
    }

    /**
     * Helping method to check if current character is compare operator.
     *
     * @return {@code true} if current character is compare operator,
     * {@code false} if isn't
     */
    private boolean isCompareOperator() {
        String operators = "!<>=";
        return operators.indexOf(getCurrentChar()) != -1;
    }

    /**
     * Private method that extracts next operator token.
     *
     * @return next operator token
     */
    private QueryToken nextCompareOperatorToken() {
        StringBuilder operator = new StringBuilder();

        while (inRange() && isCompareOperator()) {
            operator.append(getCurrentChar());
            currentIndex++;
        }
        token = new QueryToken(OPERATOR, operator.toString());
        return token;
    }

    /**
     * Private method for extracting next string literal token.
     *
     * @return next string literal token
     * @throws QueryLexerException if closing quote is missing
     */
    private QueryToken nextStringLiteralToken() {
        StringBuilder sb = new StringBuilder();
        currentIndex++;

        while (inRange() && !currentCharIs('\"')) {
            sb.append(getCurrentChar());
            currentIndex++;
        }
        if (!inRange()) {
            throw new QueryLexerException("Closing quote is missing");
        }
        currentIndex++;
        return new QueryToken(STRING_LITERAL, sb.toString());
    }

    /**
     * Helping method that skips all spaces and position index on the first
     * character that is not space.
     */
    private void skipSpaces() {
        while (inRange() && currentIsSpace()) {
            currentIndex++;
        }
    }

    /**
     * Helping method that current char is space.
     *
     * @return {@code true} if current char is space,
     * {@code false} if isn't
     */
    private boolean currentIsSpace() {
        String blank = " \t\n\r";
        return blank.indexOf(getCurrentChar()) != -1;
    }

    /**
     * Helping method that checks if current char is equal to passed char.
     *
     * @param c char to check if equal to current char
     * @return {@code true} if they are equal, otherwise {@code false}
     */
    private boolean currentCharIs(char c) {
        return getCurrentChar() == c;
    }

    /**
     * Helping method that returns current char.
     *
     * @return current char;
     */
    private char getCurrentChar() {
        return query[currentIndex];
    }

    /**
     * Checks if current index is in range.
     *
     * @return true if in range
     */
    private boolean inRange() {
        return currentIndex < query.length;
    }
}
