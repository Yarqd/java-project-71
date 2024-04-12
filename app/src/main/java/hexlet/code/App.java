package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;
import java.util.concurrent.Callable;
import java.nio.file.Files;
import java.nio.file.Path;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.Formatter;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file")
    private File firstFile;

    @Parameters(index = "1", description = "path to second file")
    private File secondFile;

    @Option(names = {"-f", "--format"}, description = "output format [default: ${DEFAULT-VALUE}]")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        String firstFilePath = firstFile.getPath();
        String secondFilePath = secondFile.getPath();

        Parser parser = new Parser();

        if (firstFilePath.endsWith(".yaml") || firstFilePath.endsWith(".yml")) {
            String content = Files.readString(Path.of(firstFilePath));
            String json = parser.convertYamlToJson(content);
            Path tempFile = Files.createTempFile("firstFile", ".json");
            Files.writeString(tempFile, json);
            firstFile = tempFile.toFile();
        }

        if (secondFilePath.endsWith(".yaml") || secondFilePath.endsWith(".yml")) {
            String content = Files.readString(Path.of(secondFilePath));
            String json = parser.convertYamlToJson(content);
            Path tempFile = Files.createTempFile("secondFile", ".json");
            Files.writeString(tempFile, json);
            secondFile = tempFile.toFile();
        }

        Formatter formatter;
        if ("plain".equals(format)) {
            formatter = new PlainFormatter();
        } else {
            formatter = new StylishFormatter();
        }

        String diff = Differ.generate(firstFile, secondFile, formatter);
        System.out.println(diff);
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
