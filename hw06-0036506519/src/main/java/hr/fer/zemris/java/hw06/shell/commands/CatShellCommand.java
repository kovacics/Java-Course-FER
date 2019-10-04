package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'cat' command.
 *
 * @author Stjepan Kovačić
 */
public class CatShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to write content of the file to a console.");
        DESCRIPTION.add("Command arguments: ");
        DESCRIPTION.add("\t1. path to the file - MANDATORY");
        DESCRIPTION.add("\t2. charset used for interpreting file - OPTIONAL");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);
        Charset charset;

        switch (parts.length) {
        case 1:
            charset = Charset.defaultCharset();
            break;
        case 2:
            charset = Charset.forName(parts[1]);
            break;
        default:
            env.writeln("Illegal command, cat should be followed by 1 or 2 arguments");
            return ShellStatus.CONTINUE;
        }

        File file = new File(parts[0]);
        if (file.isDirectory()) {
            env.writeln("Cat command argument cannot be directory.");
            return ShellStatus.CONTINUE;
        }
        if (!file.exists()) {
            env.writeln("File doesn't exist.");
            return ShellStatus.CONTINUE;
        }
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            env.writeln(new String(bytes, charset));
        } catch (IOException e) {
            env.writeln("Unable to read/write from the given file");
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
