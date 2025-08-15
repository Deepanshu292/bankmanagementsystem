package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {
    JButton b100, b500, b1000, b2000, b5000, b10000, back;
    String pin;

    public FastCash(String pin) {
        this.pin = pin;
        setTitle("Fast Cash");
        setLayout(null);

        JLabel text = new JLabel("Select Withdrawal Amount");
        text.setFont(new Font("System", Font.BOLD, 20));
        text.setBounds(160, 40, 300, 30);
        add(text);

        b100 = new JButton("Rs 100");   b500 = new JButton("Rs 500");
        b1000 = new JButton("Rs 1000"); b2000 = new JButton("Rs 2000");
        b5000 = new JButton("Rs 5000"); b10000 = new JButton("Rs 10000");
        back = new JButton("Back");

        JButton[] arr = {b100, b500, b1000, b2000, b5000, b10000};
        int x1=80, x2=300, y=100;
        for (int i=0;i<arr.length;i++) {
            arr[i].setBounds(i%2==0?x1:x2, y, 180, 35);
            arr[i].addActionListener(this);
            add(arr[i]);
            if (i%2==1) y += 50;
        }
        back.setBounds(190, y+20, 180, 35); back.addActionListener(this); add(back);

        setSize(580, 380);
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
            bal += rs.getString("type").equalsIgnoreCase("Deposit") ? rs.getDouble("amount") : -rs.getDouble("amount");
        }
        return bal;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
            return;
        }
        String txt = ((JButton) ae.getSource()).getText().replace("Rs ", "");
        double amt = Double.parseDouble(txt);
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
