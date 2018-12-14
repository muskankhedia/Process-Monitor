package customprocess;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class NetworkHandle {
    private List<String> networkCardsList;
    private List<Object> networkStatsList;
    private long networkSpeed;
    private Map<String, Long> networkSpeedListCollection;
    private String separator="%%%";

    NetworkHandle() {
        this.networkCardsList = new ArrayList<String>();
    }

    /*public List<String> getNetworkCards() throws SigarException {
        for (String name: this.sigar.getNetInterfaceList()) {
            this.networkCardsList.add(name);
            System.out.println("X");
        }
        return this.networkCardsList;
    }*/
/*
    public List<Object> getNetworkCardsStats() throws SigarException {
        for (String name: this.sigar.getNetInterfaceList()) {

            this.networkStatsList.add(this.sigar.getNetInterfaceStat(name));
        }
        return this.networkStatsList;
    }

    public long getNetworkCardSpeed(String cardName) throws SigarException {
        this.networkSpeed = this.sigar.getNetInterfaceStat(cardName).getSpeed();
        return this.sigar.getNetInterfaceStat(cardName).getSpeed();
    }

    public Map<String, Long> getAllNetworkSpeeds() throws SigarException {
        for (String name: this.sigar.getNetInterfaceList()) {
            this.networkSpeedListCollection.put(name, this.sigar.getNetInterfaceStat(name).getSpeed());
        }
        return this.networkSpeedListCollection;
    }
*/
    public List<Object> netstatPID(String pid) throws IOException {
        Process netstats = Runtime.getRuntime().exec("netstat -tanp | grep "+(pid));
        Scanner SS = new Scanner(netstats.getInputStream());
        String line="", sentence="";
        Boolean exit=false;
        while (true) {
            try {
                line = SS.nextLine();
                if (line == null)
                    break;
                sentence += line + this.separator;
            } catch (Exception e) {
                exit=true;
            } if (exit)
                break;
        }
        String[] aa = sentence.split(this.separator);
        String[] headers = aa[1].split(" ");
        // making list of it
        List<String> headings = new ArrayList<String>();
        for (int n=0;n< headers.length; n++) {
            if (!headers[n].trim().equals("") && !(n==3 || n ==15 || n==32) && !(n==4 || n==16 || n==33))
                headings.add(headers[n].trim());
            else if ((n==3|| n==15 || n == 32))
                headings.add(headers[n].trim()+" "+headers[n+1].trim());
        }
        List<Object> processObjects = new ArrayList<Object>();
        for (int j=2; j< aa.length; j++){ // first two being the headings
            Map<String, String> abc = new LinkedHashMap<String, String>();
            String[] b = aa[j].split(" ");
            // filter empty cells from b
            List<String> b_filtered = new ArrayList<String>();
            int count = 0;
            for (int x=0;x< b.length; x++) {
                if (!b[x].trim().equals("") && x!=(b.length-1))
                    b_filtered.add(b[x].trim());
                else if (x==(b.length-1))
                    if (!b[x].startsWith(pid))
                        b_filtered.add("%%%removethis%%%");
                    else
                        b_filtered.add(b[x].trim());
            }
            if (b_filtered.size()==headings.size())
                for (int k = 0; k < b_filtered.size(); k++)
                    abc.put(headings.get(k).trim(), b_filtered.get(k).trim());
            else
                System.err.println("Size incompatible");
            if (!(abc.get("PID/Program name")=="%%%removethis%%%"))
                processObjects.add(abc);
        }
        return processObjects;
    }

    public List<Object> netstatALL() throws IOException {
        Process netstats = Runtime.getRuntime().exec("netstat -tanp");
        Scanner SS = new Scanner(netstats.getInputStream());
        String line="", sentence="";
        Boolean exit=false;
        while (true) {
            try {
                line = SS.nextLine();
                if (line == null)
                    break;
                sentence += line + this.separator;
            } catch (Exception e) {
                exit=true;
            } if (exit)
                break;
        }
        String[] aa = sentence.split(this.separator);
        String[] headers = aa[1].split(" ");
        // making list of it
        List<String> headings = new ArrayList<String>();
        for (int n=0;n< headers.length; n++) {
            if (!headers[n].trim().equals("") && !(n==3 || n ==15 || n==32) && !(n==4 || n==16 || n==33))
                headings.add(headers[n].trim());
            else if ((n==3|| n==15 || n == 32))
                headings.add(headers[n].trim()+" "+headers[n+1].trim());
        }
        List<Object> processObjects = new ArrayList<Object>();
        for (int j=2; j< aa.length; j++){ // first two being the headings
            Map<String, String> abc = new LinkedHashMap<String, String>();
            String[] b = aa[j].split(" ");
            // filter empty cells from b
            List<String> b_filtered = new ArrayList<String>();
            int count = 0;
            for (int x=0;x< b.length; x++) {
                if (!b[x].trim().equals(""))
                    b_filtered.add(b[x].trim());
            }
            if (b_filtered.size()==headings.size())
                for (int k = 0; k < b_filtered.size(); k++)
                    abc.put(headings.get(k).trim(), b_filtered.get(k).trim());
            else
                System.err.println("Size incompatible");
            processObjects.add(abc);
        }
        return processObjects;
    }

//    public void networkActivityMonitoring() throws IOException {
//        /**
//         * disable root permissions in nethogs, type the following
//         * sudo setcap "cap_net_admin,cap_net_raw=ep" /usr/sbin/nethogs
//         */
//        final Process pcc = Runtime.getRuntime().exec("nethogs -t");
//        BufferedReader br = new BufferedReader(new InputStreamReader(pcc.getInputStream()));
//        String ll = "", sentence="";
//        String aa[];
//        Timer timer = new Timer();
//        boolean exit = false;
//        int c=10100;
//        ll = br.readLine();
//        String headers[] = {"Program", "Sent", "Received"};
//        List<Object> processObjects = new ArrayList<Object>();
//        if(ll == null){
//            System.out.println("Hii");
//        }
//        else {
////            while (true) {
////                ll = br.readLine().trim();
////                sentence = ll;
////                System.out.print(sentence);
////                if(sentence.equals("Refreshing:")){
////                    System.out.println("asdsf");
////                }
////                aa = sentence.split("%%%%%%");
////                if (aa.length >= 2) {
////                    sentence = "";
////                    while (true) {
////                        ll = br.readLine();
////                        sentence += ll + this.separator;
////                        aa = sentence.split("%%%%%%");
////                        if (aa.length >= 2) {
////                            Map<String, String> line = new LinkedHashMap<String, String>();
////                            String[] b_process = aa[0].split("%%%");
////                            for (int j = 1; j < b_process.length; j++) {
////                                String[] c_process = b_process[j].split("\t");
////                                if (c_process.length == 3)
////                                    for (int k = 0; k < 3; k++)
////                                        line.put(headers[k].trim(), c_process[k].trim());
////                                processObjects.add(line);
////                                System.out.println(processObjects);
////                                break;
////                            }
////
////                        }
////                    }
//                while(ll == "Refreshing:"){
//                    ll = br.readLine().trim();
//                    System.out.println(ll);
//
//                    if(ll.equals("")){
//                        break;
//                    }
//                    else{
//                        sentence += ll + this.separator;
//                        System.out.println(sentence);
//                    }
//
//                }
////                sentence += ll + this.separator;
////                String[] aa = sentence.split("%%%%%%");
////                if (aa.length == 10)
////                    break;
//
////                System.out.println(sentence);
////                List<Object> processObjects = new ArrayList<Object>();
////                String[] newProcess = sentence.split("%%%%%%");
////                String headers[] = {"Program", "Sent", "Received"};
////                for (int i = 1; i < newProcess.length; i++) {
////                    Map<String, String> line = new LinkedHashMap<String, String>();
////                    String[] b_process = newProcess[i].split("%%%");
////                    for (int j = 1; j < b_process.length; j++) {
////                        String[] c_process = b_process[j].split("\t");
////                        if (c_process.length == 3)
////                            for (int k = 0; k < 3; k++)
////                                line.put(headers[k].trim(), c_process[k].trim());
////                        processObjects.add(line);
////                        System.out.println(processObjects);
////                    }
////                    //System.out.println(processObjects);
////
////                }
//                }
//            }
//
////        System.out.println(processObjects);
//        }
//        pcc.destroy();
//    }

    public void networkActivityMonitoring() throws IOException{
        final Process pcc = Runtime.getRuntime().exec("nethogs -t");
        BufferedReader br = new BufferedReader(new InputStreamReader(pcc.getInputStream()));
        String ll = "", sentence="", next="";
        String headers[] = {"Program", "Sent", "Received"};
        ll = br.readLine();
        List<Object> processObjects = new ArrayList<Object>();

        if(ll == null){
            System.out.println("No internet Connection");
        }
        else{
            while(true){
                ll = br.readLine();
                if(ll.equals("Refreshing:")) {
                    processObjects.clear();
                    while(true){
                        ll = br.readLine();
                        if(ll.equals("")) {
                            break;
                        }
                        sentence = ll;
                        String[] process_details = sentence.split("\t");
                        Map<String, String> line = new LinkedHashMap<String, String>();
                        if (process_details.length == 3) {

                            for (int k = 0; k < 3; k++)
                                line.put(headers[k].trim(), process_details[k].trim());
                        }
//                        System.out.println(line);
                        processObjects.add(line);
                    }
                    System.out.println(processObjects);
                }
            }
        }
    }


    public void runFunctionalities(){
    }



    public static void main(String[] args) throws IOException {
        System.out.println(new NetworkHandle().netstatALL());
        NetworkHandle obj = new NetworkHandle();
         obj.networkActivityMonitoring();
    }
}
