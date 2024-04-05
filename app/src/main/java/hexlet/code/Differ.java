package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.TreeMap;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    public static String generate(File firstFile, File secondFile, String format) throws Exception {
        if (!"stylish".equals(format)) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }

        TreeMap<String, Object> firstMap = OBJECT_MAPPER.readValue(firstFile, new TypeReference<TreeMap<String,
                Object>>() { });
        TreeMap<String, Object> secondMap = OBJECT_MAPPER.readValue(secondFile, new TypeReference<TreeMap<String,
                Object>>() { });

        SortedSet<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());

        StringBuilder builder = new StringBuilder("{\n");
        for (String key : keys) {
            Object firstValue = firstMap.get(key);
            Object secondValue = secondMap.get(key);
            if (!secondMap.containsKey(key)) {
                builder.append("  - ").append(key).append(": ").append(stylishFormatter(firstValue)).append("\n");
            } else if (!firstMap.containsKey(key)) {
                builder.append("  + ").append(key).append(": ").append(stylishFormatter(secondValue)).append("\n");
            } else if (!Objects.equals(firstValue, secondValue)) {
                builder.append("  - ").append(key).append(": ").append(stylishFormatter(firstValue)).append("\n");
                builder.append("  + ").append(key).append(": ").append(stylishFormatter(secondValue)).append("\n");
            } else {
                builder.append("    ").append(key).append(": ").append(stylishFormatter(firstValue)).append("\n");
            }
        }
        builder.append("}\n");
        return builder.toString();
    }
}
