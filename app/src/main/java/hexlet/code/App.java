package hexlet.code;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine;
import java.io.File;
import java.util.concurrent.Callable;

// Импорт необходим для работы с содержимым файла
import java.nio.file.Files;
import java.nio.file.Path;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file")
    private File firstFile;

    @Parameters(index = "1", description = "path to second file")
    private File secondFile;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        String firstFilePath = firstFile.getPath();
        String secondFilePath = secondFile.getPath();

        // Предполагается, что у нас уже есть Parser класс с методом convertYamlToJson
        Parser parser = new Parser();

        // Проверка расширения первого файла и конвертация при необходимости
        if (firstFilePath.endsWith(".yaml") || firstFilePath.endsWith(".yml")) {
            String content = Files.readString(Path.of(firstFilePath));
            String json = parser.convertYamlToJson(content);
            // Запись конвертированного JSON во временный файл
            Path tempFile = Files.createTempFile("firstFile", ".json");
            Files.writeString(tempFile, json);
            // Замена оригинального файла временным для сравнения
            firstFile = tempFile.toFile();
        }

        // Та же логика для второго файла
        if (secondFilePath.endsWith(".yaml") || secondFilePath.endsWith(".yml")) {
            String content = Files.readString(Path.of(secondFilePath));
            String json = parser.convertYamlToJson(content);
            // Запись конвертированного JSON во временный файл
            Path tempFile = Files.createTempFile("secondFile", ".json");
            Files.writeString(tempFile, json);
            // Замена оригинального файла временным для сравнения
            secondFile = tempFile.toFile();
        }

        // Продолжение работы программы уже с файлами в формате JSON
        String diff = Differ.generate(firstFile, secondFile);
        System.out.println(diff);

        // Удаление временных файлов, если они были созданы
//        Files.deleteIfExists(firstFile.toPath());
//        Files.deleteIfExists(secondFile.toPath());

        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
