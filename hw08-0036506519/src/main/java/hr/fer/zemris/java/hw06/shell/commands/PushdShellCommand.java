package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents 'pushd' command.
 * Changes current directory path but saves the old one in the shared data.
 *
 * @author Stjepan Kovačić
 */
public class PushdShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to push current dir path to the stack and sets given path as current dir.");
        DESCRIPTION.add("Command expects 1 argument:  ");
        DESCRIPTION.add("\t1. new current dir path");
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);
        if (parts.length != 1) {
            env.writeln("Command expects 1 argument.");
            return ShellStatus.CONTINUE;
        }
        File newDir = env.getCurrentDirectory().resolve(parts[0]).toFile();

        if (!newDir.exists()) {
            env.writeln("Given directory doesn't exist.");
            return ShellStatus.CONTINUE;
        }

        if (!newDir.isDirectory()) {
            env.writeln("Given file is not a directory.");
            return ShellStatus.CONTINUE;
        }

        Stack stack = (Stack) env.getSharedData("cdstack");
        if (stack == null) {
            env.setSharedData("cdstack", new Stack<>());
        }
        ((Stack<Path>) env.getSharedData("cdstack")).push(env.getCurrentDirectory());
        env.setCurrentDirectory(newDir.toPath());
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "pushd";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
