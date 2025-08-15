package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Withdraw extends JFrame implements ActionListener {
    JTextField amountField;
    JButton withdrawBtn, backBtn;
    String pin;

    public Withdraw(String pin) {
        this.pin = pin;
        setTitle("Withdraw");
        setLayout(null);

        JLabel text = new JLabel("Enter amount to withdraw:");
        text.setFont(new Font("System", Font.BOLD, 20));
        text.setBounds(120, 60, 320, 30);
        add(text);

        amountField = new JTextField();
        amountField.setBounds(120, 110, 300, 30);
        add(amountField);

        withdrawBtn = new JButton("WITHDRAW");
        backBtn = new JButton("BACK");
        withdrawBtn.setBounds(120, 170, 140, 35);
        backBtn.setBounds(280, 170, 140, 35);
        withdrawBtn.addActionListener(this);
        backBtn.addActionListener(this);
        add(withdrawBtn); add(backBtn);

        setSize(560, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private double getBalance(String pin) throws Exception {
        Conn c = new Conn();
        PreparedStatement ps = c.c.prepareStatement("SELECT type, amount FROM bank WHERE pin=?");
        ps.setString(1, pin);
        ResultSet rs = ps.executeQuery();
        double bal = 0;
        while (rs.next()) {
            String type = rs.getString("type");
            double amt = rs.getDouble("amount");
            bal += type.equalsIgnoreCase("Deposit") ? amt : -amt;
        }
        return bal;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
            return;
        }
        String amtStr = amountField.getText().trim();
        double amt;
        try { amt = Double.parseDouble(amtStr); }
        catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid amount"); return; }
        if (amt <= 0) { JOptionPane.showMessageDialog(this, "Amount must be positive"); return; }

        try {
            double bal = getBalance(pin);
            if (amt > bal) {
                JOptionPane.showMessageDialog(this, "Insufficient balance! Current balance: Rs. " + bal);
                return;
            }
            Conn c = new Conn();
            PreparedStatement ps = c.c.prepareStatement("INSERT INTO bank(pin, date, type, amount) VALUES(?,?,?,?)");
            ps.setString(1, pin);
            ps.setString(2, new Date().toString());
            ps.setString(3, "Withdrawal");
            ps.setDouble(4, amt);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Rs. " + amt + " Debited Successfully");
            setVisible(false);
            new Transactions(pin).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}
