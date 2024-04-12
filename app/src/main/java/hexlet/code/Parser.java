package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;

public class Parser {
    private static final ObjectMapper YAML_READER = new ObjectMapper(new YAMLFactory());
    private static final ObjectMapper JSON_WRITER = new ObjectMapper();

    public static String convertYamlToJson(String yamlContent) throws IOException {
        if (yamlContent == null || yamlContent.isEmpty()) {
            throw new IllegalArgumentException("YAML content cannot be null or empty");
        }

        Object obj = YAML_READER.readValue(yamlContent, Object.class);
        return JSON_WRITER.writeValueAsString(obj);
    }
}
