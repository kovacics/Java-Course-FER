package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface represent one shell command.
 *
 * @author Stjepan Kovačić
 */
public interface ShellCommand {

    /**
     * Executes command in the given environment with the given arguments.
     *
     * @param env       environment in which to execute command
     * @param arguments arguments of the command
     * @return status for the shell
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Returns name of the command.
     *
     * @return command name
     */
    String getCommandName();

    /**
     * Returns full description of the command.
     *
     * @return list of strings which describes the command
     */
    List<String> getCommandDescription();
}
