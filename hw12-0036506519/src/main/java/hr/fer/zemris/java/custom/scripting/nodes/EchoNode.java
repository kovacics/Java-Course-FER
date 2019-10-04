package hr.fer.zemris.java.custom.scripting.nodes;


import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically.
 *
 * @author Stjepan Kovačić
 */
public class EchoNode extends Node {

    /**
     * Elements of the node.
     */
    private Element[] elements;

    /**
     * Getter of the elements.
     *
     * @return elements of the node
     */
    public Element[] getElements() {
        return elements;
    }

    /**
     * Constructs node with given elements.
     *
     * @param elements elements of the node
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{$= ");
        for (Element e : elements) {
            sb.append(e.asText()).append(" ");
        }
        sb.append("$}");

        return sb.toString();
    }


    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitEchoNode(this);
    }
}
