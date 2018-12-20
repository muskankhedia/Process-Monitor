package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

 class First  {
    public static void main(String[] args) {
        JFrame Main = new JFrame();
        JLabel welcome = new JLabel("Hi! Welcome to Process-Monitor");
        welcome.setFont(new Font("Arial", Font.PLAIN, 20));
        welcome.setBounds(300, 300, 2000, 20);
        final JButton next = new JButton();
        next.setText("Next");
        next.setBounds(430, 400, 80, 40);
        next.setBackground(Color.BLUE);
        next.setForeground(Color.WHITE);
        next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (next.isEnabled()) {
                    JOptionPane.showMessageDialog(null, "Next button clicked");
                }
            }
        });
        Main.add(welcome, SwingConstants.CENTER);
        Main.add(next, SwingConstants.CENTER);
        Main.setSize(900, 700);
        Main.setLayout(null);
        Main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main.setVisible(true);
    }
}
