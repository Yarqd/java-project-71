package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testAppWithTwoFiles() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        int exitCode = new CommandLine(new App()).execute("src/main/resources/file1.yml",
                "src/main/resources/file2.yml");

        System.setOut(originalOut);

        String expectedOutput = "{"
                + "\n  - follow: false"
                + "\n    host: hexlet.io"
                + "\n  - proxy: 123.234.53.22"
                + "\n  - timeout: 50"
                + "\n  + timeout: 20"
                + "\n  + verbose: true"
                + "\n}";

        assertEquals(0, exitCode);
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}
