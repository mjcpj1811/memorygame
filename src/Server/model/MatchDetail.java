package Server.model;
import Server.model.User;
import Server.model.Match;

import java.io.Serializable;

public class MatchDetail implements Serializable {
    private int totalScore;
    private double playTime;
    private User user;
    private Match match;

    public MatchDetail() {};

    public MatchDetail(int totalScore, double playTime, User user, Match match) {
        this.totalScore = totalScore;
        this.playTime = playTime;
        this.user = user;
        this.match = match;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public double getPlayTime() {
        return playTime;
    }

    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
