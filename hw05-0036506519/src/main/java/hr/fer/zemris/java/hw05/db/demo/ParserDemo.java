package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.parser.QueryParser;

/**
 * Demo class.
 */
public class ParserDemo {

    /**
     * Main method of the program.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
        System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
        System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
        System.out.println("size: " + qp1.getQuery().size()); // 1
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
        System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
        // System.out.println(qp2.getQueriedJMBAG()); // would throw!
        System.out.println("size: " + qp2.getQuery().size()); // 2
    }
}
