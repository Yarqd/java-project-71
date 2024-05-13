package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.List;

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

        return Tree.createTree(firstMap, secondMap, keys);
    }

    private static Map<String, Object> readData(String filePath) throws IOException {
        ObjectMapper objectMapper;
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            throw new IOException("File does not exist or is empty: " + filePath);
        }

        if (filePath.endsWith(".json")) {
            objectMapper = JSON_MAPPER;
        } else if (filePath.endsWith(".yml") || filePath.endsWith(".yaml")) {
            objectMapper = YAML_MAPPER;
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filePath);
        }
        return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() { });
    }

}
