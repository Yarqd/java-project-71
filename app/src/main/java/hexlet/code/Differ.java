package hexlet.code;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.Formatter;
import hexlet.code.utils.Diff;
import hexlet.code.utils.DiffProcessor;

import static hexlet.code.formatters.Formatter.chooseFormatter;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String generate(String firstFilePath, String secondFilePath, String formatterName) throws Exception {
        List<Diff> diffs = DiffProcessor.process(firstFilePath, secondFilePath, formatterName);
        Formatter formatter = chooseFormatter(formatterName);
        return formatter.format(diffs);
    }
}
