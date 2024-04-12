package hexlet.code.formatters;

import hexlet.code.Diff;
import java.util.List;
import java.util.Map;

public final class StylishFormatter implements Formatter {
    @Override
    public String format(List<Diff> diffs) {
        StringBuilder result = new StringBuilder("{\n");
        for (Diff diff : diffs) {
            switch (diff.getType()) {
                case ADDED:
                    result.append("  + ").append(diff.getKey()).append(": ").append(formatValue(diff.getNewValue())).
                            append("\n");
                    break;
                case REMOVED:
                    result.append("  - ").append(diff.getKey()).append(": ").append(formatValue(diff.getOldValue())).
                            append("\n");
                    break;
                case UPDATED:
                    result.append("  - ").append(diff.getKey()).append(": ").append(formatValue(diff.getOldValue())).
                            append("\n");
                    result.append("  + ").append(diff.getKey()).append(": ").append(formatValue(diff.getNewValue())).
                            append("\n");
                    break;
                case UNCHANGED:
                    result.append("    ").append(diff.getKey()).append(": ").append(formatValue(diff.getNewValue())).
                            append("\n");
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
        } else if (value instanceof String) {
            return value.toString();
        } else {
            return String.valueOf(value);
        }
    }

    private String formatMap(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (count > 0) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append(": ").append(formatValue(entry.getValue()));
            count++;
        }
        sb.append("}");
        return sb.toString();
    }

    private String formatList(List<?> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(formatValue(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}
