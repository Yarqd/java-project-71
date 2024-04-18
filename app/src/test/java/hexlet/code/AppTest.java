package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AppTest {

    private final String basePath = Paths.get("src", "test", "resources").toString();
    private String readFixture(String path) throws IOException {
        Path filePath = Paths.get(basePath, path);
        return Files.readString(filePath);
    }



    @Test
    void testJsonDiffWithStylishFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.json").toString();
        String secondFilePath = Paths.get(basePath, "file2.json").toString();
        Formatter formatter = new StylishFormatter();
        String expected = readFixture("fixtures/fixture1.json");
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }


    @Test
    void testYamlDiffWithPlainFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.yml").toString();
        String secondFilePath = Paths.get(basePath, "file2.yml").toString();
        Formatter formatter = new PlainFormatter();
        String expected = readFixture("fixtures/fixture3.json");
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testHandlingNonExistentFiles() {
        String firstFile = Paths.get(basePath, "nonexistent.json").toString();
        String secondFile = Paths.get(basePath, "nonexistent.json").toString();
        Formatter formatter = new StylishFormatter();
        assertThrows(Exception.class, () -> Differ.generate(firstFile, secondFile, formatter));
    }

    @Test
    void testInvalidJsonContent() {
        String firstFile = Paths.get(basePath, "invalid.json").toString();
        String secondFile = Paths.get(basePath, "file2.json").toString();
        Formatter formatter = new StylishFormatter();
        assertThrows(Exception.class, () -> Differ.generate(firstFile, secondFile, formatter));
    }
}
