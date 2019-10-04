package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import static hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType.*;

/**
 * Class is used for parsing {@link SmartScriptToken}
 * and creating a {@link DocumentNode} tree of given input.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptParser {

    /**
     * Lexer used for token supply
     */
    private SmartScriptLexer lexer;

    /**
     * Node that represents whole document.
     */
    private DocumentNode docNode;

    /**
     * Helping stack structure for building a document tree
     */
    private ObjectStack stack = new ObjectStack();


    /**
     * Constructor whose parameter is input string which should be parsed.
     *
     * @param input string to parse
     */
    public SmartScriptParser(String input) {
        try {
            lexer = new SmartScriptLexer(input);
            parseScript();
        } catch (SmartScriptLexerException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
        if (stack.size() != 1) {
            throw new SmartScriptParserException("Cannot parse properly.");
        }
    }

    /**
     * Private method that parses input string.
     * It construct {@link DocumentNode} which is is root node for creating proper
     * node tree of a document and pushes that <code>DocumentNode</code> on stack
     * so that other nodes can become its children.
     *
     * @throws SmartScriptParserException if sequence of token is not valid
     */
    private void parseScript() {
        docNode = new DocumentNode();
        stack.push(docNode);

        SmartScriptToken token = lexer.nextToken();

        while (token.getType() != EOF) {

            switch (token.getType()) {
            case TEXT:
                parseText();
                break;

            case OPENING_TAG:
                parseTag();
                break;

            default:
                throw new SmartScriptParserException("Invalid input.");
            }

            token = lexer.nextToken();
        }
    }

    /**
     * Helping method that parses text(outside of the tags), creates <code>TextNode</code>
     * and adds it to the children of the last node on the stack.
     */
    private void parseText() {
        SmartScriptToken token = lexer.getToken();
        Node node = new TextNode((String) token.getValue());
        try {
            Node lastOnStack = (Node) stack.peek();
            lastOnStack.addChildNode(node);
        } catch (EmptyStackException e) {
            throw new SmartScriptParserException("Invalid input. Empty stack.");
        }
    }

    /**
     * Helping method used for parsing tag-token.
     *
     * @throws SmartScriptParserException if there is no tag name or if it is not valid
     */
    private void parseTag() {
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        // after opening tag comes tag name
        if (token.getType() == TAG_NAME) {

            String tokenValue = ((String) token.getValue()).toUpperCase();

            switch (tokenValue) {

            case "FOR":
                parseForTag();
                break;

            case "=":
                parseEchoTag();
                break;

            case "END":
                parseEndTag();
                break;

            default:
                throw new SmartScriptParserException("Invalid tag name.");
            }

            lexer.setState(SmartScriptLexerState.TEXT);
        } else {
            throw new SmartScriptParserException("Invalid input. " +
                    "Opening tag should be followed by tag name");
        }
    }

    /**
     * Parses end-tag and pops last node from the stack.
     *
     * @throws SmartScriptParserException if there are some arguments inside end-tag
     *                                    or if there are too much end tags
     */
    private void parseEndTag() {
        SmartScriptToken token = lexer.nextToken();

        if (token.getType() == CLOSING_TAG) {
            try {
                stack.pop();
                if (stack.size() == 0) {
                    throw new SmartScriptParserException("Invalid input, too much END-tags");
                }
            } catch (EmptyStackException e) {
                throw new SmartScriptParserException("Invalid input, too much END-tags");
            }
        } else {
            throw new SmartScriptParserException("Invalid input. " +
                    "End tag cannot have any additional elements.");
        }
    }

    /**
     * Parses echo-tag and constructs {@link EchoNode},
     * which then adds as child of the node that is at
     * the top of the stack.
     */
    private void parseEchoTag() {
        SmartScriptToken token = lexer.nextToken();
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        while (token.getType() != CLOSING_TAG) {
            collection.add(parseElement());
            token = lexer.nextToken();
        }

        Object[] objects = collection.toArray();
        Element[] elements = new Element[objects.length];

        for (int i = 0; i < elements.length; i++) {
            elements[i] = (Element) objects[i];
        }

        Node node = new EchoNode(elements);
        Node lastOnStack = (Node) stack.peek();
        lastOnStack.addChildNode(node);
    }

    /**
     * Parses for-tag and constructs {@link ForLoopNode},
     * which then adds to the children of the node that is
     * at the top of the stack. Then also pushes this <code>ForLoopNode</code>
     * on the stack.
     *
     * @throws SmartScriptParserException if there is too much
     *                                    or too less arguments inside for-tag.
     */
    private void parseForTag() {
        SmartScriptToken token = lexer.nextToken();

        if (token.getType() == VARIABLE) {

            Element startExpression, endExpression, stepExpression;
            ElementVariable variable = parseVariable();
            lexer.nextToken();

            // there must be start and end expression
            try {
                startExpression = parseVarNumOrString();
                lexer.nextToken();
                endExpression = parseVarNumOrString();
                token = lexer.nextToken();
                stepExpression = null;
            } catch (SmartScriptParserException e) {
                throw new SmartScriptParserException("Invalid input. "
                        + "For loop needs to have at least 3 arguments");
            }

            // there is some stepExpression
            if (token.getType() != CLOSING_TAG) {
                stepExpression = parseVarNumOrString();

                token = lexer.nextToken();
                // too many arguments
                if (token.getType() != CLOSING_TAG) {
                    throw new SmartScriptParserException("Illegal input. "
                            + "For loop has too many arguments.");
                }
            }

            Node node = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
            Node lastOnStack = (Node) stack.peek();
            lastOnStack.addChildNode(node);
            stack.push(node);

            // if first element in for tag is not variable
        } else {
            throw new SmartScriptParserException("Illegal input. "
                    + "First element in for loop should be variable.");
        }
    }

    /**
     * Parses variable text and constructs {@link ElementVariable}.
     *
     * @return <code>ElementVariable</code>
     */
    private ElementVariable parseVariable() {
        SmartScriptToken token = lexer.getToken();
        return new ElementVariable((String) token.getValue());
    }

    /**
     * Parses next token as variable, number or string into {@link Element}
     *
     * @return <code>Element</code> constructed based on token type
     * @throws SmartScriptParserException if token doesn't represent variable,
     *                                    number or string
     */
    private Element parseVarNumOrString() {
        SmartScriptToken token = lexer.getToken();
        SmartScriptTokenType type = token.getType();

        // we expect next token to be variable, number, or string
        // so if its not, throw exception
        if (type == FUNCTION || type == OPERATOR) {
            throw new SmartScriptParserException(
                    "Invalid input. For loop elements" + " cannot be a function or operator");
        }
        return parseElement();
    }

    /**
     * Parses next token into corresponding {@link Element}.
     *
     * @return Element constructed based on token type
     * @throws SmartScriptParserException if token is not parsable into any element type
     */
    private Element parseElement() {
        Element element;
        SmartScriptToken token = lexer.getToken();
        SmartScriptTokenType type = token.getType();
        Object tokenValue = token.getValue();

        switch (type) {

        case VARIABLE:
            element = new ElementVariable((String) tokenValue);
            break;

        case INT_NUMBER:
            element = new ElementConstantInteger((int) tokenValue);
            break;

        case DOUBLE_NUMBER:
            element = new ElementConstantDouble((double) tokenValue);
            break;

        case FUNCTION:
            element = new ElementFunction((String) tokenValue);
            break;

        case OPERATOR:
            element = new ElementOperator((String) tokenValue);
            break;

        case TAG_TEXT:
            element = new ElementString((String) tokenValue);
            break;

        default:
            throw new SmartScriptParserException("Invalid input.");
        }

        return element;
    }


    /**
     * @return document node
     */
    public DocumentNode getDocumentNode() {
        return docNode;
    }
}
