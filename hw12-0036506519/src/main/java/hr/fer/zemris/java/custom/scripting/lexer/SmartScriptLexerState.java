package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents states on which {@link SmartScriptLexer} class can be.
 *
 * @author Stjepan Kovačić
 */
public enum SmartScriptLexerState {

    /**
     * Text state
     */
    TEXT,
    /**
     * Tag state
     */
    TAG
}
