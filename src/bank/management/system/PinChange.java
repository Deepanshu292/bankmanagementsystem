package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {
    JPasswordField t1, t2;
    JButton change, back;
    String pin;

    public PinChange(String pin) {
        this.pin = pin;
        setTitle("Change PIN");
        setLayout(null);

        JLabel l1 = new JLabel("Enter New PIN:");
        l1.setBounds(120, 80, 150, 25); add(l1);
        t1 = new JPasswordField(); t1.setBounds(280, 80, 200, 25); add(t1);

        JLabel l2 = new JLabel("Re-enter PIN:");
        l2.setBounds(120, 120, 150, 25); add(l2);
        t2 = new JPasswordField(); t2.setBounds(280, 120, 200, 25); add(t2);

        change = new JButton("Change");
        change.setBounds(180, 170, 120, 30);
        change.addActionListener(this); add(change);

        back = new JButton("Back");
        back.setBounds(320, 170, 120, 30);
        back.addActionListener(this); add(back);

        setSize(620, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
            return;
        }
        String p1 = new String(t1.getPassword()).trim();
        String p2 = new String(t2.getPassword()).trim();
        if (p1.isEmpty() || p2.isEmpty()) { JOptionPane.showMessageDialog(this, "Fill both fields"); return; }
        if (!p1.equals(p2)) { JOptionPane.showMessageDialog(this, "PINs do not match"); return; }

        try {
            Conn c = new Conn();
            PreparedStatement ps1 = c.c.prepareStatement("UPDATE login SET pin=? WHERE pin=?");
            ps1.setString(1, p1); ps1.setString(2, pin); ps1.executeUpdate();

            PreparedStatement ps2 = c.c.prepareStatement("UPDATE bank SET pin=? WHERE pin=?");
            ps2.setString(1, p1); ps2.setString(2, pin); ps2.executeUpdate();

            JOptionPane.showMessageDialog(this, "PIN changed successfully");
            setVisible(false);
            new Transactions(p1).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}
