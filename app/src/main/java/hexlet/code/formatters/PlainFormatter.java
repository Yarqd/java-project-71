package hexlet.code.formatters;

import hexlet.code.utils.Diff;
import java.util.List;
import java.util.Map;

public final class PlainFormatter implements Formatter {
    @Override
    public String format(List<Diff> diffs) {
        StringBuilder result = new StringBuilder();
        for (Diff diff : diffs) {
            switch (diff.getType()) {
                case ADDED:
                    result.append(String.format("Property '%s' was added with value: %s\n",
                            diff.getKey(), formatValue(diff.getNewValue())));
                    break;
                case REMOVED:
                    result.append(String.format("Property '%s' was removed\n", diff.getKey()));
                    break;
                case UPDATED:
                    result.append(String.format("Property '%s' was updated. From %s to %s\n",
                            diff.getKey(), formatValue(diff.getOldValue()), formatValue(diff.getNewValue())));
                    break;
                default:
                    break;
            }
        }
        if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    private String formatValue(Object value) {
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        } else if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else {
            return value.toString();
        }
    }
}
