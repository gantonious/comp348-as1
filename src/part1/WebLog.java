package part1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 2017-12-25.
 */
public class WebLog {
    private List<WebLogEntry> entries;

    public WebLog(List<WebLogEntry> entries) {
        this.entries = new ArrayList<>();
        this.entries.addAll(entries);
    }

    public List<WebLogEntry> getEntries() {
        return new ArrayList<>(entries);
    }
}
