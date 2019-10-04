package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents 'pwd' command.
 * Prints current directory path to the environment.
 *
 * @author Stjepan Kovačić
 */
public class PwdShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to print current dir path.");
        DESCRIPTION.add("Command expects 0 arguments. ");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 0) {
            env.writeln("Pwd command expects 0 arguments");
            return ShellStatus.CONTINUE;
        }

        env.writeln(env.getCurrentDirectory().toString());
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "pwd";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
