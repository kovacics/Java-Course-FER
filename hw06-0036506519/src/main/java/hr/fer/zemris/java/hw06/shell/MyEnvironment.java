package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Concrete implementation of the {@code Environment} interface.
 *
 * @author Stjepan Kovačić
 */
public class MyEnvironment implements Environment {

    /**
     * Prompt symbol of the environment.
     */
    private char promptSymbol = '>';

    /**
     * Multi-lines symbol of the environment.
     */
    private char multiLinesSymbol = '|';

    /**
     * More-lines symbol of the environment.
     */
    private char moreLinesSymbol = '\\';

    /**
     * Scanner used for getting user input.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Sorted map of all shell commands.
     */
    private SortedMap<String, ShellCommand> commands;

    /**
     * Constructs environment and initialize map of the all commands.
     */
    public MyEnvironment() {
        TreeMap<String, ShellCommand> map = new TreeMap<>();

        map.put("exit", new ExitShellCommand());
        map.put("ls", new LsShellCommand());
        map.put("symbol", new SymbolShellCommand());
        map.put("charsets", new CharsetsShellCommand());
        map.put("cat", new CatShellCommand());
        map.put("tree", new TreeShellCommand());
        map.put("copy", new CopyShellCommand());
        map.put("mkdir", new MkdirShellCommand());
        map.put("hexdump", new HexdumpShellCommand());
        map.put("help", new HelpShellCommand());

        commands = Collections.unmodifiableSortedMap(map);
    }

    @Override
    public String readLine() throws ShellIOException {
        try {
            return sc.nextLine();
        } catch (Exception e) {
            throw new ShellIOException(e.getMessage());
        }
    }

    @Override
    public void write(String text) throws ShellIOException {
        try {
            System.out.print(text);
        } catch (Exception e) {
            throw new ShellIOException(e.getMessage());
        }
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        try {
            System.out.println(text);
        } catch (Exception e) {
            throw new ShellIOException(e.getMessage());
        }
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return commands;
    }

    @Override
    public Character getMultilineSymbol() {
        return multiLinesSymbol;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        multiLinesSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    @Override
    public Character getMorelinesSymbol() {
        return moreLinesSymbol;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        moreLinesSymbol = symbol;
    }

    /**
     * Method closes scanner.
     */
    public void closeScanner() {
        sc.close();
    }
}
