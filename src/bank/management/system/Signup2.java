package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Signup2 extends JFrame implements ActionListener {
    JComboBox<String> cReligion, cCategory, cIncome, cEducation, cOccupation;
    JTextField tPan, tAadhar;
    JRadioButton rSCYes, rSCNo, rEAYes, rEANo;
    JButton next;
    String formno;

    public Signup2(String formno) {
        this.formno = formno;
        setTitle("New Account Application Form - Page 2");
        setLayout(null);

        JLabel details = new JLabel("Additional Details");
        details.setFont(new Font("Raleway", Font.BOLD, 26));
        details.setBounds(80, 20, 400, 30); add(details);

        JLabel l1 = new JLabel("Religion:"); l1.setBounds(80, 80, 150, 25); add(l1);
        cReligion = new JComboBox<>(new String[]{"Hindu", "Muslim", "Sikh", "Christian", "Other"});
        cReligion.setBounds(240, 80, 300, 25); add(cReligion);

        JLabel l2 = new JLabel("Category:"); l2.setBounds(80, 120, 150, 25); add(l2);
        cCategory = new JComboBox<>(new String[]{"General", "OBC", "SC", "ST", "Other"});
        cCategory.setBounds(240, 120, 300, 25); add(cCategory);

        JLabel l3 = new JLabel("Income:"); l3.setBounds(80, 160, 150, 25); add(l3);
        cIncome = new JComboBox<>(new String[]{"<1,50,000", "1,50,000-2,50,000", "2,50,000-5,00,000", ">5,00,000"});
        cIncome.setBounds(240, 160, 300, 25); add(cIncome);

        JLabel l4 = new JLabel("Education:"); l4.setBounds(80, 200, 150, 25); add(l4);
        cEducation = new JComboBox<>(new String[]{"Non-Graduate", "Graduate", "Post-Graduate", "Doctorate", "Other"});
        cEducation.setBounds(240, 200, 300, 25); add(cEducation);

        JLabel l5 = new JLabel("Occupation:"); l5.setBounds(80, 240, 150, 25); add(l5);
        cOccupation = new JComboBox<>(new String[]{"Salaried", "Self-Employed", "Business", "Student", "Retired", "Other"});
        cOccupation.setBounds(240, 240, 300, 25); add(cOccupation);

        JLabel l6 = new JLabel("PAN No:"); l6.setBounds(80, 280, 150, 25); add(l6);
        tPan = new JTextField(); tPan.setBounds(240, 280, 300, 25); add(tPan);

        JLabel l7 = new JLabel("Aadhar No:"); l7.setBounds(80, 320, 150, 25); add(l7);
        tAadhar = new JTextField(); tAadhar.setBounds(240, 320, 300, 25); add(tAadhar);

        JLabel l8 = new JLabel("Senior Citizen:"); l8.setBounds(80, 360, 150, 25); add(l8);
        rSCYes = new JRadioButton("Yes"); rSCNo = new JRadioButton("No");
        rSCYes.setBackground(Color.WHITE); rSCNo.setBackground(Color.WHITE);
        ButtonGroup g1 = new ButtonGroup(); g1.add(rSCYes); g1.add(rSCNo);
        rSCYes.setBounds(240, 360, 70, 25); rSCNo.setBounds(320, 360, 70, 25);
        add(rSCYes); add(rSCNo);

        JLabel l9 = new JLabel("Existing Account:"); l9.setBounds(80, 400, 150, 25); add(l9);
        rEAYes = new JRadioButton("Yes"); rEANo = new JRadioButton("No");
        rEAYes.setBackground(Color.WHITE); rEANo.setBackground(Color.WHITE);
        ButtonGroup g2 = new ButtonGroup(); g2.add(rEAYes); g2.add(rEANo);
        rEAYes.setBounds(240, 400, 70, 25); rEANo.setBounds(320, 400, 70, 25);
        add(rEAYes); add(rEANo);

        next = new JButton("Next");
        next.setBounds(440, 450, 100, 30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(Color.WHITE);
        setSize(700, 560);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String religion = (String) cReligion.getSelectedItem();
        String category = (String) cCategory.getSelectedItem();
        String income = (String) cIncome.getSelectedItem();
        String education = (String) cEducation.getSelectedItem();
        String occupation = (String) cOccupation.getSelectedItem();
        String pan = tPan.getText().trim();
        String aadhar = tAadhar.getText().trim();
        String senior = rSCYes.isSelected() ? "Yes" : (rSCNo.isSelected() ? "No" : "");
        String existing = rEAYes.isSelected() ? "Yes" : (rEANo.isSelected() ? "No" : "");

        if (pan.isEmpty() || aadhar.isEmpty() || senior.isEmpty() || existing.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try {
            Conn c = new Conn();
            String q = "INSERT INTO signup2 VALUES(?,?,?,?,?,?,?,?,?,?)";
            java.sql.PreparedStatement ps = c.c.prepareStatement(q);
            ps.setString(1, formno);
            ps.setString(2, religion);
            ps.setString(3, category);
            ps.setString(4, income);
            ps.setString(5, education);
            ps.setString(6, occupation);
            ps.setString(7, pan);
            ps.setString(8, aadhar);
            ps.setString(9, senior);
            ps.setString(10, existing);
            ps.executeUpdate();

            setVisible(false);
            new Signup3(formno).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
