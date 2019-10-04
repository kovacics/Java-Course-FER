package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node representing an entire document.
 *
 * @author Stjepan Kovačić
 */
public class DocumentNode extends Node {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfChildren(); i++) {
            sb.append(getChild(i));
        }
        return sb.toString();
    }
}
