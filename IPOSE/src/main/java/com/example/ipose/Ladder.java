package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;

public class Ladder {
    private Entity ladder;
    private String LadderImage = "ladder.png";

    public void setNewLadder(int x, int y){
        this.ladder = FXGL.entityBuilder()
                .at(x, y)
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
}
