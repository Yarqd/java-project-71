package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;

public class FileProcessor {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public final TreeMap<String, Object> readFile(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));

        if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
            return YAML_MAPPER.readValue(content, new TypeReference<TreeMap<String, Object>>() { });
        } else {
            return JSON_MAPPER.readValue(content, new TypeReference<TreeMap<String, Object>>() { });
        }
    }
}
