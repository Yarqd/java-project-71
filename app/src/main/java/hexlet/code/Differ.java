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
import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;

public class Differ {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String generate(String firstFilePath, String secondFilePath, String formatterName) throws Exception {
        File firstFile = new File(firstFilePath);
        File secondFile = new File(secondFilePath);

        TreeMap<String, Object> firstMap = OBJECT_MAPPER.readValue(firstFile,
                new TypeReference<TreeMap<String, Object>>() { });
        TreeMap<String, Object> secondMap = OBJECT_MAPPER.readValue(secondFile,
                new TypeReference<TreeMap<String, Object>>() { });
        SortedSet<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());

        Formatter formatter = chooseFormatter(formatterName);
        List<Diff> diffs = createDiffs(firstMap, secondMap, keys);
        return formatter.format(diffs);
    }

    private static Formatter chooseFormatter(String formatterName) {
        if (formatterName == null || formatterName.isEmpty()) {
            formatterName = "stylish";
        }
        switch (formatterName) {
            case "stylish":
                return new StylishFormatter();
            case "plain":
                return new PlainFormatter();
            default:
                throw new IllegalArgumentException("Unsupported format: " + formatterName);
        }
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
