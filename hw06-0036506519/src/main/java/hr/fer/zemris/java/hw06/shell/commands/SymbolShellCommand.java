package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'symbol' command.
 *
 * @author Stjepan Kovačić
 */
public class SymbolShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to write current symbol used for the special shell symbol, ");
        DESCRIPTION.add("or to change that symbol if given additional argument.");
        DESCRIPTION.add("Command arguments: ");
        DESCRIPTION.add("\t1. special shell symbol - MANDATORY");
        DESCRIPTION.add("\t2. replacement symbol - OPTIONAL");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        if (arguments.isBlank()) {
            env.writeln("1 or 2 arguments expected.");
            return ShellStatus.CONTINUE;
        }
        String[] parts = arguments.split("\\s+");

        switch (parts.length) {
        case 1:
            switch (parts[0]) {
            case "PROMPT":
                env.writeln(String.format("Symbol for %s is '%c'", parts[0], env.getPromptSymbol()));
                return ShellStatus.CONTINUE;

            case "MORELINES":
                env.writeln(String.format("Symbol for %s is '%c'", parts[0], env.getMorelinesSymbol()));
                return ShellStatus.CONTINUE;

            case "MULTILINE":
                env.writeln(String.format("Symbol for %s is '%c'", parts[0], env.getMultilineSymbol()));
                return ShellStatus.CONTINUE;

            default:
                env.writeln("Illegal command. " + parts[0] + " is not a shell symbol");
                return ShellStatus.CONTINUE;
            }

        case 2:
            if (parts[1].length() > 1) {
                env.writeln("Symbol for " + parts[0] + " must be single character.");
                return ShellStatus.CONTINUE;
            }

            switch (parts[0]) {
            case "PROMPT":
                env.writeln(String.format("Symbol for %s changed from'%c' to '%c'",
                        parts[0], env.getPromptSymbol(), parts[1].charAt(0)));
                env.setPromptSymbol(parts[1].charAt(0));
                return ShellStatus.CONTINUE;

            case "MORELINES":
                env.writeln(String.format("Symbol for %s changed from'%c' to '%c'",
                        parts[0], env.getMorelinesSymbol(), parts[1].charAt(0)));
                env.setMorelinesSymbol(parts[1].charAt(0));
                return ShellStatus.CONTINUE;

            case "MULTILINE":
                env.writeln(String.format("Symbol for %s changed from'%c' to '%c'",
                        parts[0], env.getMultilineSymbol(), parts[1].charAt(0)));
                env.setMultilineSymbol(parts[1].charAt(0));
                return ShellStatus.CONTINUE;

            default:
                env.writeln("Illegal command. " + parts[0] + " is not a shell symbol");
                return ShellStatus.CONTINUE;
            }

        default:
            env.writeln("Symbol command must have 1 or 2 arguments.");
            return ShellStatus.CONTINUE;
        }
    }


    @Override
    public String getCommandName() {
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
