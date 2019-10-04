package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents 'dropd' command.
 * Pops(removes) last path from the stack.
 *
 * @author Stjepan Kovačić
 */
public class DropdShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to pop last path from the stack.");
        DESCRIPTION.add("Command expects 0 arguments. ");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 0) {
            env.writeln("Popd command expects 0 arguments");
            return ShellStatus.CONTINUE;
        }

        Stack stack = (Stack) env.getSharedData("cdstack");

        if (stack.isEmpty()) {
            env.writeln("Cannot pop path, stack is empty.");
        } else {
            stack.pop();
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "dropd";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
