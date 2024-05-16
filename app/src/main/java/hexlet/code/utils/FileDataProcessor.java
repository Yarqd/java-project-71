package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class FileDataProcessor {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static List<Map<String, Object>> process(Map<String, Object> firstData, Map<String, Object> secondData) {
        List<Map<String, Object>> diffs = new ArrayList<>();
        SortedSet<String> keys = new TreeSet<>(firstData.keySet());
        keys.addAll(secondData.keySet());

        for (String key : keys) {
            Map<String, Object> diff = new HashMap<>();
            Object firstValue = firstData.get(key);
            Object secondValue = secondData.get(key);

            if (!secondData.containsKey(key)) {
                diff.put("key", key);
                diff.put("oldValue", firstValue);
                diff.put("status", "removed");
            } else if (!firstData.containsKey(key)) {
                diff.put("key", key);
                diff.put("newValue", secondValue);
                diff.put("status", "added");
            } else if (!Objects.equals(firstValue, secondValue)) {
                diff.put("key", key);
                diff.put("oldValue", firstValue);
                diff.put("newValue", secondValue);
                diff.put("status", "updated");
            } else {
                diff.put("key", key);
                diff.put("value", firstValue);
                diff.put("status", "unchanged");
            }
            diffs.add(diff);
        }
        return diffs;
    }

    public static Map<String, Object> parse(String data, String format) throws Exception {
        if (format.equals("json")) {
            return JSON_MAPPER.readValue(data, new TypeReference<Map<String, Object>>() { });
        } else if (format.equals("yaml")) {
            return YAML_MAPPER.readValue(data, new TypeReference<Map<String, Object>>() { });
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }
}
