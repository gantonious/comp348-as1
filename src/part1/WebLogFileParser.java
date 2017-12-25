package part1;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by George on 2017-12-25.
 */
public class WebLogFileParser {
    public WebLog parseWebLogFile(InputStream webLogInputStream) {
        return new WebLog(new ArrayList<>());
    }
}
