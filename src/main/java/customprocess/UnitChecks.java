package customprocess;
import customprocess.ProcessesHandle;

import java.io.IOException;

public class UnitChecks {
    public static void main(String[] args) throws IOException {
        ProcessesHandle pr = new ProcessesHandle();
        pr.getAllCurrentProcesses();
        pr.displayAllFunctionalities();
    }
}
