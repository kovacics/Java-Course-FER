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
 * Represents 'popd' command.
 * Pops last path from the stack and sets that path as process current directory.
 *
 * @author Stjepan Kovačić
 */
public class PopdShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to pop last path from the stack and set it as current dir.");
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
            return ShellStatus.CONTINUE;
        }

        Path newDir = (Path) stack.pop();
        env.setCurrentDirectory(newDir);
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "popd";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
