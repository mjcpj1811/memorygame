package Server.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MatchDetail implements Serializable {
    private int totalScore;
    private double playTime;
    private User user;
    private Match match;
    private Timestamp createdAt;

    public MatchDetail() {};

    public MatchDetail(int totalScore, double playTime, User user, Match match) {
        this.totalScore = totalScore;
        this.playTime = playTime;
        this.user = user;
        this.match = match;
    }

    public MatchDetail(int totalScore, double playTime, User user, Match match, Timestamp createdAt) {
        this.totalScore = totalScore;
        this.playTime = playTime;
        this.user = user;
        this.match = match;
        this.createdAt = createdAt;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
