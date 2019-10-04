package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'copy' command.
 *
 * @author Stjepan Kovačić
 */
public class CopyShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to copy given file on the given destination.");
        DESCRIPTION.add("Command expects 2 arguments: ");
        DESCRIPTION.add("\t1. path to the source file");
        DESCRIPTION.add("\t2. path to the destination file(or directory) ");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 2) {
            env.writeln("Copy method expects 2 arguments.");
            return ShellStatus.CONTINUE;
        }

        File sourceFile = env.getCurrentDirectory().resolve(parts[0]).toFile();
        File destinationFile = env.getCurrentDirectory().resolve(parts[1]).toFile();

        if (sourceFile.isDirectory()) {
            env.writeln("Cannot copy directory, only files.");
            return ShellStatus.CONTINUE;
        }

        if (!sourceFile.exists()) {
            env.writeln("Source file doesn't exist.");
            return ShellStatus.CONTINUE;
        }

        if (destinationFile.isDirectory()) {
            destinationFile = new File(parts[1] + "\\" + sourceFile.getName());
        }

        if (!destinationFile.exists() ||
                (destinationFile.exists() && wantToOverwrite(env, destinationFile.getName()))) {

            try {
                copy(sourceFile, destinationFile);
                env.writeln("File copied");
            } catch (IOException e) {
                env.writeln("Couldn't copy file.");
            }
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Private method that checks if user wants to overwrite file with the same name.
     *
     * @param env  environment of the shell
     * @param name file name
     * @return true if user wants to overwrite file, otherwise false
     */
    private boolean wantToOverwrite(Environment env, String name) {
        env.writeln("Destination file: " + name + " already exist.");
        env.writeln("Do you want to overwrite it? (Y,N)");

        String response = env.readLine().trim().toUpperCase();
        if (response.equals("Y")) return true;
        if (response.equals("N")) {
            env.writeln("Will not overwrite.");
            return false;
        } else {
            env.writeln("Invalid input. Will not overwrite.");
            return false;
        }
    }

    /**
     * Private method for copying file.
     *
     * @param sourceFile      file which should be copied
     * @param destinationFile destination file
     * @throws IOException if IO error happens
     */
    private void copy(File sourceFile, File destinationFile) throws IOException {
        try (InputStream is = Files.newInputStream(sourceFile.toPath());
             OutputStream os = Files.newOutputStream(destinationFile.toPath())) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    public String getCommandName() {
        return "copy";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
