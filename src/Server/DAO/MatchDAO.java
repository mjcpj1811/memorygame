package Server.DAO;

import Server.model.Match;
import Server.model.MatchDetail;
import Server.model.User;
import Server.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    
    // Lấy danh sách tổng hợp: 1 hàng/1 trận (2 người chơi)
    public List<Object[]> getMatchOverviews() {
        List<Object[]> overviews = new ArrayList<>();
        String query =
                "SELECT m.id AS match_id, " +
                "u1.username AS player1, md1.total_score AS score1, " +
                "u2.username AS player2, md2.total_score AS score2, " +
                "md1.play_time AS time1, md2.play_time AS time2, " +
                "GREATEST(md1.created_at, md2.created_at) AS created_at, " +
                "CASE " +
                "  WHEN md1.total_score > md2.total_score THEN u1.username " +
                "  WHEN md1.total_score < md2.total_score THEN u2.username " +
                "  WHEN md1.play_time < md2.play_time THEN u1.username " +
                "  ELSE u2.username " +
                "END AS winner " +
                "FROM Matches m " +
                "JOIN MatchDetails md1 ON md1.MatchId = m.id " +
                "JOIN MatchDetails md2 ON md2.MatchId = m.id AND md1.UserId <> md2.UserId " +
                "JOIN Users u1 ON u1.id = md1.UserId " +
                "JOIN Users u2 ON u2.id = md2.UserId " +
                "WHERE md1.UserId < md2.UserId " +
                "ORDER BY created_at DESC";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.err.println("❌ Cannot connect to database!");
                return overviews;
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Object[] row = new Object[] {
                        rs.getString("match_id"),
                        rs.getString("player1"),
                        rs.getInt("score1"),
                        rs.getString("player2"),
                        rs.getInt("score2"),
                        rs.getDouble("time1"),
                        rs.getDouble("time2"),
                        rs.getTimestamp("created_at"),
                        rs.getString("winner")
                    };
                    overviews.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Get match overviews error: " + e.getMessage());
        }
        return overviews;
    }
}