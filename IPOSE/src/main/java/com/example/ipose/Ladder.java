package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;

public class Ladder {
    private Entity ladder;
    private String LadderImage = "ladder.png";
    private boolean active;
    private boolean climbable = false;

    public boolean isActive() {
        return active;
    }

    public boolean isClimbable() {
        return climbable;
    }

    public Ladder(boolean active) {
        this.active = active;
    }

    public void setNewLadder(int x, int y){
        this.ladder = FXGL.entityBuilder()
                .at(450, 648)
                .viewWithBBox(this.LadderImage)
                .with(new CollidableComponent(true))
                .type(EntityTypes.LADDER)
                .scale(2, 2)
                .buildAndAttach();
    }

    public Entity getLadder() {
        return ladder;
    }

    public void setLadder(Entity ladder) {
        this.ladder = ladder;
    }

    public String getLadderImage() {
        return LadderImage;
    }

    public void setLadderImage(String ladderImage) {
        LadderImage = ladderImage;
    }
}
