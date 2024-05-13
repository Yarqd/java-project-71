package hexlet.code.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;

public class Tree {

    public static List<Map<String, Object>> createTree(TreeMap<String, Object> firstMap,
                                                       TreeMap<String, Object> secondMap,
                                                       SortedSet<String> keys) {
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
            } else if (!compareValues(firstValue, secondValue)) {
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

    private static boolean compareValues(Object firstValue, Object secondValue) {
        return Objects.equals(firstValue, secondValue);
    }
}
