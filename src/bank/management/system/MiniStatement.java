package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MiniStatement extends JFrame {
    JTextArea area;

    public MiniStatement(String pin) {
        setTitle("Mini Statement");
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-12s %-12s %-10s%n", "DATE", "TYPE", "AMOUNT"));
        sb.append("---------------------------------------\n");

        try {
            Conn c = new Conn();
            PreparedStatement ps = c.c.prepareStatement("SELECT date, type, amount FROM bank WHERE pin=? ORDER BY id DESC LIMIT 10");
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append(String.format("%-12.12s %-12s %-10.2f%n",
                        rs.getString("date"), rs.getString("type"), rs.getDouble("amount")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("\nError loading statement: ").append(e.getMessage());
        }

        area.setText(sb.toString());
        add(new JScrollPane(area), BorderLayout.CENTER);

        setSize(420, 320);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
