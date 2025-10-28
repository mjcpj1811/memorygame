package Server.DAO;

import Server.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchDAO {


    public List<Map<String, Object>> getAllMatchHistory() {
        List<Map<String, Object>> historyList = new ArrayList<>();

        String query = "SELECT " +
                "m.id as matchId, " +
                "u1.username as player1, " +
                "u2.username as player2, " +
                "md1.total_score as score1, " +
                "md2.total_score as score2, " +
                "md1.play_time as time1, " +
                "md2.play_time as time2, " +
                "md1.created_at as matchDate " +
                "FROM Matches m " +
                "JOIN MatchDetails md1 ON m.id = md1.MatchId " +
                "JOIN Users u1 ON md1.UserId = u1.id " +
                "JOIN MatchDetails md2 ON m.id = md2.MatchId AND md1.UserId != md2.UserId " +
                "JOIN Users u2 ON md2.UserId = u2.id " +
                "WHERE md1.UserId < md2.UserId " +
                "ORDER BY md1.created_at DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> match = new HashMap<>();
                match.put("matchId", rs.getString("matchId"));
                match.put("player1", rs.getString("player1"));
                match.put("player2", rs.getString("player2"));
                match.put("score1", rs.getInt("score1"));
                match.put("score2", rs.getInt("score2"));
                match.put("time1", rs.getDouble("time1"));
                match.put("time2", rs.getDouble("time2"));
                match.put("matchDate", rs.getTimestamp("matchDate"));

                historyList.add(match);
            }
        } catch (SQLException e) {
            System.err.println("Get match history error: " + e.getMessage());
            e.printStackTrace();
        }

        return historyList;
    }
}
