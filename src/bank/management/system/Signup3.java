package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.sql.*;

public class Signup3 extends JFrame implements ActionListener {
    JRadioButton rSavings, rCurrent, rFD, rRD;
    JCheckBox cATM, cInternet, cMobile, cEmail, cCheque, cStatement;
    JButton submit, cancel;
    String formno;

    public Signup3(String formno) {
        this.formno = formno;
        setTitle("New Account Application Form - Page 3");
        setLayout(null);

        JLabel l1 = new JLabel("Account Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 26));
        l1.setBounds(80, 20, 300, 30); add(l1);

        JLabel type = new JLabel("Account Type:");
        type.setBounds(80, 80, 150, 25); add(type);

        rSavings = new JRadioButton("Savings Account");
        rCurrent = new JRadioButton("Current Account");
        rFD = new JRadioButton("Fixed Deposit Account");
        rRD = new JRadioButton("Recurring Deposit Account");
        ButtonGroup bg = new ButtonGroup();
        for (JRadioButton rb : new JRadioButton[]{rSavings, rCurrent, rFD, rRD}) {
            rb.setBackground(Color.WHITE);
            bg.add(rb);
        }
        rSavings.setBounds(240, 80, 200, 25);
        rCurrent.setBounds(450, 80, 200, 25);
        rFD.setBounds(240, 110, 200, 25);
        rRD.setBounds(450, 110, 220, 25);
        add(rSavings); add(rCurrent); add(rFD); add(rRD);

        JLabel l2 = new JLabel("Services Required:");
        l2.setBounds(80, 160, 200, 25); add(l2);

        cATM = new JCheckBox("ATM Card");
        cInternet = new JCheckBox("Internet Banking");
        cMobile = new JCheckBox("Mobile Banking");
        cEmail = new JCheckBox("Email Alerts");
        cCheque = new JCheckBox("Cheque Book");
        cStatement = new JCheckBox("E-Statement");
        JCheckBox[] all = {cATM, cInternet, cMobile, cEmail, cCheque, cStatement};
        int y = 190;
        for (JCheckBox cb : all) {
            cb.setBackground(Color.WHITE);
            cb.setBounds(240, y, 200, 25);
            add(cb);
            y += 30;
        }

        submit = new JButton("Submit");
        submit.setBounds(240, 360, 120, 30);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(400, 360, 120, 30);
        cancel.addActionListener(this);
        add(cancel);

        getContentPane().setBackground(Color.WHITE);
        setSize(750, 470);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            setVisible(false);
            new Login().setVisible(true);
            return;
        }

        String accountType =
                rSavings.isSelected() ? "Savings" :
                rCurrent.isSelected() ? "Current" :
                rFD.isSelected() ? "FD" :
                rRD.isSelected() ? "RD" : "";

        if (accountType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select account type");
            return;
        }

        String facility = "";
        if (cATM.isSelected()) facility += " ATM";
        if (cInternet.isSelected()) facility += " Internet";
        if (cMobile.isSelected()) facility += " Mobile";
        if (cEmail.isSelected()) facility += " Email";
        if (cCheque.isSelected()) facility += " Cheque";
        if (cStatement.isSelected()) facility += " E-Statement";
        facility = facility.trim();

        // generate card & pin
        Random r = new Random();
        String card = "504093" + (100000000L + (Math.abs(r.nextLong()) % 900000000L));
        String pin = String.valueOf(1000 + r.nextInt(9000));

        try {
            Conn c = new Conn();

            PreparedStatement ps3 = c.c.prepareStatement(
                    "INSERT INTO signup3(formno, accountType, cardnumber, pin, facility) VALUES(?,?,?,?,?)");
            ps3.setString(1, formno);
            ps3.setString(2, accountType);
            ps3.setString(3, card);
            ps3.setString(4, pin);
            ps3.setString(5, facility);
            ps3.executeUpdate();

            PreparedStatement psLogin = c.c.prepareStatement("INSERT INTO login(cardnumber, pin) VALUES(?,?)");
            psLogin.setString(1, card);
            psLogin.setString(2, pin);
            psLogin.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Card Number: " + card + "\nPIN: " + pin + "\n\nSave these credentials!");
            setVisible(false);
            new Login().setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
