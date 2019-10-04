package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Demo program for tree writing of a script document.
 *
 * @author Stjepan Kovačić
 */
public class TreeWriter {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Expected filename as argument.");
            System.exit(1);
        }

        String docBody = "";

        try {
            docBody = Files.readString(Paths.get(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        SmartScriptParser ssp = new SmartScriptParser(docBody);
        ssp.getDocumentNode().accept(new WriterVisitor());
    }

    private static class WriterVisitor implements INodeVisitor {

        @Override
        public void visitTextNode(TextNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            System.out.print(node.toString());
            visitAllChildren(node);
            System.out.print("{$END$}");
        }


        @Override
        public void visitEchoNode(EchoNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            visitAllChildren(node);
        }

        private void visitAllChildren(Node node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    }
}
