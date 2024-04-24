package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public interface Formatter {
    String format(List<Map<String, Object>> diffs) throws Exception;

    static Formatter chooseFormatter(String formatterName) {
        if (formatterName == null || formatterName.isEmpty()) {
            formatterName = "stylish";
        }
        switch (formatterName) {
            case "stylish":
                return new StylishFormatter();
            case "plain":
                return new PlainFormatter();
            case "json":
                return new JsonFormatter();
            default:
                throw new IllegalArgumentException("Unsupported format: " + formatterName);
        }
    }
}
