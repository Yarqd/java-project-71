package hexlet.code.formatters;

import hexlet.code.utils.Diff;
import java.util.List;

public interface Formatter {
    String format(List<Diff> diffs) throws Exception;

    static Formatter chooseFormatter(String formatterName) {
        if (formatterName == null || formatterName.isEmpty()) {
            formatterName = "stylish";
        }
        switch (formatterName) {
            case "stylish":
                return new StylishFormatter();
            case "plain":
                return new PlainFormatter();
            default:
                throw new IllegalArgumentException("Unsupported format: " + formatterName);
        }
    }
}
