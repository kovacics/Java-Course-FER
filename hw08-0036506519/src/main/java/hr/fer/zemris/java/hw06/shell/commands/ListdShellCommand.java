package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents 'listd' command.
 * Prints all paths on the stack (from the last added) to the environment.
 *
 * @author Stjepan Kovačić
 */
public class ListdShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to list all paths on the stack(from the last added).");
        DESCRIPTION.add("Command expects 0 arguments.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 0) {
            env.writeln("Listd command expects 0 arguments");
            return ShellStatus.CONTINUE;
        }

        Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

        if (stack == null || stack.isEmpty()) {
            env.writeln("Nema pohranjenih direktorija");
        } else {
            stack.forEach((path) -> env.writeln(path.toString()));
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "listd";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
