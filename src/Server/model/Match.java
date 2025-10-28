package Server.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Match implements Serializable {
    private String id;
    private String winner;
    private Timestamp createdAt;

    public Match(){};

    public Match(String id, String winner , Timestamp createdAt) {
        this.id = id;
        this.winner = winner;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
