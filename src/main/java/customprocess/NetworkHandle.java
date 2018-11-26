package customprocess;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.IOException;
import java.util.*;

public class NetworkHandle {
    private Sigar sigar;
    private NetInterfaceStat networkStats;
    private NetInterfaceConfig networkConfig;
    private List<String> networkCardsList;
    private List<Object> networkStatsList;
    private long networkSpeed;
    private Map<String, Long> networkSpeedListCollection;
    private String separator="%%%";

    NetworkHandle() {
        this.sigar = new Sigar();
        this.networkCardsList = new ArrayList<String>();
    }

    public List<String> getNetworkCards() throws SigarException {
        for (String name: this.sigar.getNetInterfaceList()) {
            this.networkCardsList.add(name);
        }
        return this.networkCardsList;
    }

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

    public List<Object> netstatPID(String pid) throws IOException {
        Process netstats = Runtime.getRuntime().exec("netstat -tanp | grep "+(pid));
        Scanner SS = new Scanner(netstats.getInputStream());
        System.out.println("from netstatas");
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
        System.out.println("Headings below");
        System.out.println(headings);
        System.out.println(aa.length);
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

    public void runFunctionalities() throws SigarException {
        this.getNetworkCards();
        this.getNetworkCardsStats();
    }

    public static void main(String[] args) throws IOException {
        System.out.print(new NetworkHandle().netstatPID(Integer.toString(2059)));
    }
}
