package Server.DAO;

import Server.db.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchDAO {

    public List<Map<String, Object>> getMatchHistory() {
        List<Map<String, Object>> historyList = new ArrayList<>();
        
        String query = "SELECT " +
                "md1.MatchId, " +
                "u1.username as player1, " +
                "u2.username as player2, " +
                "md1.total_score as score1, " +
                "md2.total_score as score2, " +
                "md1.play_time as time1, " +
                "md2.play_time as time2, " +
                "COALESCE(md1.created_at, md2.created_at) as match_date " +
                "FROM MatchDetails md1 " +
                "JOIN MatchDetails md2 ON md1.MatchId = md2.MatchId AND md1.UserId != md2.UserId " +
                "JOIN Users u1 ON md1.UserId = u1.id " +
                "JOIN Users u2 ON md2.UserId = u2.id " +
                "WHERE md1.UserId < md2.UserId " +
                "ORDER BY match_date DESC, md1.MatchId DESC " +
                "LIMIT 100";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> matchData = new HashMap<>();
                matchData.put("match_id", rs.getString("MatchId"));
                matchData.put("player1", rs.getString("player1"));
                matchData.put("player2", rs.getString("player2"));
                
                int score1 = rs.getInt("score1");
                int score2 = rs.getInt("score2");
                matchData.put("score", score1 + " - " + score2);
                
                matchData.put("time1", rs.getDouble("time1"));
                matchData.put("time2", rs.getDouble("time2"));
                
                // Try to get date, fallback to MatchId if column doesn't exist
                try {
                    Timestamp timestamp = rs.getTimestamp("match_date");
                    if (timestamp != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        matchData.put("date", sdf.format(timestamp));
                    } else {
                        matchData.put("date", "N/A");
                    }
                } catch (SQLException e) {
                    matchData.put("date", "N/A");
                }
                
                historyList.add(matchData);
            }
        } catch (SQLException e) {
            System.err.println("Get match history error: " + e.getMessage());
            e.printStackTrace();
        }

        return historyList;
    }
}
