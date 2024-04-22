package hexlet.code.utils;

import java.io.File;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.Formatter;

public class DiffProcessor {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<Diff> process(String firstFilePath, String secondFilePath, String formatterName)
            throws Exception {
        File firstFile = new File(firstFilePath);
        File secondFile = new File(secondFilePath);

        TreeMap<String, Object> firstMap = OBJECT_MAPPER.readValue(firstFile,
                new TypeReference<TreeMap<String, Object>>() { });
        TreeMap<String, Object> secondMap = OBJECT_MAPPER.readValue(secondFile,
                new TypeReference<TreeMap<String, Object>>() { });
        SortedSet<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());

        Formatter formatter = Formatter.chooseFormatter(formatterName);
        List<Diff> diffs = Tree.createTree(firstMap, secondMap, keys);
        return diffs;
    }
}

