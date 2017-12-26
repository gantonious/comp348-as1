package part1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by George on 2017-12-25.
 */
public class WebLogFileParser {
    private final static int NUM_THREADS = 4;
    private ExecutorService executorService;

    public WebLogFileParser() {
        executorService = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public WebLog parseWebLogFile(InputStream webLogInputStream) {
        try {
            return tryToParseWebLogFile(webLogInputStream);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private WebLog tryToParseWebLogFile(InputStream webLogInputStream) throws Exception {
        List<Future<WebLogEntry>> futureWebLogEntries = startParsing(webLogInputStream);
        List<WebLogEntry> webLogEntries = retrieveWebLogEntriesFrom(futureWebLogEntries);
        return new WebLog(webLogEntries);
    }

    private List<Future<WebLogEntry>> startParsing(InputStream webLogInputStream) throws Exception {
        List<Future<WebLogEntry>> futureWebLogEntries = new ArrayList<>();

        InputStreamReader inputStreamReader = new InputStreamReader(webLogInputStream);
        BufferedReader inReader = new BufferedReader(inputStreamReader);

        for (String nextLine = inReader.readLine(); nextLine != null; nextLine = inReader.readLine()) {
            Future<WebLogEntry> webLogEntryFuture = executorService.submit(new ParsingTask(nextLine));
            futureWebLogEntries.add(webLogEntryFuture);
        }

        inReader.close();

        return futureWebLogEntries;
    }

    private List<WebLogEntry> retrieveWebLogEntriesFrom(List<Future<WebLogEntry>> futureWebLogEntries) {
        List<WebLogEntry> webLogEntries = new ArrayList<>();

        for (Future<WebLogEntry> webLogEntryFuture: futureWebLogEntries) {
            try {
                webLogEntries.add(webLogEntryFuture.get());
            } catch (Exception e) {
                // if we fail to parse a line we just won't include it in
                // representation of the web log
            }
        }

        return webLogEntries;
    }

    public static class ParsingTask implements Callable<WebLogEntry> {
        private String webLogFileLine;

        public ParsingTask(String webLogFileLine) {
            this.webLogFileLine = webLogFileLine;
        }

        @Override
        public WebLogEntry call() {
            String[] spaceSeparatedLine = webLogFileLine.split(" ");
            String hostname = spaceSeparatedLine[0];
            int bytesTransmitted = Integer.parseInt(spaceSeparatedLine[9]);

            return new WebLogEntry(hostname, bytesTransmitted);
        }
    }

    public static class ParsingException extends RuntimeException {
        public ParsingException(Exception innerException) {
            super("Failed to parse web log filed because of: " + innerException.getMessage());
        }
    }
}
