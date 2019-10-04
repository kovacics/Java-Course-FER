package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Class represents engine for executing smart script.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptEngine {

    /**
     * Document node which should be executed.
     */
    private DocumentNode documentNode;

    /**
     * Request context.
     */
    private RequestContext requestContext;

    /**
     * Helping multistack.
     */
    private ObjectMultistack multistack = new ObjectMultistack();

    /**
     * Static map of all functions.
     */
    private static final Map<String, BiConsumer<Stack<Object>, RequestContext>> FUNCTIONS;

    /**
     * Static map of all operators.
     */
    private static final Map<String, BiFunction<Object, Object, Object>> OPERATIONS;

    /**
     * Constructs smart script engine with specified document node and request context.
     *
     * @param documentNode   document node
     * @param requestContext request context
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    /**
     * Executes smart script.
     */
    public void execute() {
        documentNode.accept(visitor);
    }

    //******************
    //    VISITOR
    //******************


    /**
     * Visitor class used for script execution.
     */
    private INodeVisitor visitor = new INodeVisitor() {

        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            Object start = getExpressionValue(node.getStartExpression());
            Object end = getExpressionValue(node.getEndExpression());
            Object step = node.getStepExpression() == null ? 0 : getExpressionValue(node.getStepExpression());

            ValueWrapper variable = new ValueWrapper(start);
            multistack.push(node.getVariable().getName(), variable);

            while (variable.numCompare(end) < 1) {
                visitAllChildren(node);
                variable.add(step);
            }

            multistack.pop(node.getVariable().getName());
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<Object> tempStack = new Stack<>();

            for (Element e : node.getElements()) {
                if (e instanceof ElementConstantDouble) {
                    tempStack.push(((ElementConstantDouble) e).getValue());
                } else if (e instanceof ElementConstantInteger) {
                    tempStack.push(((ElementConstantInteger) e).getValue());
                } else if (e instanceof ElementString) {
                    tempStack.push(((ElementString) e).getValue());
                } else if (e instanceof ElementVariable) {
                    tempStack.push(multistack.peek(((ElementVariable) e).getName()).getValue());
                } else if (e instanceof ElementOperator) {
                    Object second = tempStack.pop();
                    Object first = tempStack.pop();
                    String operator = ((ElementOperator) e).getSymbol();

                    tempStack.push(OPERATIONS.get(operator).apply(first, second));
                } else if (e instanceof ElementFunction) {
                    String function = ((ElementFunction) e).getName();
                    FUNCTIONS.get(function).accept(tempStack, requestContext);
                }
            }
            printStackFromBottom(tempStack);
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            visitAllChildren(node);
        }

        /**
         * Helping method that visits all node children.
         * @param node node whose children need to be visited
         */
        private void visitAllChildren(Node node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    };

    /**
     * Helping method that returns numeric value of the expression.
     *
     * @param expression expression
     * @return value of the expression
     * @throws RuntimeException if expression doesn't represent number
     */
    private double getExpressionValue(Element expression) {
        try {
            if (expression instanceof ElementConstantDouble) {
                return ((ElementConstantDouble) expression).getValue();
            } else if (expression instanceof ElementConstantInteger) {
                return ((ElementConstantInteger) expression).getValue();
            } else if (expression instanceof ElementString) {
                return Double.parseDouble(((ElementString) expression).getValue());
            } else if (expression instanceof ElementVariable) {
                return Double.parseDouble(String.valueOf(multistack.peek(((ElementVariable) expression).getName()).getValue()));
            } else {
                throw new RuntimeException("Invalid expression type.");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Expression cannot be parse to number.");
        }
    }

    //*****************************
    //    HELPING METHODS
    //*****************************

    /**
     * Helping method that prints given stack from bottom to the top.
     *
     * @param stack stack to print
     */
    private void printStackFromBottom(Stack<Object> stack) {
        for (Object obj : stack) {
            try {
                requestContext.write(String.valueOf(obj).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //  HELPING STATIC METHODS

    /**
     * Static helping method that returns String representation of the given object.
     *
     * @param value string or number value
     * @return given string or string with given number value
     * @throws RuntimeException if given object is not string or number
     */
    private static String getStringOrNumber(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Number) {
            return String.valueOf(value);
        } else {
            throw new RuntimeException("Invalid object type, your type was: " + value.getClass().getName());
        }
    }

    /**
     * Method returns string value if given object is of type String, or throws exception if not.
     *
     * @param value object for which to check if string
     * @return string value of the object
     * @throws RuntimeException if object is not string
     */
    private static String getString(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else {
            throw new RuntimeException(value + " - given object isn't of type String, it is: " + value.getClass().getName());
        }
    }

    /**
     * Static helping method that returns double value of the given object.
     *
     * @param value string parsable to double or number
     * @return double value of the given object
     * @throws RuntimeException if given object is not string parsable to double
     *                          or number
     */
    private static double getValue(Object value) {
        if (value instanceof String) {
            return Double.parseDouble((String) value);
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            throw new RuntimeException("Invalid object type. Your object type was: " + value.getClass().getName());
        }
    }

    //*****************************
    //    MAPS INITIALIZATION
    //*****************************

    static {
        FUNCTIONS = new HashMap<>();

        FUNCTIONS.put("sin", (stack, rc) -> {
            double value = getValue(stack.pop());

            stack.push(Math.sin(Math.toRadians(value)));
        });

        FUNCTIONS.put("decfmt", (stack, rc) -> {
            String format = getString(stack.pop());
            DecimalFormat formatter = new DecimalFormat(format);
            double toFormat = getValue(stack.pop());

            stack.push(formatter.format(toFormat));
        });

        FUNCTIONS.put("dup", (stack, rc) -> {
            Object value = stack.pop();
            stack.push(value);
            stack.push(value);
        });

        FUNCTIONS.put("swap", (stack, rc) -> {
            Object a = stack.pop();
            Object b = stack.pop();
            stack.push(a);
            stack.push(b);
        });

        FUNCTIONS.put("setMimeType", (stack, rc) -> {
            rc.setMimeType(getString(stack.pop()));
        });

        FUNCTIONS.put("paramGet", (stack, rc) -> {
            var defV = stack.pop();
            String name = getString(stack.pop());
            String value = rc.getParameter(name);

            stack.push(value == null ? defV : value);
        });

        FUNCTIONS.put("pparamGet", (stack, rc) -> {
            var defV = stack.pop();
            String name = getString(stack.pop());
            String value = rc.getPersistentParameter(name);
            stack.push(value == null ? defV : value);
        });

        FUNCTIONS.put("pparamSet", (stack, rc) -> {
            String name = getString(stack.pop());
            String value = getStringOrNumber(stack.pop());

            rc.setPersistentParameter(name, value);
        });

        FUNCTIONS.put("pparamDel", (stack, rc) -> {
            rc.removePersistentParameter((String) stack.pop());
        });

        FUNCTIONS.put("tparamGet", (stack, rc) -> {
            var defV = stack.pop();
            String name = getString(stack.pop());
            var value = rc.getTemporaryParameter(name);
            stack.push(value == null ? defV : value);
        });

        FUNCTIONS.put("tparamSet", (stack, rc) -> {
            String name = getString(stack.pop());
            String value = getStringOrNumber(stack.pop());
            rc.setTemporaryParameter(name, value);
        });

        FUNCTIONS.put("tparamDel", (stack, rc) -> {
            rc.removeTemporaryParameter((String) stack.pop());
        });

        OPERATIONS = new HashMap<>();

        OPERATIONS.put("+", (first, second) -> {
            ValueWrapper firstV = new ValueWrapper(first);
            firstV.add(second);
            return firstV.getValue();
        });

        OPERATIONS.put("-", (first, second) -> {
            ValueWrapper firstV = new ValueWrapper(first);
            firstV.subtract(second);
            return firstV.getValue();
        });

        OPERATIONS.put("*", (first, second) -> {
            ValueWrapper firstV = new ValueWrapper(first);
            firstV.multiply(second);
            return firstV.getValue();
        });

        OPERATIONS.put("/", (first, second) -> {
            ValueWrapper firstV = new ValueWrapper(first);
            firstV.divide(second);
            return firstV.getValue();
        });
    }
}
