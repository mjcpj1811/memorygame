package Server.DAO;

import Server.model.User;
import Server.db.DatabaseConnection;

import java.sql.*;
import java.util.UUID;

public class UserDAO {

        public boolean registerUser (User user) {
            String query = "INSERT INTO users (id, username, password, email, status, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
            try(Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setString(5, "OFFLINE");
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                int rows = ps.executeUpdate();

                return rows > 0;
            } catch (SQLException e){
            System.err.println("Register user error: " + e.getMessage());
            return false;}
        }

    public User checkUser (String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = null;
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt")
                );
            }
        } catch (SQLException e){
            System.err.println("Check user error: " + e.getMessage());
            return null;
        }
        if (user != null) {
            updateUserStatus(username);
        }
        return user;
    }


    public void updateUserStatus(String username) {
        String sql = "UPDATE Users SET status='ONLINE' WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("updateUserStatus error: " + e.getMessage());
        }
    }
}
