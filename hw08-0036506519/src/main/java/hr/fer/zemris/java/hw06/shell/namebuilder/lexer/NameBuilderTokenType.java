package hr.fer.zemris.java.hw06.shell.namebuilder.lexer;

/**
 * Represents type of tokens that {@link NameBuilderLexer} can produce
 *
 * @author Stjepan Kovačić
 */
public enum NameBuilderTokenType {

    TEXT,
    OPEN_GROUP,
    CLOSE_GROUP,
    NUMBER,
    PADDING_NUMBER,
    EOF
}
