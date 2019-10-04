package hr.fer.zemris.java.hw06.shell.namebuilder;


import hr.fer.zemris.java.hw06.shell.namebuilder.lexer.NameBuilderLexer;
import hr.fer.zemris.java.hw06.shell.namebuilder.lexer.NameBuilderLexerState;

import static hr.fer.zemris.java.hw06.shell.namebuilder.lexer.NameBuilderTokenType.*;

/**
 * Class represents parser for a name builder class.
 *
 * @author Stjepan Kovačić
 */
public class NameBuilderParser {

    /**
     * Name builder which is being constructed while parsing.
     */
    private NameBuilder nb = null;

    /**
     * Constructs name builder parser with specified expression
     *
     * @param expression expression for parsing
     */
    public NameBuilderParser(String expression) {
        parseExpression(expression);
    }

    /**
     * Parses given expression.
     *
     * @param expression string expression which should be parsed
     */
    private void parseExpression(String expression) {
        var lexer = new NameBuilderLexer(expression);
        var token = lexer.nextToken();

        while (token.getType() != EOF) {
            switch (token.getType()) {
            case OPEN_GROUP:
                lexer.setState(NameBuilderLexerState.GROUP);
                break;
            case TEXT:
                addNextNb(DefaultNameBuilders.text((String) token.getValue()));
                break;
            case NUMBER:
            case PADDING_NUMBER:
                parseGroup(lexer);
                break;
            default:
                throw new RuntimeException("Parsing error.");
            }

            token = lexer.nextToken();
        }
    }

    /**
     * Private method that parses part of input which represents a group.
     *
     * @param lexer lexer which supplies parser with tokens
     */
    private void parseGroup(NameBuilderLexer lexer) {
        int group;
        int minSize;
        char padding = ' ';

        var token = lexer.getToken();
        group = (int) token.getValue();

        if (group < 0) {
            throw new RuntimeException("Group cannot be less than zero.");
        }
        token = lexer.nextToken();

        if (token.getType() == CLOSE_GROUP) {
            addNextNb(DefaultNameBuilders.group(group));
            lexer.setState(NameBuilderLexerState.TEXT);
            return;
        } else if (token.getType() == PADDING_NUMBER) {
            padding = '0';
        }
        minSize = (int) token.getValue();
        addNextNb(DefaultNameBuilders.group(group, padding, minSize));

        token = lexer.nextToken();
        if (token.getType() == CLOSE_GROUP) {
            lexer.setState(NameBuilderLexerState.TEXT);
        } else {
            throw new RuntimeException("Illegal input, group should have been closed.");
        }
    }

    /**
     * Method adds another name builder to the composite name builder.
     *
     * @param nextNb name builder to add
     */
    private void addNextNb(NameBuilder nextNb) {
        if (nb == null) {
            nb = nextNb;
        } else {
            nb = nb.then(nextNb);
        }
    }

    /**
     * Returns parsed name builder.
     *
     * @return name builder
     */
    public NameBuilder getNameBuilder() {
        return nb;
    }
}
