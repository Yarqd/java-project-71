package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;

class AppTest {

    private final String basePath = Paths.get("src", "test", "resources").toString();

    @Test
    void testJsonDiffWithStylishFormatter() throws Exception {
        File firstFile = new File(basePath, "file1.json");
        File secondFile = new File(basePath, "file2.json");
        Formatter formatter = new StylishFormatter();
        String expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";
        String actual = Differ.generate(firstFile, secondFile, formatter);
        assertEquals(expected, actual);
    }

    @Test
    void testYamlDiffWithPlainFormatter() throws Exception {
        File firstFile = new File(basePath, "file1.yml");
        File secondFile = new File(basePath, "file2.yml");
        Formatter formatter = new PlainFormatter();
        String expected = "Property 'follow' was removed\n"
                + "Property 'proxy' was removed\n"
                + "Property 'timeout' was updated. From 50 to 20\n"
                + "Property 'verbose' was added with value: true\n";
        String actual = Differ.generate(firstFile, secondFile, formatter);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testHandlingNonExistentFiles() {
        File firstFile = new File(basePath, "nonexistent.json");
        File secondFile = new File(basePath, "nonexistent.json");
        Formatter formatter = new StylishFormatter();
        assertThrows(Exception.class, () -> Differ.generate(firstFile, secondFile, formatter));
    }

    @Test
    void testInvalidJsonContent() {
        File firstFile = new File(basePath, "invalid.json");
        File secondFile = new File(basePath, "file2.json");
        Formatter formatter = new StylishFormatter();
        assertThrows(Exception.class, () -> Differ.generate(firstFile, secondFile, formatter));
    }
}
