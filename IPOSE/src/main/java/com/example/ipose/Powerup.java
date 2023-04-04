package com.example.ipose;

import com.almasb.fxgl.entity.Entity;

abstract class Powerup {
    private boolean isEquiped; // Ik vind dat dit + alle references er naar weg mag.
    private int secondsRemaining;
    private Player player;

    public Powerup(int secondsRemaining, Player player) {
        this.isEquiped = false;
        this.secondsRemaining = secondsRemaining;
        this.player = player;
    }

    abstract void startPowerup();
    abstract void endPowerup();

    public boolean isEquiped() {
        return isEquiped;
    }

    public void setEquiped(boolean equiped) {
        isEquiped = equiped;
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(int secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
