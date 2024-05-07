package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public final class PlainFormatter implements Formatter {
    @Override
    public String format(List<Map<String, Object>> diffs) {
        StringBuilder result = new StringBuilder();
        for (Map<String, Object> diff : diffs) {
            String key = (String) diff.get("key");
            String status = (String) diff.get("status");
            Object oldValue = diff.get("oldValue");
            Object newValue = diff.get("newValue");

            switch (status) {
                case "added":
                    result.append(String.format("Property '%s' was added with value: %s\n",
                            key, formatValue(newValue)));
                    break;
                case "removed":
                    result.append(String.format("Property '%s' was removed\n", key));
                    break;
                case "updated":
                    result.append(String.format("Property '%s' was updated. From %s to %s\n",
                            key, formatValue(oldValue), formatValue(newValue)));
                    break;
                case "unchanged":
                    // Ignore unchanged properties
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected status: " + status);
            }
        }
        return result.toString().trim();
    }

    private String formatValue(Object value) {
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        } else if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else {
            return String.valueOf(value);
        }
    }
}
