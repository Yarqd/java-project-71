package hexlet.code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Diff.DiffType;
import hexlet.code.formatters.Formatter;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String generate(File firstFile, File secondFile, Formatter formatter) throws Exception {
        TreeMap<String, Object> firstMap = OBJECT_MAPPER.readValue(firstFile,
                new TypeReference<TreeMap<String, Object>>() { });
        TreeMap<String, Object> secondMap = OBJECT_MAPPER.readValue(secondFile,
                new TypeReference<TreeMap<String, Object>>() { });
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
                diffs.add(new Diff(key, firstMap.get(key), null, DiffType.REMOVED));
            } else if (!firstMap.containsKey(key)) {
                diffs.add(new Diff(key, null, secondMap.get(key), DiffType.ADDED));
            } else {
                Object firstValue = firstMap.get(key);
                Object secondValue = secondMap.get(key);
                if (!Objects.equals(firstValue, secondValue)) {
                    diffs.add(new Diff(key, firstValue, secondValue, DiffType.UPDATED));
                } else {
                    diffs.add(new Diff(key, firstValue, secondValue, DiffType.UNCHANGED));
                }
            }
        }
        return diffs;
    }
}
