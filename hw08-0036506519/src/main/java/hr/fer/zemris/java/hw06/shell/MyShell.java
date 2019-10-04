package hr.fer.zemris.java.hw06.shell;


/**
 * Simple shell program which uses {@link MyEnvironment} class
 * as environment in which to work.
 * <p>Shell works and keeps asking for a new command until 'exit' command is given.
 * * If user needs help, there is 'help' command.</p>
 *
 * @author Stjepan Kovačić
 */
public class MyShell {

    /**
     * Main method of the program, being called first.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        MyEnvironment environment = new MyEnvironment();
        ShellStatus status = ShellStatus.CONTINUE;

        environment.writeln("Welcome to MyShell v 1.0");

        do {
            try {
                StringBuilder sb = new StringBuilder();

                environment.write((environment.getPromptSymbol() + " "));
                String line = environment.readLine();

                while (line.endsWith(String.valueOf(environment.getMorelinesSymbol()))) {
                    sb.append(line, 0, line.length() - 1);
                    System.out.print(environment.getMultilineSymbol());
                    line = environment.readLine();
                }
                sb.append(line); // add last line(the one which doesn't end with more lines symbol)

                String[] parts = sb.toString().trim().split("\\s+", 2); //2-> splitting only command and arguments part

                String commandName = parts[0];
                String arguments = "";

                if (parts.length >= 2) {
                    arguments = parts[1];
                }

                ShellCommand command = environment.commands().get(commandName);
                if (command == null) {
                    environment.writeln("Illegal command.");
                    continue;
                }
                status = command.executeCommand(environment, arguments);
            } catch (ShellIOException e) {
                status = ShellStatus.TERMINATE;
            }
        } while (status != ShellStatus.TERMINATE);

        environment.closeScanner();
    }
}
