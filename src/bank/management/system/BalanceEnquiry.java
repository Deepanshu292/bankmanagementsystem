package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BalanceEnquiry extends JFrame {
    public BalanceEnquiry(String pin) {
        setTitle("Balance Enquiry");
        setLayout(new BorderLayout());

        double bal = 0;
        try {
            Conn c = new Conn();
            PreparedStatement ps = c.c.prepareStatement("SELECT type, amount FROM bank WHERE pin=?");
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bal += rs.getString("type").equalsIgnoreCase("Deposit") ? rs.getDouble("amount") : -rs.getDouble("amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }

        JLabel l = new JLabel("Your current balance is: Rs. " + bal, SwingConstants.CENTER);
        l.setFont(new Font("System", Font.BOLD, 20));
        add(l, BorderLayout.CENTER);

        setSize(420, 180);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
