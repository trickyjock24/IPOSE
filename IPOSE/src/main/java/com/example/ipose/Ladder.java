package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;

public class Ladder {
    private Entity ladder;
    private String LadderImage = "ladder.png";
    private boolean lastLadder = false;

    public void setNewLadder(int x, int y, boolean last){
        this.ladder = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(this.LadderImage)
                .with(new CollidableComponent(true))
                .type(EntityTypes.LADDER)
                .scale(2, 2)
                .buildAndAttach();
        if(last){
            this.lastLadder = true;
        }
    }

    public boolean isLastLadder() {
        return lastLadder;
    }

    public void setLastLadder(boolean lastLadder) {
        this.lastLadder = lastLadder;
    }

    public Entity getLadder() {
        return ladder;
    }

    public void setLadder(Entity ladder) {
        this.ladder = ladder;
    }
}
