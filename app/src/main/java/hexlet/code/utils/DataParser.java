package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class DataParser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static Map<String, Object> parse(String data, String format) throws Exception {
        if (format.equals("json")) {
            return JSON_MAPPER.readValue(data, new TypeReference<Map<String, Object>>() { });
        } else if (format.equals("yaml")) {
            return YAML_MAPPER.readValue(data, new TypeReference<Map<String, Object>>() { });
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }
}
