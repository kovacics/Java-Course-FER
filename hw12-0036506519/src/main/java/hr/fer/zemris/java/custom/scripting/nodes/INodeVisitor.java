package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents visitor of the nodes.
 *
 * @author Stjepan Kovačić
 */
public interface INodeVisitor {

    void visitTextNode(TextNode node);

    void visitForLoopNode(ForLoopNode node);

    void visitEchoNode(EchoNode node);

    void visitDocumentNode(DocumentNode node);
}