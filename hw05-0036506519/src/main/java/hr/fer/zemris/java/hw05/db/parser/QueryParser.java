package hr.fer.zemris.java.hw05.db.parser;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hr.fer.zemris.java.hw05.db.ComparisonOperators.*;
import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;
import static hr.fer.zemris.java.hw05.db.parser.QueryTokenType.*;


/**
 * Class represents parser which parses given string form of the query
 * into {@code List} of one or more objects of type {@code ConditionalExpression}.
 */
public class QueryParser {

    /**
     * Query string.
     */
    private String query;

    /**
     * Lexer used to tokenize query.
     */
    private QueryLexer lexer;

    /**
     * Field getter.
     */
    private IFieldValueGetter fieldGetter;

    /**
     * String literal.
     */
    private String stringLiteral;

    /**
     * Comparison operator.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * List of all query expressions.
     */
    private List<ConditionalExpression> expressions;

    /**
     * Map of all operators.
     */
    private static HashMap<String, IComparisonOperator> operators;

    /**
     * Map of all field geters.
     */
    private static HashMap<String, IFieldValueGetter> fieldGetters;

    static {
        initializeFieldGetters();
        initializeOperators();
    }

    /**
     * Constructor specifying query to be parsed.
     *
     * @param query query to be parsed
     */
    public QueryParser(String query) {
        this.query = query;
        expressions = new ArrayList<>();
        try {
            parse(query);
        } catch (QueryLexerException ex) {
            throw new QueryParserException(ex.getMessage());
        }
    }

    /**
     * Parses given {@code query}.
     *
     * @param query query to ne parsed
     * @throws QueryParserException if query is not valid
     */
    private void parse(String query) {
        lexer = new QueryLexer(query);
        QueryToken token = lexer.nextToken();

        while (true) {
            if (token.getType() == WORD) {
                parseFieldName();
                token = lexer.nextToken();

                if (token.getType() == OPERATOR || token.getType() == WORD) {
                    parseComparisonOperator();
                    token = lexer.nextToken();

                    if (token.getType() == STRING_LITERAL) {
                        stringLiteral = (String) token.getValue();
                        expressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
                        token = lexer.nextToken();
                        if (token.getType() == EOF) {
                            break;
                        } else {
                            if (token.getType() != WORD || !((String) token.getValue()).equalsIgnoreCase("AND")) {
                                throw new QueryParserException("Invalid query, expression should be seperated by 'and' keyword");
                            }
                            token = lexer.nextToken();
                        }
                    } else throw new QueryParserException("Cannot parse to query, " +
                            "third expression argument should be string literal");
                } else throw new QueryParserException("Cannot parse to query, " +
                        "second expression argument should be comparing operator");
            } else throw new QueryParserException("Cannot parse to query, " +
                    "first expression argument should be word(field)");
        }
    }

    /**
     * Private method that parses field name.
     *
     * @throws QueryParserException if field name is invalid
     */
    private void parseFieldName() {
        String word = (String) lexer.getToken().getValue();
        fieldGetter = fieldGetters.get(word);

        if (fieldGetter == null) {
            throw new QueryParserException("Not valid field name.");
        }
    }

    /**
     * Private method that parses comparing operator.
     *
     * @throws QueryParserException if comparing operator is invalid
     */
    private void parseComparisonOperator() {
        String operator = (String) lexer.getToken().getValue();
        comparisonOperator = operators.get(operator);

        if (comparisonOperator == null) {
            throw new QueryParserException("Not valid comparison operator.");
        }
    }

    /**
     * Checks if parsed query is direct query which means following conditions are satisfied:
     * <li>Only one {@code ConditionalExpression}</li>
     * <li>Field name is {@code jmbag}</li>
     * <li>Comparing operator is '='</li>
     * Which means query looks like this: <b>jmbag="xxx"</b>
     *
     * @return {@code true} if query is directive, {@code false} if is not
     */
    public boolean isDirectQuery() {
        return expressions.size() == 1 && expressions.get(0).getFieldGetter() == JMBAG
                && expressions.get(0).getComparisonOperator() == EQUALS;
    }

    /**
     * Method returns string literal which was given in a direct query.
     * If query is not direct, exception is being thrown.
     *
     * @return string literal of the direct query, which actually is the {@code jmbag} value
     * @throws IllegalStateException if query was not direct
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Cannot get queried JMBAG for not direct query");
        }
        return query.split("\"")[1];
    }

    /**
     * Returns list of parsed conditional expressions from the query.
     * If list's size is 1, then query was a direct one.
     *
     * @return list of conditional expressions
     */
    public List<ConditionalExpression> getQuery() {
        return expressions;
    }

    /**
     * Private method that initialize map of the field getters.
     */
    private static void initializeFieldGetters() {
        fieldGetters = new HashMap<>();
        fieldGetters.put("firstName", FIRST_NAME);
        fieldGetters.put("lastName", LAST_NAME);
        fieldGetters.put("jmbag", JMBAG);
    }

    /**
     * Private method that initialize map of the operators.
     */
    private static void initializeOperators() {
        operators = new HashMap<>();
        operators.put("=", EQUALS);
        operators.put("<", LESS);
        operators.put("<=", LESS_OR_EQUALS);
        operators.put(">", GREATER);
        operators.put(">=", GREATER_OR_EQUALS);
        operators.put("!=", NOT_EQUALS);
        operators.put("LIKE", LIKE);
    }
}
