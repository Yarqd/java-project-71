package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StylishFormatter implements Formatter {

    @Override
    public final String format(List<Map<String, Object>> diffs) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        for (Map<String, Object> diff : diffs) {
            String key = diff.get("key").toString();
            String status = diff.get("status").toString();
            switch (status) {
                case "added":
                    result.append("  + ").append(key).append(": ").append(formatValue(diff.get("newValue")))
                            .append("\n");
                    break;
                case "removed":
                    result.append("  - ").append(key).append(": ").append(formatValue(diff.get("oldValue")))
                            .append("\n");
                    break;
                case "updated":
                    result.append("  - ").append(key).append(": ").append(formatValue(diff.get("oldValue")))
                            .append("\n");
                    result.append("  + ").append(key).append(": ").append(formatValue(diff.get("newValue")))
                            .append("\n");
                    break;
                case "unchanged":
                    result.append("    ").append(key).append(": ").append(formatValue(diff.get("value"))).append("\n");
                    break;
                default:
                    break;
            }
        }
        result.append("}");
        return result.toString();
    }

    private String formatValue(Object value) {
        if (value instanceof Map) {
            return formatMap((Map<?, ?>) value);
        } else if (value instanceof List) {
            return formatList((List<?>) value);
        } else if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof Boolean || value instanceof Number) {
            return value.toString();
        } else {
            return value.toString();
        }
    }

    private String formatList(List<?> list) {
        return list.stream()
                .map(item -> item == null ? "null" : item.toString()) // Remove quotes around the strings
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private String formatMap(Map<?, ?> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey().toString() + "=" + formatValue(entry.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
