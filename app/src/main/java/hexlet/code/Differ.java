package hexlet.code;

import hexlet.code.utils.DataParser;
import hexlet.code.utils.FileDataProcessor;
import hexlet.code.formatters.Formatter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static hexlet.code.formatters.Formatter.chooseFormatter;

public class Differ {

    public static String generate(String firstFilePath, String secondFilePath, String formatterName) throws Exception {
        String firstFileData = Files.readString(Paths.get(firstFilePath));
        String secondFileData = Files.readString(Paths.get(secondFilePath));

        String firstFileFormat = getFileFormat(firstFilePath);
        String secondFileFormat = getFileFormat(secondFilePath);

        Map<String, Object> firstData = DataParser.parse(firstFileData, firstFileFormat);
        Map<String, Object> secondData = DataParser.parse(secondFileData, secondFileFormat);

        List<Map<String, Object>> diffs = FileDataProcessor.process(firstData, secondData);
        Formatter formatter = chooseFormatter(formatterName);
        return formatter.format(diffs);
    }

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, "stylish");
    }

    private static String getFileFormat(String filePath) {
        if (filePath.endsWith(".json")) {
            return "json";
        } else if (filePath.endsWith(".yml") || filePath.endsWith(".yaml")) {
            return "yaml";
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filePath);
        }
    }
}
