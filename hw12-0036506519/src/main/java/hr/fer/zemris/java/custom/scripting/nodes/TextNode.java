package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node representing a piece of textual data.
 *
 * @author Stjepan Kovačić
 */
public class TextNode extends Node {

    /**
     * Text value of the node.
     */
    private String text;

    /**
     * Constructs node with given text value.
     *
     * @param text text value of the node.
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * @return text value of the node
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        String result = text;

        result = result.replace("\\", "\\\\");
        result = result.replace("{", "\\{");

        return result;
    }


    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }
}
