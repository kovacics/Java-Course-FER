package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw06.shell.namebuilder.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents 'massrename' command.
 * Command offers few functionality.
 * Command expects minimal 4 arguments.
 * <li>DIR1 - source directory</li>
 * <li>DIR2 - destination directory, can be same as DIR1</li>
 * <li>CMD - concrete command which should be executed</li>
 * <li>MASK - regular expression written by {@code Pattern} class rules</li>
 * <li>other - additional parameter for renaming/moving</li>
 *
 * <p>
 * CMD argument is string that represent concrete command to be executed
 * and can be :
 * <li>filter - filters all file names from the DIR1 with MASK</li>
 * <li>groups - groups every file from DIR1 into groups defined with MASK,
 * number of groups will be numberOfGroupsDefinedInMASK + 1 </li>
 * <li>show - expects one more argument in other part, a EXPRESSION,
 * shows how would file names look if renamed with MASK+EXPRESSION combination</li>
 * <li>execute - executes renaming of all files from the DIR1 based on given
 * MASK and EXPRESSION (in other part) and places renamed files in DIR2</li>
 * </p>
 *
 * <p>
 * For using MASK - see {@link java.util.regex.Pattern}
 * </p>
 * <p>
 * "other" is part which tells how renamed file should look like.
 * It can be normal text and expressions which use groups from the MASK part:
 * <li>
 * ${groupNumber}
 * </li>
 * <li>
 * ${groupNumber, minimalWidth}, e.g. ${1, 3}, where minimal width must be 0
 * or positive number, padding will be ' '
 * </li>
 * <li>
 * ${groupNumber, minimalWidth}, e.g. ${1, 03}, where minimal width must be 0
 * or positive number, padding will be '0'
 * </li>
 * </p>
 *
 * @author Stjepan Kovačić
 */
public class MassrenameShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used for mass renaming/relocating files.");
        DESCRIPTION.add("Command expects minimum 4 arguments: ");
        DESCRIPTION.add("\tDIR1 - source directory");
        DESCRIPTION.add("\tDIR2 - destination directory, can be same as DIR1");
        DESCRIPTION.add("\t\tCMD can be: filter, groups, show or execute.");
        DESCRIPTION.add("\tCMD - concrete command which should be executed");
        DESCRIPTION.add("\tMASK - regular expression written by Pattern class rules");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);
        if (parts.length < 4 || parts.length > 5) {
            env.writeln("Illegal number of arguments." + parts.length);
            return ShellStatus.CONTINUE;
        }

        Path sourceDir = env.getCurrentDirectory().resolve(parts[0]);
        Path destDir = env.getCurrentDirectory().resolve(parts[1]);
        String mask = parts[3];

        if (parts.length == 4) {
            switch (parts[2]) {
            case "filter":
                filter(sourceDir, mask).forEach((file) -> env.writeln(file.toString()));
                return ShellStatus.CONTINUE;
            case "groups":
                groups(sourceDir, mask, env);
                return ShellStatus.CONTINUE;
            }
        } else {
            String expression = parts[4];
            var parser = new NameBuilderParser(expression);
            var builder = parser.getNameBuilder();

            switch (parts[2]) {
            case "show":
                show(sourceDir, mask, env, builder);
                return ShellStatus.CONTINUE;
            case "execute":
                execute(sourceDir, destDir, mask, env, builder);
                return ShellStatus.CONTINUE;
            }
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Private method for processing 'groups' command.
     *
     * @param sourceDir source directory
     * @param mask      mask
     * @param env       environment
     */
    private void groups(Path sourceDir, String mask, Environment env) {
        filter(sourceDir, mask).forEach(
                (file) -> {
                    env.write(file.toString());
                    for (int i = 0; i <= file.numberOfGroups(); i++) {
                        env.write(" " + i + ": " + file.group(i));
                    }
                    env.writeln("");
                });
    }

    /**
     * Private method for processing 'show' command.
     *
     * @param sourceDir source directory
     * @param mask      mask
     * @param env       environment
     * @param builder   name builder
     */
    private void show(Path sourceDir, String mask, Environment env, NameBuilder builder) {
        filter(sourceDir, mask).forEach((result) -> {

            StringBuilder sb = new StringBuilder();
            builder.execute(result, sb);
            env.writeln(result.toString() + " => " + sb.toString());
        });
    }

    /**
     * Private method for processing 'execute' command.
     *
     * @param sourceDir source directory
     * @param destDir   destination directory
     * @param mask      mask
     * @param env       environment
     * @param builder   name builder
     */
    private void execute(Path sourceDir, Path destDir, String mask, Environment env, NameBuilder builder) {
        filter(sourceDir, mask).forEach((result) -> {

            StringBuilder sb = new StringBuilder();
            builder.execute(result, sb);
            String newName = sb.toString();
            env.writeln(result.toString() + " => " + newName);
            try {
                Path from = result.getFile().toPath();
                Path to = destDir.resolve(newName);

                if (!destDir.toFile().exists()) {
                    destDir.toFile().mkdirs();
                }

                Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                env.writeln("Error while moving file");
            }
        });
    }

    @Override
    public String getCommandName() {
        return "massrename";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }

    /**
     * Creates list of filter results for files whose names satisfied given pattern.
     *
     * @param dir     directory of the files
     * @param pattern pattern to satisfy
     * @return list of filter results
     */
    public List<FilterResult> filter(Path dir, String pattern) {
        File[] files = dir.toFile().listFiles();
        List<FilterResult> accepted = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory() && file.getName().matches(pattern)) {
                    accepted.add(new FilterResult(file, pattern));
                }
            }
        }
        return accepted;
    }
}
