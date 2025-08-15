package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Transactions extends JFrame implements ActionListener {
    JButton deposit, withdraw, fastcash, ministatement, pinchange, balanceenquiry, exit;
    String pin;

    public Transactions(String pin) {
        this.pin = pin;
        setTitle("Transactions");
        setLayout(null);

        JLabel text = new JLabel("Please select your Transaction");
        text.setFont(new Font("System", Font.BOLD, 20));
        text.setBounds(150, 40, 350, 30);
        add(text);

        deposit = new JButton("Deposit");
        withdraw = new JButton("Withdraw");
        fastcash = new JButton("Fast Cash");
        ministatement = new JButton("Mini Statement");
        pinchange = new JButton("Pin Change");
        balanceenquiry = new JButton("Balance Enquiry");
        exit = new JButton("Exit");

        deposit.setBounds(80, 100, 180, 35);
        withdraw.setBounds(300, 100, 180, 35);
        fastcash.setBounds(80, 150, 180, 35);
        ministatement.setBounds(300, 150, 180, 35);
        pinchange.setBounds(80, 200, 180, 35);
        balanceenquiry.setBounds(300, 200, 180, 35);
        exit.setBounds(190, 260, 180, 35);

        for (JButton b : new JButton[]{deposit, withdraw, fastcash, ministatement, pinchange, balanceenquiry, exit}) {
            b.addActionListener(this);
            add(b);
        }

        setSize(580, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == exit) System.exit(0);
        else if (src == deposit) { setVisible(false); new Deposit(pin).setVisible(true); }
        else if (src == withdraw) { setVisible(false); new Withdraw(pin).setVisible(true); }
        else if (src == fastcash) { setVisible(false); new FastCash(pin).setVisible(true); }
        else if (src == pinchange) { setVisible(false); new PinChange(pin).setVisible(true); }
        else if (src == balanceenquiry) { new BalanceEnquiry(pin).setVisible(true); }
        else if (src == ministatement) { new MiniStatement(pin).setVisible(true); }
    }
}
