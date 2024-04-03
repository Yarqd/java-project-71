package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.TreeMap;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Стилишный форматтер значения
    private static String stylishFormatter(Object value) throws JsonProcessingException {
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Boolean || value instanceof Number || value == null) {
            return OBJECT_MAPPER.writeValueAsString(value);
        } else if (value instanceof Map || value instanceof List) {
            String rawJson = OBJECT_MAPPER.writeValueAsString(value);
            return rawJson.replaceAll("\"([\\w]+)\":", "$1:").replaceAll("\"", "");
        }
        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
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
