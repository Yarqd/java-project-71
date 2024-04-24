package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DiffProcessor {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static List<Map<String, Object>> process(String firstFilePath, String secondFilePath) throws Exception {
        Map<String, Object> firstRawMap = readData(firstFilePath);
        Map<String, Object> secondRawMap = readData(secondFilePath);

        TreeMap<String, Object> firstMap = new TreeMap<>(firstRawMap);
        TreeMap<String, Object> secondMap = new TreeMap<>(secondRawMap);

        SortedSet<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());

        List<Map<String, Object>> diffs = new ArrayList<>();
        for (String key : keys) {
            Map<String, Object> diff = new HashMap<>();
            Object firstValue = firstMap.get(key);
            Object secondValue = secondMap.get(key);

            if (!secondMap.containsKey(key)) {
                diff.put("key", key);
                diff.put("oldValue", firstValue);
                diff.put("status", "removed");
            } else if (!firstMap.containsKey(key)) {
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

    private static Map<String, Object> readData(String filePath) throws IOException {
        ObjectMapper objectMapper;
        if (filePath.endsWith(".json")) {
            objectMapper = JSON_MAPPER;
        } else if (filePath.endsWith(".yml") || filePath.endsWith(".yaml")) {
            objectMapper = YAML_MAPPER;
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filePath);
        }
        return objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
    }
}
