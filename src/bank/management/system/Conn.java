package bank.management.system;
import java.sql.*;

public class Conn {
    public Connection c;
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bankmanagementsystem",
                "root",
                "deepu123"
            );

            if (c != null) {
                System.out.println("✅ Database connected successfully.");
            } else {
                throw new SQLException("❌ Connection object is null!");
            }

            s = c.createStatement();

        } catch (Exception e) {
            System.out.println("❌ Database connection failed:");
            e.printStackTrace();
            throw new RuntimeException("Stopping app due to DB connection failure.");
        }
    }
}
