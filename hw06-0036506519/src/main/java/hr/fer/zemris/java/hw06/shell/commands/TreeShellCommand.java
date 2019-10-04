package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'tree' command.
 *
 * @author Stjepan Kovačić
 */
public class TreeShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to print directory tree to a console.");
        DESCRIPTION.add("Command expects 1 argument: ");
        DESCRIPTION.add("\t1. directory path");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 1) {
            env.writeln("One argument expected for tree command");
            return ShellStatus.CONTINUE;
        }

        PrintFileTreeVisitor pfv = new PrintFileTreeVisitor();
        try {
            Files.walkFileTree(Paths.get(parts[0]), pfv);
            env.writeln(pfv.getFileTreeString());
        } catch (IOException e) {
            env.writeln("Couldn't walk through files.");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "tree";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }


    /**
     * Implementation of the {@link SimpleFileVisitor} class.
     * Visitor is used to write a tree structure of a directory.
     *
     * @author Stjepan Kovačić
     */
    private static class PrintFileTreeVisitor extends SimpleFileVisitor<Path> {

        /**
         * Current level.
         */
        private int level = 0;

        /**
         * String builder for building file tree.
         */
        private StringBuilder sb = new StringBuilder();

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            sb.append(" ".repeat(level * 2)).append(dir.getFileName()).append('\n');
            level++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            sb.append(" ".repeat(level * 2)).append(file.getFileName()).append('\n');
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            level--;
            return FileVisitResult.CONTINUE;
        }

        /**
         * Method returns file tree string.
         *
         * @return file tree string
         */
        public String getFileTreeString() {
            return sb.toString();
        }
    }
}
