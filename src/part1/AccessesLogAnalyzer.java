package part1;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by George on 2017-12-25.
 */
public class AccessesLogAnalyzer implements ILogAnalyzer {
    private PrintStream outputPrintStream;

    public AccessesLogAnalyzer(PrintStream outputPrintStream) {
        this.outputPrintStream = outputPrintStream;
    }

    @Override
    public void analyzeWebLog(WebLog webLog) {
        LinkedHashMap<String, Integer> accessesByHost = new LinkedHashMap<>();

        for (WebLogEntry webLogEntry: webLog.getEntries()) {
            String remoteHost = webLogEntry.getRemoteHostIpAddress();
            int lastCount = accessesByHost.getOrDefault(remoteHost, 0);
            accessesByHost.put(remoteHost, lastCount + 1);
        }

        printOutAnalysisFor(accessesByHost);
    }

    private void printOutAnalysisFor(LinkedHashMap<String, Integer> accessesByHost) {
        for (Map.Entry<String, Integer> analysisEntry : accessesByHost.entrySet()) {
            String hostName = analysisEntry.getKey();
            Integer count = analysisEntry.getValue();
            outputPrintStream.println(String.format("%s was accessed %d time(s)", hostName, count));
        }
    }
}
