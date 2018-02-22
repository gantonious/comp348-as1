package part1.analyzers;

import part1.WebLog;
import part1.WebLogEntry;
import part1.analyzers.ILogAnalyzer;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by George on 2017-12-25.
 */
public class TotalBytesByHostLogAnalyzer implements ILogAnalyzer {
    private PrintStream outputPrintStream;

    public TotalBytesByHostLogAnalyzer(PrintStream outputPrintStream) {
        this.outputPrintStream = outputPrintStream;
    }

    @Override
    public void analyzeWebLog(WebLog webLog) {
        LinkedHashMap<String, Integer> bytesByHost = new LinkedHashMap<>();

        for (WebLogEntry webLogEntry: webLog.getEntries()) {
            String remoteHost = webLogEntry.getRemoteHostIpAddress();
            int lastTotalBytes = bytesByHost.getOrDefault(remoteHost, 0);
            bytesByHost.put(remoteHost, lastTotalBytes + webLogEntry.getBytesTransmitted());
        }

        printOutAnalysisFor(bytesByHost);
    }

    private void printOutAnalysisFor(LinkedHashMap<String, Integer> accessesByHost) {
        for (Map.Entry<String, Integer> analysisEntry : accessesByHost.entrySet()) {
            String hostName = analysisEntry.getKey();
            Integer count = analysisEntry.getValue();
            outputPrintStream.println(String.format("%s transmitted %d byte(s)", hostName, count));
        }
    }
}
