package com.example.ipose;
public class Score {
    private String username;
    private int score;
    private String level;
    public Score(String username, String level, int score) {
        this.username = username;
        this.score = score;
        this.level = level;
    }
    public int getScore() {return this.score;}
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
}