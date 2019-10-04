package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'charsets' command.
 *
 * @author Stjepan Kovačić
 */
public class CharsetsShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command lists all available charsets.");
        DESCRIPTION.add("Command expects 0 arguments.");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isBlank()) {
            env.writeln("Charsets method expected zero arguments.");
            return ShellStatus.CONTINUE;
        }
        Charset.availableCharsets().keySet().forEach(System.out::println);
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charsets";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
