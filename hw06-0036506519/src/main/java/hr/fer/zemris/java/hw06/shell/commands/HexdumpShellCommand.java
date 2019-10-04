package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.InputParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@code ShellCommand} interface.
 * Represents 'hexdump' command.
 *
 * @author Stjepan Kovačić
 */
public class HexdumpShellCommand implements ShellCommand {

    /**
     * Tracks value of the current line.
     */
    private int currentLine;

    /**
     * List of all lines.
     */
    private List<String> lines = new ArrayList<>();

    /**
     * Command description.
     */
    private static List<String> DESCRIPTION = new ArrayList<>();

    static {
        DESCRIPTION.add("Command prints hex-output of the given file.");
        DESCRIPTION.add("Command expects 1 argument: ");
        DESCRIPTION.add("\t1. path to the file ");
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] parts = new InputParser().parseArguments(arguments);

        if (parts.length != 1) {
            env.writeln("One argument expected.");
            return ShellStatus.CONTINUE;
        }

        File file = new File(parts[0]);

        if (!file.exists()) {
            env.writeln("File doesn't exist.");
            return ShellStatus.CONTINUE;
        }

        if (file.isDirectory()) {
            env.writeln("Hexdump argument must be a file, not directory.");
            return ShellStatus.CONTINUE;
        }
        try (InputStream input = Files.newInputStream(file.toPath())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                getHexDumpLines(buffer, bytesRead).forEach(env::writeln);
            }
        } catch (IOException e) {
            env.writeln("Error happened while reading from a file");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Method gets next portion of the hex dump output.
     *
     * @param buffer buffer of bytes
     * @param len    number of bytes in the buffer
     * @return hex dump made from the given buffered bytes
     */
    private List<String> getHexDumpLines(byte[] buffer, int len) {
        int linesNumber = (int) Math.ceil((double) len / 16);

        for (int i = 0; i < linesNumber * 16; ) {
            StringBuilder line = new StringBuilder();
            line.append((String.format("%08X", currentLine))).append(":");

            //first part
            for (int j = 0; j < 8; j++, i++) {
                line.append(" ");
                line.append(i < len ? getHexValue(buffer[i]) : "  ");
            }
            line.append("|");

            //second part
            for (int j = 0; j < 8; j++, i++) {
                line.append(i < len ? getHexValue(buffer[i]) : "  ");
                line.append(" ");
            }
            line.append("| ");

            // third part
            i -= 16; // back to start of line
            for (int j = 0; j < 16; j++, i++) {
                if (i < len) {
                    line.append(buffer[i] < 32 ? '.' : (char) buffer[i]);
                }
            }
            lines.add(line.toString());
            currentLine += 16;
        }

        return lines;
    }

    /**
     * Converts given byte into hexadecimal string.
     *
     * @param b byte to be converted
     * @return hexadecimal string
     */
    private String getHexValue(byte b) {
        String HEXES = "0123456789abcdef";

        int hex1 = (b & 0xf0) >> 4;
        int hex2 = b & 0xf;

        return String.valueOf(HEXES.charAt(hex1)) + HEXES.charAt(hex2);
    }

    @Override
    public String getCommandName() {
        return "hexdump";
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
