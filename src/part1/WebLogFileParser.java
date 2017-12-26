package part1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by George on 2017-12-25.
 */
public class WebLogFileParser {

    public WebLog parseWebLogFile(InputStream webLogInputStream) {
        try {
            return tryToParseWebLogFile(webLogInputStream);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private WebLog tryToParseWebLogFile(InputStream webLogInputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(webLogInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        List<WebLogEntry> webLogEntries = bufferedReader.lines()
                .map(this::parseWebLogFileLine)
                .collect(Collectors.toList());

        return new WebLog(webLogEntries);
    }

    private WebLogEntry parseWebLogFileLine(String line) {
        String[] spaceSeparatedLine = line.split(" ");
        String hostname = spaceSeparatedLine[0];
        int bytesTransmitted = Integer.parseInt(spaceSeparatedLine[9]);

        return new WebLogEntry(hostname, bytesTransmitted);
    }

    public static class ParsingException extends RuntimeException {
        public ParsingException(Exception innerException) {
            super("Failed to parse web log filed because of: " + innerException.getMessage());
        }
    }
}
