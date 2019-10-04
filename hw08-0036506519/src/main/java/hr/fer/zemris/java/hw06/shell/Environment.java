package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface represent environment in which shell operates.
 *
 * @author Stjepan Kovačić
 */
public interface Environment {

    /**
     * Reads one line.
     *
     * @return read line
     * @throws ShellIOException if error happened while reading a line
     */
    String readLine() throws ShellIOException;

    /**
     * Writes text but does not append '\n' on the end.
     *
     * @param text text to write
     * @throws ShellIOException if error happened while writing
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes one line.
     *
     * @param text line to write
     * @throws ShellIOException if error happened while writing
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns all commands.
     *
     * @return sorted map of all commands.
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Returns current multi line symbol.
     *
     * @return multi line symbol
     */
    Character getMultilineSymbol();

    /**
     * Sets new multi line symbol.
     *
     * @param symbol symbol to be set
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Returns current prompt symbol.
     *
     * @return prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets new prompt symbol.
     *
     * @param symbol symbol to be set
     */
    void setPromptSymbol(Character symbol);

    /**
     * Returns current more lines symbol.
     *
     * @return more lines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Sets new more lines symbol.
     *
     * @param symbol symbol to be set
     */
    void setMorelinesSymbol(Character symbol);

    /**
     * Returns absolute normalized path of the current process directory.
     *
     * @return path of the current directory
     */
    Path getCurrentDirectory();

    /**
     * Sets given directory path as process current directory.
     *
     * @param path directory which should be set as process current directory
     * @throws RuntimeException if given directory doesn't exist
     */
    void setCurrentDirectory(Path path);

    /**
     * Gets data that is shared among all commands.
     *
     * @param key key of the map where data is stored
     * @return shared data for given key
     */
    Object getSharedData(String key);

    /**
     * Puts some data in the map of shared data.
     *
     * @param key   key of the map for where to put data
     * @param value data
     */
    void setSharedData(String key, Object value);
}
