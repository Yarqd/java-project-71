package hexlet.code.formatters;

import hexlet.code.Diff;
import java.util.List;

public interface Formatter {
    String format(List<Diff> diffs) throws Exception;
}
