package Server.db;
import java.sql.*;
public class DatabaseConnection {
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/MemoryGame";
    private static final String USER = "root";
    private static final String PASS = "123456";

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
