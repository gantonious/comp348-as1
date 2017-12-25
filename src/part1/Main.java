package part1;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 2017-12-25.
 */
public class Main {
    private final static int COUNT_ACCESSES_OPTION = 1;
    private final static int COUNT_TOTAL_BYTES_TRANSMITTED_OPTION = 2;
    private final static int COUNT_TOTAL_BYTES_BY_HOST_OPTION = 3;

    private static Map<Integer, ILogAnalyzer> optionsMap = new HashMap<>();
    private static WebLogFileParser webLogFileParser;

    public static void main(String[] args) {
        registerLogAnalyzes();
        webLogFileParser = new WebLogFileParser();

        try {
            runProgram(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Usage: java MyPooledWeblog logname option");
            System.exit(1);
        }
    }

    private static void registerLogAnalyzes() {
        optionsMap = new HashMap<>();
        optionsMap.put(COUNT_ACCESSES_OPTION, new AccessesLogAnalyzer(System.out));
        optionsMap.put(COUNT_TOTAL_BYTES_TRANSMITTED_OPTION, new TotalBytesTransmittedLogAnalyzer(System.out));
        optionsMap.put(COUNT_TOTAL_BYTES_BY_HOST_OPTION, new TotalBytesByHostLogAnalyzer(System.out));
    }

    private static void runProgram(String[] args) throws Exception {
        String logFileName = getFileNameFrom(args);
        int option = getOptionFrom(args);
        ILogAnalyzer logAnalyzer = getLogAnalyzerFrom(option);

        WebLog webLog = webLogFileParser.parseWebLogFile(new FileInputStream(logFileName));
        logAnalyzer.analyzeWebLog(webLog);
    }

    private static String getFileNameFrom(String[] args) {
        try {
            return args[0];
        } catch (Exception e) {
            throw new RuntimeException("Filename must be specified as a string.");
        }
    }

    private static int getOptionFrom(String[] args) {
        try {
            return Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new RuntimeException("Option must be specified as an integer.");
        }
    }

    private static ILogAnalyzer getLogAnalyzerFrom(int option) {
        try {
            return optionsMap.get(option);
        } catch (Exception e) {
            throw new RuntimeException("There is no log analyzer for the selected option.");
        }
    }
}