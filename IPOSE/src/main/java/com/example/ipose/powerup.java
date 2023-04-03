package com.example.ipose;

public class powerup {
    private boolean isEquiped;
    private int secondsRemaining;

    public powerup(boolean isEquiped, int secondsRemaining) {
        this.isEquiped = isEquiped;
        this.secondsRemaining = secondsRemaining;
    }

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
}
