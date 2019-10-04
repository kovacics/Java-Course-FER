package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDatabaseTest {

    List<String> lines;
    StudentDatabase db;

    IFilter alwaysTrue = (record -> true);
    IFilter alwaysFalse = (record -> false);

    @BeforeEach
    public void dbSet() throws IOException {
        lines = Files.readAllLines(
                Paths.get("./database.txt"),
                StandardCharsets.UTF_8
        );
        String[] arr = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            arr[i] = lines.get(i);
        }
        db = new StudentDatabase(arr);
    }


    @Test
    void forJMBAGTest() {
        StudentRecord rec = db.forJMBAG("0000000057");
        assertNotNull(rec);
        assertEquals(2, rec.getFinalGrade());
        assertEquals("Širanović", rec.getLastName());
        assertEquals("Hrvoje", rec.getFirstName());
        assertEquals("0000000057", rec.getJmbag());
        assertNull(db.forJMBAG("1234"));
    }

    @Test
    void filterTest() {
        List<StudentRecord> list = db.filter(alwaysTrue);
        assertEquals(lines.size(), list.size());
        list.clear();
        list = db.filter(alwaysFalse);
        assertEquals(0, list.size());
    }
}