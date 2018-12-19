import customprocess.*;

import java.io.IOException;

public class Core {

    private static void manualTestCases() throws IOException, NullPointerException {
        ProcessesHandle processHandler = new ProcessesHandle();
        boolean resultProc = processHandler.runTests();

        NetworkHandle netw = new NetworkHandle();
        boolean resultNetwork = netw.runTests();

        if ( resultNetwork && resultProc) {
            System.out.println("Test cases passed");
        } else {
            System.err.println("Error encountered in manual test cases");
        }

    }

    public static void main(String[] args) throws IOException {
        manualTestCases();
    }
}
