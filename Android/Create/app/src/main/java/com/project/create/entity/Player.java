package com.project.create.entity;

public class Player {

    private int playerId;
    private String email, password;

    public Player() {

    }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Player(int playerId, String email, String password) {
        this.playerId = playerId;
        this.email = email;
        this.password = password;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
