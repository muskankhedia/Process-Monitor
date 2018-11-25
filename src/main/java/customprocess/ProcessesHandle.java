package customprocess;

import org.hyperic.sigar.Sigar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ProcessesHandle {

    public Sigar sigarObs;
    public String processLine;
    public String processLineAll;
    private String separator;
    public String[] processCurrentArray;
    public List<List<String>> processCurrentTrimmed;
    public List<Integer> pidLists;
    private String processDetails;
    public String processDetailsAll;
    public String[] processDetailsArray;
    public List processDetailsArrayAll;

    ProcessesHandle() {
        this.separator = "%%%";
        this.processCurrentTrimmed = new ArrayList<List<String>>();
        this.pidLists = new ArrayList<Integer>();
        processDetailsArrayAll = new ArrayList();
    }

    private void filterProcesses(String[] prs) throws IOException {
        System.out.println(prs);
        for (int i =0; i< prs.length; i++) {
            String[] one = prs[i].trim().split(" ");
            List<String> pure = new ArrayList<String>();
            int count=0;
            for (String j: one) {
                ++count;
                try {
                    if (count==1 && i!=0) {
                        pidLists.add(Integer.parseInt(j.trim()));
                    } else if (count==2 && i==0) {
                        pidLists.add(1);
                    }
                } catch (Exception n) {;}
                String a = j.trim();
                if (!a.trim().equals(""))
                    pure.add(a);
            }
            this.processCurrentTrimmed.add(pure);
        }
        this.getProcessDetails_catProc(this.pidLists);
    }


    public void getAllCurrentProcesses() throws IOException, NullPointerException {
        // get all running process in batch mode in single iteration
        ProcessBuilder procSys = new ProcessBuilder("top", "-b" ,"-n", "1");
        ProcessBuilder procMain = new ProcessBuilder("ps", "-e");
        Process proc = procMain.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        this.processLine = br.readLine();
        try {
            while (true) {
                this.processLine = br.readLine();
                if (this.processLine.trim().equals("")) {
                    break;
                }
                this.processLineAll += this.processLine + this.separator;
            }
        } catch (NullPointerException e) {
            System.out.println("Finished scanning");
        }
        this.processCurrentArray = this.processLineAll.split(separator);
        this.filterProcesses(this.processCurrentArray);
    }

    public void getProcessDetails_catProc(List<Integer> ps) throws IOException {
        int count=0;
        BufferedReader Br;
        for (int pid: ps) {
            ProcessBuilder prr = new ProcessBuilder("cat", "/proc/"+String.valueOf(pid)+"/status");
            Process prrRun = prr.start();
            Br = new BufferedReader(new InputStreamReader(prrRun.getInputStream()));
            try {
                while (true) {
                    this.processLine = Br.readLine();
                    if (this.processLine.trim().equals("")) {
                        break;
                    }
                    this.processDetailsAll += this.processLine.replaceAll(",", "|") + this.separator;

                }
            } catch (Exception e) {;}
            this.processDetailsArray = this.processDetailsAll.split(this.separator);
            this.processDetailsArrayAll.add(Arrays.toString(this.processDetailsAll.split(this.separator)));
            this.processDetailsAll = "";
        }
    }

    public void displayAllFunctionalities() {
//        System.out.println(Arrays.toString(this.processCurrentArray));
        System.out.println(this.processCurrentTrimmed);
        System.out.println(this.pidLists);
        System.out.println((this.processDetailsArrayAll));
    }
}

