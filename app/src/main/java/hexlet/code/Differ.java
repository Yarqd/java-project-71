package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Differ {

    public static String generate(File firstFile, File secondFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> firstMap = objectMapper.readValue(firstFile, TreeMap.class);
        Map<String, Object> secondMap = objectMapper.readValue(secondFile, TreeMap.class);

        SortedSet<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());

        StringBuilder builder = new StringBuilder("{\n");
        for (String key : keys) {
            if (firstMap.containsKey(key) && !secondMap.containsKey(key)) {
                // Ключ удален во втором файле
                builder.append("  - ").append(key).append(": ").append(firstMap.get(key)).append("\n");
            } else if (!firstMap.containsKey(key) && secondMap.containsKey(key)) {
                // Ключ, добавленный во второй файл
                builder.append("  + ").append(key).append(": ").append(secondMap.get(key)).append("\n");
            } else if (!firstMap.get(key).equals(secondMap.get(key))) {
                // Значение ключа изменилось во втором файле
                builder.append("  - ").append(key).append(": ").append(firstMap.get(key)).append("\n");
                builder.append("  + ").append(key).append(": ").append(secondMap.get(key)).append("\n");
            } else {
                // Значение ключа не изменилось
                builder.append("    ").append(key).append(": ").append(firstMap.get(key)).append("\n");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
