package hr.fer.zemris.java.hw03.prob1.demo;

import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.LexerState;
import hr.fer.zemris.java.hw03.prob1.TokenType;

/**
 * Demo program for {@code Lexer} class.
 *
 * @author Stjepan Kovačić
 */
public class LexerDemo {

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        String[] testData = {
                "Ovo je 123ica, ab57.\nKraj",
                "\\1\\2 ab\\\\\\2c\\3\\4d",
                "...??b\\1a      bla \\\\     ",
                " 12 13 14 15.5"
        };

        for (String s : testData) {
            Lexer lexer = new Lexer(s);
            lexer.setState(LexerState.BASIC);

            while (lexer.nextToken().getType() != TokenType.EOF) {
                System.out.println(lexer.getToken().getValue() + " " + lexer.getToken().getType());
            }
            System.out.println();
        }
    }
}
