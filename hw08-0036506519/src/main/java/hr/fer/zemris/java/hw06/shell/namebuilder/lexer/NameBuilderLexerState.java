package hr.fer.zemris.java.hw06.shell.namebuilder.lexer;

/**
 * Represents states in which lexer can work.
 * <li>TEXT STATE - producing text , and open group tokens</li>
 * <li>GROUP STATE - producing only number tokens (and close group token)</li>
 *
 * @author Stjepan Kovačić
 */
public enum NameBuilderLexerState {
    /**
     * Text state
     */
    TEXT,
    /**
     * Group state
     */
    GROUP
}
