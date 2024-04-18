package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.Formatter;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String generate(String firstFilePath, String secondFilePath, Formatter formatter) throws Exception {
        FileProcessor fileProcessor = new FileProcessor();

        TreeMap<String, Object> firstMap = fileProcessor.readFile(firstFilePath);
        TreeMap<String, Object> secondMap = fileProcessor.readFile(secondFilePath);

        SortedSet<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());

        List<Diff> diffs = createDiffs(firstMap, secondMap, keys);
        return formatter.format(diffs);
    }

    private static List<Diff> createDiffs(TreeMap<String, Object> firstMap, TreeMap<String, Object> secondMap,
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
