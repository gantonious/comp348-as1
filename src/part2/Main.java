package part2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * title: Main.java
 * description: The main entry for the Source Viewer
 * date: February 22, 2018
 * @author George Antonious
 * @version 1.0
 * @copyright 2018 George Antonious
 *
 * I declare that this assignment is my own work and that all material
 * previously written or published in any source by any other person
 * has been duly acknowledged in the assignment. I have not submitted
 * this work, or a significant part thereof, previously as part of any
 * academic program. In submitting this assignment I give permission to
 * copy it for assessment purposes only.
 *
 * The usage, design, and test-cases for this part can be found in the
 * README.md file in the root of this project. It is recommended to view
 * it in a markdown reader.
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
            System.out.println(highlightFilterWordIn(line, filter));
        }
    }

    public static String highlightFilterWordIn(String line, String filter) {
        return highlightFilterWordIn(line, filter, 0);
    }

    public static String highlightFilterWordIn(String line, String filter, int startFrom) {
        Pattern filterPattern = Pattern.compile(filter);
        Matcher filterMatcher = filterPattern.matcher(line);

        String startRedSequence = "\033[31m";
        String endRedSequence = "\033[0m";

        if (filterMatcher.find(startFrom)) {
            int startIndex = filterMatcher.start();
            int endIndex = filterMatcher.end();
            int nextStartIndex = endIndex + startRedSequence.length() + endRedSequence.length();

            line = line.substring(0, startIndex) + startRedSequence +
                    line.substring(startIndex, endIndex) + endRedSequence +
                    line.substring(endIndex);
            
            return highlightFilterWordIn(line, filter, nextStartIndex);
        }

        return line;
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
