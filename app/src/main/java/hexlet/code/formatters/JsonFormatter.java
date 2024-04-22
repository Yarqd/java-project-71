package hexlet.code.formatters;

import hexlet.code.utils.Diff;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class JsonFormatter implements Formatter {
    @Override
    public String format(List<Diff> diffs) throws Exception {
        List<Map<String, Object>> changes = new ArrayList<>();
        for (Diff diff : diffs) {
            Map<String, Object> change = new HashMap<>();
            change.put("key", diff.getKey());
            change.put("type", diff.getType().toString().toLowerCase());

            switch (diff.getType()) {
                case ADDED:
                    change.put("newValue", diff.getNewValue());
                    break;
                case REMOVED:
                    change.put("oldValue", diff.getOldValue());
                    break;
                case UPDATED:
                    change.put("oldValue", diff.getOldValue());
                    change.put("newValue", diff.getNewValue());
                    break;
                default:
                    break;
            }
            changes.add(change);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(changes);
    }
}
