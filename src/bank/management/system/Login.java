package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JTextField cardTextField;
    JPasswordField pinTextField;
    JButton login, clear, signup;

    public Login() {
        setTitle("Bank Management System");
        setLayout(null);

        // Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("bank/management/system/icons/logo.jpg"));
        JLabel logo = new JLabel(i1);
        logo.setBounds(40, 20, 100, 100);
        add(logo);

        JLabel text = new JLabel("Welcome to ATM");
        text.setFont(new Font("Osward", Font.BOLD, 32));
        text.setBounds(180, 45, 400, 40);
        add(text);

        JLabel cardno = new JLabel("Card No:");
        cardno.setFont(new Font("Raleway", Font.BOLD, 22));
        cardno.setBounds(120, 150, 150, 30);
        add(cardno);

        cardTextField = new JTextField();
        cardTextField.setBounds(260, 150, 240, 30);
        add(cardTextField);

        JLabel pin = new JLabel("PIN:");
        pin.setFont(new Font("Raleway", Font.BOLD, 22));
        pin.setBounds(120, 200, 150, 30);
        add(pin);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(260, 200, 240, 30);
        add(pinTextField);

        login = new JButton("SIGN IN");
        login.setBounds(260, 260, 100, 30);
        login.addActionListener(this);
        add(login);

        clear = new JButton("CLEAR");
        clear.setBounds(400, 260, 100, 30);
        clear.addActionListener(this);
        add(clear);

        signup = new JButton("SIGN UP");
        signup.setBounds(260, 310, 240, 30);
        signup.addActionListener(this);
        add(signup);

        getContentPane().setBackground(Color.WHITE);
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            cardTextField.setText("");
            pinTextField.setText("");
            return;
        }
        if (ae.getSource() == signup) {
            setVisible(false);
            new Signup1().setVisible(true);
            return;
        }
        if (ae.getSource() == login) {
            String card = cardTextField.getText().trim();
            String pin = new String(pinTextField.getPassword()).trim();
            if (card.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Card No and PIN");
                return;
            }
            try {
                Conn conn = new Conn();
                PreparedStatement ps = conn.c.prepareStatement("SELECT * FROM login WHERE cardnumber=? AND pin=?");
                ps.setString(1, card);
                ps.setString(2, pin);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect Card or PIN");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
