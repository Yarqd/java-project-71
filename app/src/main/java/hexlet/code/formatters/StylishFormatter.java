package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

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
                    result.append("  + ").append(key).append(": ").append(stringify(diff.get("newValue")))
                            .append("\n");
                    break;
                case "removed":
                    result.append("  - ").append(key).append(": ").append(stringify(diff.get("oldValue")))
                            .append("\n");
                    break;
                case "updated":
                    result.append("  - ").append(key).append(": ").append(stringify(diff.get("oldValue")))
                            .append("\n");
                    result.append("  + ").append(key).append(": ").append(stringify(diff.get("newValue")))
                            .append("\n");
                    break;
                case "unchanged":
                    result.append("    ").append(key).append(": ").append(stringify(diff.get("value"))).append("\n");
                    break;
                default:
                    break;
            }
        }
        result.append("}");
        return result.toString();
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
