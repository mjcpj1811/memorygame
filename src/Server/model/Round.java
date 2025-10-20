package Server.model;
import Server.model.Match;
import java.io.Serializable;

public class Round implements Serializable {
    private String id;
    private int numberRound;
    private String sequenceShown;
    private String player1Id;
    private String player2Id;
    private int scorePlayer1;
    private int scorePlayer2;
    private double timeSpentPlayer1;
    private double timeSpentPlayer2;
    private Match match;

    public Round() {};

    public Round(String id, int numberRound, String sequenceShown, String player1Id, String player2Id, int scorePlayer1, int scorePlayer2, Match match) {
        this.id = id;
        this.numberRound = numberRound;
        this.sequenceShown = sequenceShown;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
        this.timeSpentPlayer1 = scorePlayer1;
        this.timeSpentPlayer2 = scorePlayer2;
        this.match = match;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberRound() {
        return numberRound;
    }

    public void setNumberRound(int numberRound) {
        this.numberRound = numberRound;
    }

    public String getSequenceShown() {
        return sequenceShown;
    }

    public void setSequenceShown(String sequenceShown) {
        this.sequenceShown = sequenceShown;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }


    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public double getTimeSpentPlayer1() {
        return timeSpentPlayer1;
    }

    public void setTimeSpentPlayer1(double timeSpentPlayer1) {
        this.timeSpentPlayer1 = timeSpentPlayer1;
    }

    public double getTimeSpentPlayer2() {
        return timeSpentPlayer2;
    }

    public void setTimeSpentPlayer2(double timeSpentPlayer2) {
        this.timeSpentPlayer2 = timeSpentPlayer2;
    }
}
