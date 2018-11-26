import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

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



    public static void main(String[] args) throws IOException, SigarException {
        ProcessBuilder p = new ProcessBuilder("sudo","nethogs", "-t");
        Process pcc = p.start();
        Sigar sigar = new Sigar();
        BufferedReader br = new BufferedReader(new InputStreamReader(pcc.getInputStream()));
        String ll = "";
        while (true) {
            ll = br.readLine();
            System.out.println(ll);
            System.out.println(pcc.getOutputStream());
            if (ll==null)
                break;
        }

    }
}
