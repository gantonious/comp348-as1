package part1;

import part1.analyzers.AccessesLogAnalyzer;
import part1.analyzers.ILogAnalyzer;
import part1.analyzers.TotalBytesByHostLogAnalyzer;
import part1.analyzers.TotalBytesTransmittedLogAnalyzer;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * title: Main.java
 * description: The main entry for the Web Log analyzer
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
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
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