package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String expected = readFixture("fixtures/fixture1.json");
        String formatter = "stylish";
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testJsonDiffWithPlainFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.json").toString();
        String secondFilePath = Paths.get(basePath, "file2.json").toString();
        String expected = readFixture("fixtures/fixture3.json");
        String formatter = "plain";
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testJsonDiffWithJsonFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.json").toString();
        String secondFilePath = Paths.get(basePath, "file2.json").toString();
        String expected = readFixture("fixtures/fixture4.json");
        String formatter = "json";
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testYamlDiffWithStylishFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.yml").toString();
        String secondFilePath = Paths.get(basePath, "file2.yml").toString();
        String expected = readFixture("fixtures/fixture1.json");
        String formatter = "stylish";
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testYamlDiffWithPlainFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.yml").toString();
        String secondFilePath = Paths.get(basePath, "file2.yml").toString();
        String expected = readFixture("fixtures/fixture3.json");
        String formatter = "plain";
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testYamlDiffWithJsonFormatter() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.yml").toString();
        String secondFilePath = Paths.get(basePath, "file2.yml").toString();
        String expected = readFixture("fixtures/fixture4.json");
        String formatter = "json";
        String actual = Differ.generate(firstFilePath, secondFilePath, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testGenerateWithoutExplicitFormatterJsonFiles() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.json").toString();
        String secondFilePath = Paths.get(basePath, "file2.json").toString();
        String expected = readFixture("fixtures/fixture1.json");
        String actual = Differ.generate(firstFilePath, secondFilePath); // без указания форматтера
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testGenerateWithoutExplicitFormatterYamlFiles() throws Exception {
        String firstFilePath = Paths.get(basePath, "file1.yml").toString();
        String secondFilePath = Paths.get(basePath, "file2.yml").toString();
        String expected = readFixture("fixtures/fixture1.json");
        String actual = Differ.generate(firstFilePath, secondFilePath); // без указания форматтера
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testHandlingNonExistentFiles() {
        String firstFile = Paths.get(basePath, "nonexistent.json").toString();
        String secondFile = Paths.get(basePath, "nonexistent.json").toString();
        String formatter = "stylish";
        assertThrows(Exception.class, () -> Differ.generate(firstFile, secondFile, formatter));
    }

    @Test
    void testInvalidJsonContent() {
        String firstFile = Paths.get(basePath, "invalid.json").toString();
        String secondFile = Paths.get(basePath, "file2.json").toString();
        String formatter = "stylish";
        assertThrows(Exception.class, () -> Differ.generate(firstFile, secondFile, formatter));
    }

    @Test
    void testEmptyFileDiff() {
        String emptyFilePath = Paths.get(basePath, "nonexistent.json").toString();
        assertThrows(IOException.class, () -> Differ.generate(emptyFilePath, emptyFilePath, "json"),
                "Expected IOException for empty or nonexistent file");
    }


}
