package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.TreeMap;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file")
    private File firstFile;

    @Parameters(index = "1", description = "path to second file")
    private File secondFile;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Читаем и анализируем JSON файлы
        Map<String, Object> firstFileContent =
                objectMapper.readValue(firstFile, new TypeReference<Map<String, Object>>() { });
        Map<String, Object> secondFileContent =
                objectMapper.readValue(secondFile, new TypeReference<Map<String, Object>>() { });

        // Сортируем ключи
        Map<String, Object> sortedFirstFileContent = new TreeMap<>(firstFileContent);
        Map<String, Object> sortedSecondFileContent = new TreeMap<>(secondFileContent);

        StringBuilder diff = new StringBuilder("{\n");

        for (String key : sortedFirstFileContent.keySet()) {
            if (!sortedSecondFileContent.containsKey(key)) {
                // ключ был удален
                diff.append("  - ").append(key).append(": ")
                        .append(sortedFirstFileContent.get(key)).append("\n");
            } else if (!sortedFirstFileContent.get(key)
                    .equals(sortedSecondFileContent.get(key))) {
                // значение ключа было изменено
                diff.append("  - ").append(key).append(": ")
                        .append(sortedFirstFileContent.get(key)).append("\n");
                diff.append("  + ").append(key).append(": ")
                        .append(sortedSecondFileContent.get(key)).append("\n");
            } else {
                // значения ключей совпадают
                diff.append("    ").append(key).append(": ")
                        .append(sortedFirstFileContent.get(key)).append("\n");
            }
        }

        for (String key : sortedSecondFileContent.keySet()) {
            if (!sortedFirstFileContent.containsKey(key)) {
                // ключ был добавлен
                diff.append("  + ").append(key).append(": ")
                        .append(sortedSecondFileContent.get(key)).append("\n");
            }
        }

        diff.append("}");

        System.out.println(diff.toString());

        return 0;

    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
