package hr.fer.zemris.java.custom.scripting.nodes;


import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 *
 * @author Stjepan Kovačić
 */
public class ForLoopNode extends Node {

    /**
     * Variable of the for loop node.
     */
    private ElementVariable variable;

    /**
     * Start expression of the for loop node.
     */
    private Element startExpression;

    /**
     * End expression of the for loop node.
     */
    private Element endExpression;

    /**
     * Step expression of the for loop node.
     */
    private Element stepExpression;


    /**
     * Constructs node with for specified attributes.
     *
     * @param variable        variable of the for loop
     * @param startExpression start expression of the for loop
     * @param endExpression   end expression of the for loop
     * @param stepExpression  step expression of the loop
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
                       Element stepExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Getter of the for loop node variable.
     *
     * @return variable of the node
     */
    public ElementVariable getVariable() {
        return variable;
    }


    /**
     * Getter of the for loop node start expression.
     *
     * @return start expression of the node
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter of the for loop node end expression.
     *
     * @return end expression of the node
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Getter of the for loop node step expression.
     *
     * @return step expression of the node
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        text.append("{$ FOR ");

        text.append(variable.asText()).
                append(" ").
                append(startExpression.asText()).
                append(" ").
                append(endExpression.asText());

        if (stepExpression != null) {
            text.append(" ").append(stepExpression.asText());
        }

        text.append(" $}");

        return text.toString();
    }


    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitForLoopNode(this);
    }
}
