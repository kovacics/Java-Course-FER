package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents 'cd' command.
 * Changes current directory path.
 *
 * @author Stjepan Kovačić
 */
public class CdShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to change current working directory:");
        DESCRIPTION.add("Command arguments: ");
        DESCRIPTION.add("\t1. path to the file - absolute or relative to current dir");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 1) {
            env.writeln("Cd command expects 1 argument.");
            return ShellStatus.CONTINUE;
        }

        Path newDir = Paths.get(parts[0]);
        if (!newDir.isAbsolute()) {
            newDir = env.getCurrentDirectory().resolve(parts[0]);
        }
        if (newDir.toFile().exists()) {
            env.setCurrentDirectory(newDir);
        } else {
            env.writeln("Directory doesn't exist.");
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "cd";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
