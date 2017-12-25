package part1;

import java.io.PrintStream;

/**
 * Created by George on 2017-12-25.
 */
public class TotalBytesTransmittedLogAnalyzer implements ILogAnalyzer {
    private PrintStream outputPrintStream;

    public TotalBytesTransmittedLogAnalyzer(PrintStream outputPrintStream) {
        this.outputPrintStream = outputPrintStream;
    }

    @Override
    public void analyzeWebLog(WebLog webLog) {
        int totalBytesTransmitted = webLog.getEntries().stream()
                .map(w -> w.getBytesTransmitted())
                .reduce(0, (sum, nextBytesTransmitted) -> sum + nextBytesTransmitted);

        outputPrintStream.println(String.format("Total bytes transmitted: %d byte(s)."));
    }
}
