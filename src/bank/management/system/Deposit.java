package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;

public class Deposit extends JFrame implements ActionListener {
    JTextField amountField;
    JButton depositBtn, backBtn;
    String pin;

    public Deposit(String pin) {
        this.pin = pin;
        setTitle("Deposit");
        setLayout(null);

        JLabel text = new JLabel("Enter amount to deposit:");
        text.setFont(new Font("System", Font.BOLD, 20));
        text.setBounds(120, 60, 300, 30);
        add(text);

        amountField = new JTextField();
        amountField.setBounds(120, 110, 300, 30);
        add(amountField);

        depositBtn = new JButton("DEPOSIT");
        backBtn = new JButton("BACK");
        depositBtn.setBounds(120, 170, 140, 35);
        backBtn.setBounds(280, 170, 140, 35);
        depositBtn.addActionListener(this);
        backBtn.addActionListener(this);
        add(depositBtn); add(backBtn);

        setSize(560, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
            return;
        }
        String amtStr = amountField.getText().trim();
        if (amtStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter amount");
            return;
        }
        double amt;
        try { amt = Double.parseDouble(amtStr); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid amount"); return; }
        if (amt <= 0) { JOptionPane.showMessageDialog(this, "Amount must be positive"); return; }

        try {
            Conn c = new Conn();
            String q = "INSERT INTO bank(pin, date, type, amount) VALUES(?,?,?,?)";
            PreparedStatement ps = c.c.prepareStatement(q);
            ps.setString(1, pin);
            ps.setString(2, new Date().toString());
            ps.setString(3, "Deposit");
            ps.setDouble(4, amt);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Rs. " + amt + " Deposited Successfully");
            setVisible(false);
            new Transactions(pin).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}
