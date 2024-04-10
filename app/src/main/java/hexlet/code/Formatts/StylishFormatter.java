package hexlet.code.Formatts;

import java.util.List;
import java.util.Map;

public class StylishFormatter {

    public static String format(Object firstValue, Object secondValue, String key) {
        StringBuilder builder = new StringBuilder();
        if (secondValue == null) {
            builder.append("  - ").append(key).append(": ").append(stylishFormatter(firstValue)).append("\n");
        } else if (firstValue == null) {
            builder.append("  + ").append(key).append(": ").append(stylishFormatter(secondValue)).append("\n");
        } else if (!firstValue.equals(secondValue)) {
            builder.append("  - ").append(key).append(": ").append(stylishFormatter(firstValue)).append("\n");
            builder.append("  + ").append(key).append(": ").append(stylishFormatter(secondValue)).append("\n");
        } else {
            builder.append("    ").append(key).append(": ").append(stylishFormatter(firstValue)).append("\n");
        }
        return builder.toString();
    }

    private static String stylishFormatter(Object value) {
        if (value instanceof String) {
            return value.toString();
        } else if (value instanceof Boolean || value instanceof Number || value == null) {
            return String.valueOf(value);
        } else if (value instanceof Map) {
            return formatMap((Map<?, ?>) value);
        } else if (value instanceof List) {
            return formatList((List<?>) value);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }
    }

    private static String formatMap(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (count > 0) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append(stylishFormatter(entry.getValue()));
            count++;
        }
        sb.append("}");
        return sb.toString();
    }

    private static String formatList(List<?> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(stylishFormatter(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}
