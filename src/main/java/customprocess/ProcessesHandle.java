package customprocess;

import org.hyperic.sigar.Sigar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ProcessesHandle {

    public Sigar sigarObs;
    public String processLine;
    public String processLineAll;
    private String separator;
    public String[] processCurrentArray;
    ProcessesHandle() {
        this.separator = "%%%";
    }

    public void getAllCurrentProcesses() throws IOException {
        // get all running process in batch mode in single iteration
        ProcessBuilder procMain = new ProcessBuilder("top", "-b" ,"-n", "1");
        Process proc = procMain.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        this.processLine = br.readLine();
        while (!processLine.trim().equals("")) {
            this.processLineAll += this.processLine + this.separator;
            this.processLine = br.readLine();
        }
        this.processCurrentArray = this.processLineAll.split(separator);
    }

    public void displayAllFunctionalities() {
        System.out.println(Arrays.toString(this.processCurrentArray));
    }
}

