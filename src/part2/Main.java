package part2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 2017-12-26.
 */
public class Main {

    public static void main(String[] args) {
        try {
            runProgram(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void runProgram(String[] args) throws Exception {
        String webUrl = getUrlFrom(args);
        String filterString = getFilterFrom(args);

        List<String> filteredStrings = getLinesThatContainFilterStringFrom(webUrl, filterString);
        displayFilteredLines(filteredStrings, filterString);
    }

    private static List<String> getLinesThatContainFilterStringFrom(String url, String filterString) throws Exception {
        URL webUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) webUrl.openConnection();
        try (InputStream inputStream = httpURLConnection.getInputStream()) {
            return filter(inputStream, filterString);
        }
    }

    private static List<String> filter(InputStream inputStream, String filterString) throws Exception {
        List<String> filteredLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            for (String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine()) {
                if (nextLine.contains(filterString)) {
                    filteredLines.add(nextLine);
                }
            }
        }

        return filteredLines;
    }

    private static void displayFilteredLines(List<String> filteredLines, String filter) {
        for (String line : filteredLines) {
            System.out.println(line);
        }
    }

    private static String getUrlFrom(String[] args) {
        try {
            return args[0];
        } catch (Exception e) {
            throw new RuntimeException("Web url must be specified.");
        }
    }

    private static String getFilterFrom(String[] args) {
        try {
            return args[1];
        } catch (Exception e) {
            throw new RuntimeException("Filter string must be specified.");
        }
    }
}
