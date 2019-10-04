package hr.fer.zemris.java.hw06.shell.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser class for parsing user input, containing normal and quoted strings.
 *
 * @author Stjepan Kovačić
 */
public class InputParser {

    /**
     * Current char index.
     */
    int currentIndex;

    /**
     * Input char array.
     */
    private char[] input;

    /**
     * Array of parsed arguments.
     */
    private String[] arguments;

    /**
     * Method parses input string and returns array of parsed arguments.
     *
     * @return array of arguments
     */
    public String[] parseArguments(String input) {
        this.input = input.trim().toCharArray();
        List<String> parts = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        while (inRange()) {
            sb.setLength(0);
            skipSpaces();

            // quoted string
            if (currentCharIs('\"')) {
                currentIndex++;
                while (inRange() && !currentCharIs('\"')) {
                    //possible escaping
                    if (currentCharIs('\\')) {
                        currentIndex++;
                        if (!inRange()) {
                            throw new InputParserException("Closing quote is missing");
                        }
                        if (currentCharIs('\"') || currentCharIs('\\')) {
                            sb.append(getCurrentChar());
                        } else {
                            sb.append('\\').append(getCurrentChar());
                        }
                    } else {
                        sb.append(getCurrentChar());
                    }
                    currentIndex++;
                }
                if (!inRange()) {
                    throw new InputParserException("Closing quote is missing");
                }
                currentIndex++;

                if (inRange() && !currentCharIs(' ')) {
                    throw new InputParserException("Invalid quote input! Only following whitespace allowed.");
                }
                parts.add(sb.toString());
            }
            // normal string
            else {
                while (inRange() && !currentCharIs(' ') && !currentCharIs('\"')) {
                    sb.append(getCurrentChar());
                    currentIndex++;
                }
                parts.add(sb.toString());
            }
        }
        arguments = parts.toArray(String[]::new);
        return arguments;
    }

    /**
     * Getter of the parsed input arguments.
     *
     * @return arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    //*************************************
    //          HELPING METHODS
    //*************************************


    /**
     * Helping method that skips all spaces and position index on the first
     * character that is not space.
     */
    private void skipSpaces() {
        while (inRange() && Character.isWhitespace(getCurrentChar())) {
            currentIndex++;
        }
    }

    /**
     * Helping method that checks if current char is equal to passed char.
     *
     * @param c char to check if equal to current char
     * @return {@code true} if they are equal, otherwise {@code false}
     */
    private boolean currentCharIs(char c) {
        return getCurrentChar() == c;
    }

    /**
     * Helping method that returns current char.
     *
     * @return current char;
     */
    private char getCurrentChar() {
        return input[currentIndex];
    }

    /**
     * Checks if current index is in range.
     *
     * @return true if in range
     */
    private boolean inRange() {
        return currentIndex < input.length;
    }
}
