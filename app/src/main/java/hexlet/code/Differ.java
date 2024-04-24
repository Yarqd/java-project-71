package hexlet.code;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.Formatter;
import hexlet.code.utils.DiffProcessor;

import static hexlet.code.formatters.Formatter.chooseFormatter;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String generate(String firstFilePath, String secondFilePath, String formatterName) throws Exception {
        List<Map<String, Object>> diffs = DiffProcessor.process(firstFilePath, secondFilePath);
        Formatter formatter = chooseFormatter(formatterName);
        return formatter.format(diffs);
    }
    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, "stylish");
    }
}
