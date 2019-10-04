package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'exit' command.
 *
 * @author Stjepan Kovačić
 */
public class ExitShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to terminate shell.");
        DESCRIPTION.add("Command expects 0 arguments.");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return ShellStatus.TERMINATE;
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
