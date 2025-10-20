package Server.model;

import java.io.Serializable;

public class Match implements Serializable {
    private String id;
    private String winner;

    public Match(){};

    public Match(String id, String winner) {
        this.id = id;
        this.winner = winner;
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
}
