package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'ls' command.
 *
 * @author Stjepan Kovačić
 */
public class LsShellCommand implements ShellCommand {

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command is used to list directory.(Only direct children, not recursively.)");
        DESCRIPTION.add("Command expects 1 argument: ");
        DESCRIPTION.add("\t1. directory to list");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 1) {
            env.writeln("One argument expected.");
            return ShellStatus.CONTINUE;
        }

        File directory = env.getCurrentDirectory().resolve(parts[0]).toFile();
        if (!directory.exists()) {
            env.writeln("Directory doesn't exist.");
            return ShellStatus.CONTINUE;
        }
        if (!directory.isDirectory()) {
            env.writeln("Argument should be directory.");
            return ShellStatus.CONTINUE;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            env.writeln("Cannot get directory files.");
            return ShellStatus.CONTINUE;
        }

        for (File file : files) {
            try {
                env.writeln(getFileRecord(file).toString());
            } catch (IOException e) {
                env.writeln("Cannot read from the file");
                return ShellStatus.CONTINUE;
            }
        }

        return ShellStatus.CONTINUE;
    }


    /**
     * Helping method that creates new file record from the given file, and
     * returns it.
     *
     * @return created file record
     * @throws IOException if IO error happened
     */
    private FileRecord getFileRecord(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        String flags = sb
                .append(file.isDirectory() ? 'd' : '-')
                .append(file.canRead() ? 'r' : '-')
                .append(file.canWrite() ? 'w' : '-')
                .append(file.canExecute() ? 'x' : '-')
                .toString();

        //needed for directories
        long bytes = Files.walk(file.toPath())
                .map(Path::toFile)
                .filter(p -> !p.isDirectory())
                .mapToLong(File::length)
                .sum();

        String size = String.format("%10s", bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BasicFileAttributeView faView = Files.getFileAttributeView(
                file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
        );
        BasicFileAttributes attributes = faView.readAttributes();
        FileTime fileTime = attributes.creationTime();
        String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

        String name = file.getName();

        return new FileRecord(flags, size, formattedDateTime, name);
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }

    /**
     * Helping class that represent file records with this attributes:
     * <li>flags of the file</li>
     * <li>size of the file</li>
     * <li>date time of the file creation</li>
     * <li>name of the file</li>
     */
    private static class FileRecord {
        /**
         * File flags.
         */
        private String flags;

        /**
         * File size in bytes.
         */
        private String size;

        /**
         * File creation time.
         */
        private String dateTime;

        /**
         * File name.
         */
        private String name;

        public FileRecord(String flags, String size, String dateTime, String name) {
            this.flags = flags;
            this.size = size;
            this.dateTime = dateTime;
            this.name = name;
        }

        @Override
        public String toString() {
            return flags + " " + size + " " + dateTime + " " + name;
        }
    }
}
