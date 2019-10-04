package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Stjepan Kovačić
 */
class MassrenameShellCommandTest {

    Environment env;

    @BeforeEach
    void setUp() {
        env = new MyEnvironment();
    }

    @Test
    void executeCommandTestGroups() {

        new MassrenameShellCommand().executeCommand(env, "slike DIR2 groups slika(\\d+)-([^.]+)\\.jpg");
    }

    @Test
    void executeCommandTestFilter() {

        new MassrenameShellCommand().executeCommand(env, "slike DIR2 filter slika(\\d+)-([^.]+)\\.jpg");
    }

    @Test
    void executeCommandTestShow() {

        new MassrenameShellCommand().executeCommand(env, "slike DIR show slika(\\d+)-([^.]+)\\.jpg gradovi-${2}-${1,03}.jpg");
    }

    @Test
    void executeCommandTestExecute() {

        new MassrenameShellCommand().executeCommand(env, "slike slikeNovo execute slika(\\d+)-([^.]+)\\.jpg gradovi-${2}-${1,03}.jpg");
    }
}