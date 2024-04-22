package hexlet.code.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.HashMap;

public class Tree {

    public static List<Map<String, Object>> createTree(TreeMap<String, Object> firstMap,
                                                       TreeMap<String, Object> secondMap,
                                                       SortedSet<String> keys) {
        List<Map<String, Object>> diffs = new ArrayList<>();
        for (String key : keys) {
            Map<String, Object> diff = new HashMap<>();
            diff.put("key", key);

            if (!secondMap.containsKey(key)) {
                diff.put("status", "removed");
                diff.put("value", firstMap.get(key));
            } else if (!firstMap.containsKey(key)) {
                diff.put("status", "added");
                diff.put("value", secondMap.get(key));
            } else {
                Object firstValue = firstMap.get(key);
                Object secondValue = secondMap.get(key);
                if (!Objects.equals(firstValue, secondValue)) {
                    diff.put("status", "updated");
                    diff.put("oldValue", firstValue);
                    diff.put("newValue", secondValue);
                } else {
                    diff.put("status", "unchanged");
                    diff.put("value", firstValue);
                }
            }
            diffs.add(diff);
        }
        return diffs;
    }
}
