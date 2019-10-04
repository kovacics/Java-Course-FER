package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stjepan Kovačić
 */
public class HelpShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to help user with using the shell.");
        DESCRIPTION.add("Command expects 0 or 1 argument: ");
        DESCRIPTION.add("\t0 arguments: Listing all available commands.");
        DESCRIPTION.add("\t1 argument: COMMAND - Writes description of the given command.");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        switch (parts.length) {
        case 0:
            env.writeln("Available commands:");
            for (String command : env.commands().keySet()) {
                env.writeln(command);
            }
            return ShellStatus.CONTINUE;
        case 1:
            ShellCommand command = env.commands().get(parts[0]);
            if (command == null) {
                env.writeln("Such command doesn't exist");
            } else {
                command.getCommandDescription().forEach(System.out::println);
            }
            return ShellStatus.CONTINUE;
        default:
            env.writeln("Help command expected 0 or 1 argument.");
            return ShellStatus.CONTINUE;
        }
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
