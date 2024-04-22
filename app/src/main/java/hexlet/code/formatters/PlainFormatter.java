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
                    String addedValue = formatValue(newValue);
                    result.append(String.format("Property '%s' was added with value: %s\n", key, addedValue));
                    break;

                case "removed":
                    result.append(String.format("Property '%s' was removed\n", key));
                    break;
                case "updated":
                    String oldValueString = formatValue(oldValue);
                    String updatedValueString = formatValue(newValue);
                    result.append(String.format("Property '%s' was updated. From %s to %s\n", key, oldValueString,
                            updatedValueString));
                    break;
                default:
                    break;
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
            String str = (String) value;
            return needsQuoting(str) ? "'" + str + "'" : str;
        } else if (value instanceof Boolean || value instanceof Number) {
            return value.toString();
        } else {
            return String.valueOf(value);
        }
    }

    private boolean needsQuoting(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
        }
        if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
            return false;
        }
        return true;
    }
}
