package customprocess;

import org.hyperic.sigar.Sigar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessesHandle {

    private Sigar sigarObs;
    private String processLine;
    private String processLineAll;
    private String separator;
    public String[] processCurrentArray;
    public List<List<String>> processCurrentTrimmed;
    public List<Integer> pidLists;
    private String processDetails;
    public String[] processDetailsArray;
    public List processDetailsArrayAll;
    public List processDetailsArrayAllStringified;

    ProcessesHandle() {
        this.separator = "%%%";
        this.processCurrentTrimmed = new ArrayList<List<String>>();
        this.pidLists = new ArrayList<Integer>();
        processDetailsArrayAll = new ArrayList();
        this.processDetailsArrayAllStringified = new ArrayList();
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

    /**
     * USE CASE EXAMPLE ( PID 13021 -> JAVA )
     * Name:   java
     * Umask:  0002
     * State:  S (sleeping)
     * Tgid:   13021
     * Ngid:   0
     * Pid:    13021
     * PPid:   3638
     * TracerPid:      0
     * Uid:    1000    1000    1000    1000
     * Gid:    1000    1000    1000    1000
     * FDSize: 512
     * Groups: 10 1000
     * NStgid: 13021
     * NSpid:  13021
     * NSpgid: 1636
     * NSsid:  1636
     * VmPeak:  4266236 kB
     * VmSize:  4266236 kB
     * VmLck:         0 kB
     * VmPin:         0 kB
     * VmHWM:    152952 kB
     * VmRSS:    151852 kB
     * RssAnon:          132448 kB
     * RssFile:           19364 kB
     * RssShmem:             40 kB
     * VmData:   354916 kB
     * VmStk:       140 kB
     * VmExe:         4 kB
     * VmLib:     22492 kB
     * VmPTE:       696 kB
     * VmSwap:        0 kB
     * HugetlbPages:          0 kB
     * CoreDumping:    0
     * Threads:        23
     * SigQ:   1/47404
     * SigPnd: 0000000000000000
     * ShdPnd: 0000000000000000
     * SigBlk: 0000000000000004
     * SigIgn: 0000000000000000
     * SigCgt: 2000000181005ccf
     * CapInh: 0000000000000000
     * CapPrm: 0000000000000000
     * CapEff: 0000000000000000
     * CapBnd: 0000003fffffffff
     * CapAmb: 0000000000000000
     * NoNewPrivs:     0
     * Seccomp:        0
     * Speculation_Store_Bypass:       thread vulnerable
     * Cpus_allowed:   ff
     * Cpus_allowed_list:      0-7
     * Mems_allowed:   00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000001
     * Mems_allowed_list:      0
     * voluntary_ctxt_switches:        1
     * nonvoluntary_ctxt_switches:     0
     * */

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
            this.processDetailsArrayAll.add(this.processDetailsAll.split(this.separator));
            this.processDetailsArrayAllStringified.add(Arrays.toString(this.processDetailsAll.split(this.separator)));
            this.processDetailsAll = "";
        }
    }

    public void displayAllFunctionalities() {
        System.out.println(this.processCurrentTrimmed);
        System.out.println(this.pidLists);
        System.out.println((this.processDetailsArrayAllStringified));
    }

    public void runFunctionalities() throws IOException, NullPointerException, NumberFormatException {
        this.getAllCurrentProcesses();
    }
}

