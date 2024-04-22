package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DiffProcessor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<Map<String, Object>> process(String firstFilePath, String secondFilePath) throws Exception {
        TreeMap<String, Object> firstMap = readData(firstFilePath);
        TreeMap<String, Object> secondMap = readData(secondFilePath);

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

    private static TreeMap<String, Object> readData(String filePath) throws Exception {
        File file = new File(filePath);
        return OBJECT_MAPPER.readValue(file, new TypeReference<TreeMap<String, Object>>() { });
    }
}
