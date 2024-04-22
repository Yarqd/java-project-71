package hexlet.code.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;

public class Tree {

    public static List<Diff> createTree(TreeMap<String, Object> firstMap, TreeMap<String, Object> secondMap,
                                        SortedSet<String> keys) {
        List<Diff> diffs = new ArrayList<>();
        for (String key : keys) {
            if (!secondMap.containsKey(key)) {
                diffs.add(new Diff(key, firstMap.get(key), null, Diff.DiffType.REMOVED));
            } else if (!firstMap.containsKey(key)) {
                diffs.add(new Diff(key, null, secondMap.get(key), Diff.DiffType.ADDED));
            } else {
                Object firstValue = firstMap.get(key);
                Object secondValue = secondMap.get(key);
                if (!Objects.equals(firstValue, secondValue)) {
                    diffs.add(new Diff(key, firstValue, secondValue, Diff.DiffType.UPDATED));
                } else {
                    diffs.add(new Diff(key, firstValue, secondValue, Diff.DiffType.UNCHANGED));
                }
            }
        }
        return diffs;
    }
}
