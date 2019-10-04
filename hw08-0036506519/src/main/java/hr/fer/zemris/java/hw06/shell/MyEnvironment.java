package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    private Scanner sc = new Scanner(System.in);

    /**
     * Sorted map of all shell commands.
     */
    private SortedMap<String, ShellCommand> commands;

    /**
     * Map that stores shared data.
     */
    private Map<String, Object> sharedData = new HashMap<>();

    /**
     * Current working directory.
     */
    private Path currentDir;

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

        map.put("pwd", new PwdShellCommand());
        map.put("cd", new CdShellCommand());
        map.put("pushd", new PushdShellCommand());
        map.put("popd", new PopdShellCommand());
        map.put("listd", new ListdShellCommand());
        map.put("dropd", new DropdShellCommand());

        commands = Collections.unmodifiableSortedMap(map);
        currentDir = Paths.get(".").toAbsolutePath().normalize();
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

    @Override
    public Path getCurrentDirectory() {
        return currentDir;
    }

    @Override
    public void setCurrentDirectory(Path path) {
        currentDir = path.toAbsolutePath().normalize();
    }

    @Override
    public Object getSharedData(String key) {
        return sharedData.get(key);
    }

    @Override
    public void setSharedData(String key, Object value) {
        sharedData.put(key, value);
    }

    /**
     * Method closes scanner.
     */
    public void closeScanner() {
        sc.close();
    }
}
