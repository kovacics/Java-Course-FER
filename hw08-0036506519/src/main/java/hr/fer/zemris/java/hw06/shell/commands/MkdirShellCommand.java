package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'mkdir' command.
 *
 * @author Stjepan Kovačić
 */
public class MkdirShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to create directory.");
        DESCRIPTION.add("Command expects 1 argument: ");
        DESCRIPTION.add("\t1. directory name which should be created");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 1) {
            env.writeln("Command expected one argument.");
            return ShellStatus.CONTINUE;
        }

        File file = env.getCurrentDirectory().resolve(parts[0]).toFile();
        if (file.exists()) {
            env.writeln("Directory already exist.");
            return ShellStatus.CONTINUE;
        }

        if (file.mkdirs()) {
            env.writeln("Directory created.");
        } else {
            env.writeln("Directory creation failed.");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
