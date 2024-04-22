package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;

public class DataParser {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static TreeMap<String, Object> readFile(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));

        if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
            return parseYaml(content);
        } else {
            return JSON_MAPPER.readValue(content, new TypeReference<TreeMap<String, Object>>() { });
        }
    }

    private static TreeMap<String, Object> parseYaml(String yamlContent) throws IOException {
        return YAML_MAPPER.readValue(yamlContent, new TypeReference<TreeMap<String, Object>>() { });
    }

    public static String convertYamlToJson(String yamlContent) throws IOException {
        Object obj = YAML_MAPPER.readValue(yamlContent, Object.class);
        return JSON_MAPPER.writeValueAsString(obj);
    }
}
