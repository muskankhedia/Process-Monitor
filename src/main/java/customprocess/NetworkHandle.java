package customprocess;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;

public class NetworkHandle {
    private Sigar sigar;
    private NetInterfaceStat networkStats;
    private NetInterfaceConfig networkConfig;
    private List<String> networkCardsList;
    private List<Object> networkStatsList;
    private long networkSpeed;
    private List<Long> networkSpeedList;

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

    public List<Long> getAllNetworkSpeeds() throws SigarException {
        for (String name: this.sigar.getNetInterfaceList()) {
            this.networkSpeedList.add(this.sigar.getNetInterfaceStat(name).getSpeed());
        }
        return this.networkSpeedList;
    }



    public void runFunctionalities() throws SigarException {
        this.getNetworkCards();
        this.getNetworkCardsStats();
    }


}
