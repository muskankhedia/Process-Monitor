
import javax.swing.*;
import java.io.*;

public class Core {
    public static void main(String[] args) {
        JFrame Main = new JFrame();
        JLabel welcome = new JLabel();
        welcome.setText("Hi");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setVerticalAlignment(SwingConstants.CENTER);
        welcome.setSize(50, 50);
        JButton next = new JButton();
        next.setText("Next");
        next.setBounds(50, 50, 30, 30);
        Main.add(welcome);
        Main.add(next);
        Main.setSize(300, 200);
        Main.setLayout(null);
        Main.setVisible(true);
    }
}
