import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {

    public static void networksIntSigar() throws SigarException {
        Sigar sigar = new Sigar();
        NetInterfaceStat stats = null;
        String[] networkIntList = sigar.getNetInterfaceList();
        for (String name: networkIntList) {
            stats = sigar.getNetInterfaceStat(name);

            System.out.println(name);
            System.out.println(stats);
            System.out.println("Speed: "+ stats.getSpeed());
        }
    }

    public static void installer() throws SigarException, IOException {
//        ProcessBuilder pp = new ProcessBuilder("sudo","nethogs","-d","2");
//        Process pproc = pp.start();
//        BufferedReader br = new BufferedReader(new InputStreamReader(pproc.getInputStream()));
//        String lines="";
//        while (true) {
//            lines = br.readLine();
//            System.out.println(lines);
//            if (lines==null)
//                break;
//        }
        Runtime RT = Runtime.getRuntime();
        Process pp = RT.exec("netstat -tunap");
//        Scanner ssc = new Scanner(pp.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(pp.getInputStream()));
        String lines= "";
        while (true) {
            lines = br.readLine();
            System.out.println(lines);
            if (lines==null)
                break;
        }

    }

//    public static void main(String[] args) throws IOException, SigarException {
//        String pass = "hsinghbb";
////        ProcessBuilder p = new ProcessBuilder("echo","hsinghbb","|","sudo","-S","nethogs", "-t");
//        /**
//         * disable root permissions in nethogs, type the following
//         * sudo setcap "cap_net_admin,cap_net_raw=ep" /usr/sbin/nethogs
//          */
//
//        final Process pcc = Runtime.getRuntime().exec("nethogs -t");
////        Process pcc = p.start();
//        Sigar sigar = new Sigar();
//        BufferedReader br = new BufferedReader(new InputStreamReader(pcc.getInputStream()));
//        String ll = "";Boolean exit = false;
//        Timer timer = new Timer();
//        while (true) {
//            ll = br.readLine();
//            System.out.println(ll);
//            if (ll==null)
//                return;
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    pcc.destroy();
//                }
//            }, 5*1000);
//        }
//
//    }

    public static void main(String[] args){
        try {
            Process p = new ProcessBuilder("git", "branch").start();
            p.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            System.out.println(p.getInputStream());
            String line = br.readLine();
            System.out.println(line);
            String branch = "";
            while (line != null) {
                if (!line.startsWith("*")) {
                    line = br.readLine();
                    break;
                }
                branch = line.substring(2);

            }
            System.out.println(branch);
//            System.out.println("Hi");
        }
        catch (Exception e) {
            System.out.println("H");
        }
    }

}
