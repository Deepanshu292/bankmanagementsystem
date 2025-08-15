package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Signup1 extends JFrame implements ActionListener {
    JTextField tName, tFname, tDob, tEmail, tAddress, tCity, tPincode, tState;
    JRadioButton rMale, rFemale, rMarried, rUnmarried, rOther;
    JButton next;
    String formno;

    public Signup1() {
        setTitle("New Account Application Form - Page 1");
        setLayout(null);

        formno = String.valueOf(1000 + new Random().nextInt(9000));

        JLabel heading = new JLabel("APPLICATION FORM NO. " + formno);
        heading.setFont(new Font("Raleway", Font.BOLD, 28));
        heading.setBounds(80, 20, 500, 30);
        add(heading);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(80, 80, 150, 25); add(l1);
        tName = new JTextField(); tName.setBounds(240, 80, 300, 25); add(tName);

        JLabel l2 = new JLabel("Father's Name:");
        l2.setBounds(80, 120, 150, 25); add(l2);
        tFname = new JTextField(); tFname.setBounds(240, 120, 300, 25); add(tFname);

        JLabel l3 = new JLabel("Date of Birth (dd-mm-yyyy):");
        l3.setBounds(80, 160, 200, 25); add(l3);
        tDob = new JTextField(); tDob.setBounds(280, 160, 260, 25); add(tDob);

        JLabel l4 = new JLabel("Gender:");
        l4.setBounds(80, 200, 150, 25); add(l4);
        rMale = new JRadioButton("Male");
        rFemale = new JRadioButton("Female");
        rMale.setBackground(Color.WHITE); rFemale.setBackground(Color.WHITE);
        ButtonGroup g1 = new ButtonGroup(); g1.add(rMale); g1.add(rFemale);
        rMale.setBounds(240, 200, 80, 25); rFemale.setBounds(340, 200, 100, 25);
        add(rMale); add(rFemale);

        JLabel l5 = new JLabel("Email:");
        l5.setBounds(80, 240, 150, 25); add(l5);
        tEmail = new JTextField(); tEmail.setBounds(240, 240, 300, 25); add(tEmail);

        JLabel l6 = new JLabel("Marital Status:");
        l6.setBounds(80, 280, 150, 25); add(l6);
        rMarried = new JRadioButton("Married");
        rUnmarried = new JRadioButton("Unmarried");
        rOther = new JRadioButton("Other");
        rMarried.setBackground(Color.WHITE);
        rUnmarried.setBackground(Color.WHITE);
        rOther.setBackground(Color.WHITE);
        ButtonGroup g2 = new ButtonGroup(); g2.add(rMarried); g2.add(rUnmarried); g2.add(rOther);
        rMarried.setBounds(240, 280, 100, 25);
        rUnmarried.setBounds(350, 280, 120, 25);
        rOther.setBounds(480, 280, 80, 25);
        add(rMarried); add(rUnmarried); add(rOther);

        JLabel l7 = new JLabel("Address:");
        l7.setBounds(80, 320, 150, 25); add(l7);
        tAddress = new JTextField(); tAddress.setBounds(240, 320, 300, 25); add(tAddress);

        JLabel l8 = new JLabel("City:");
        l8.setBounds(80, 360, 150, 25); add(l8);
        tCity = new JTextField(); tCity.setBounds(240, 360, 300, 25); add(tCity);

        JLabel l9 = new JLabel("Pincode:");
        l9.setBounds(80, 400, 150, 25); add(l9);
        tPincode = new JTextField(); tPincode.setBounds(240, 400, 300, 25); add(tPincode);

        JLabel l10 = new JLabel("State:");
        l10.setBounds(80, 440, 150, 25); add(l10);
        tState = new JTextField(); tState.setBounds(240, 440, 300, 25); add(tState);

        next = new JButton("Next");
        next.setBounds(440, 490, 100, 30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(Color.WHITE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = tName.getText().trim();
        String fname = tFname.getText().trim();
        String dob = tDob.getText().trim();
        String gender = rMale.isSelected() ? "Male" : (rFemale.isSelected() ? "Female" : "");
        String email = tEmail.getText().trim();
        String marital = rMarried.isSelected() ? "Married" : (rUnmarried.isSelected() ? "Unmarried" : (rOther.isSelected() ? "Other" : ""));
        String address = tAddress.getText().trim();
        String city = tCity.getText().trim();
        String pincode = tPincode.getText().trim();
        String state = tState.getText().trim();

        if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || gender.isEmpty() || email.isEmpty()
                || marital.isEmpty() || address.isEmpty() || city.isEmpty() || pincode.isEmpty() || state.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }
        try {
            Conn c = new Conn();
            String q = "INSERT INTO signup VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            java.sql.PreparedStatement ps = c.c.prepareStatement(q);
            ps.setString(1, formno);
            ps.setString(2, name);
            ps.setString(3, fname);
            ps.setString(4, dob);
            ps.setString(5, gender);
            ps.setString(6, email);
            ps.setString(7, marital);
            ps.setString(8, address);
            ps.setString(9, city);
            ps.setString(10, pincode);
            ps.setString(11, state);
            ps.executeUpdate();

            setVisible(false);
            new Signup2(formno).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
