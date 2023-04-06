package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ground {
    private Entity ground;
    private int groundBottom;
    private int groundEndLeft;
    private int groundEndRight;
    private boolean active;

    private String treeTrunkImage = "ground-removebg-preview.png";
    private Entity treeTrunk;

    public Ground(int groundEndLeft, int groundEndRight, int groundBottom, boolean active) {
        this.groundEndLeft = groundEndLeft;
        this.groundEndRight = groundEndRight;
        this.groundBottom = groundBottom;
        this.active = active;
    }

    public void setNewGround(int x, int y, int width, int height){
         this.ground = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(new Rectangle(width, height, Color.RED))
                .with(new CollidableComponent(true))
                .type(EntityTypes.GROUND)
                .scale(1, 1)
                .buildAndAttach();
         this.treeTrunk = FXGL.entityBuilder()
                 .at(x-13, y-5)
                 .view(treeTrunkImage)
                 .scale(0.2, 0.2)
                 .buildAndAttach();

        this.treeTrunk.setScaleY(0.2);
        if(this.ground.getWidth() == 700){
            this.treeTrunk.setScaleX(0.542);
        }else if(this.ground.getWidth() == 600){
            this.treeTrunk.setScaleX(0.46);
        }else if(this.ground.getWidth() == 500){
            this.treeTrunk.setScaleX(0.39);
        }else if(this.ground.getWidth() == 500){
            this.treeTrunk.setScaleX(0.4);
        }else if(this.ground.getWidth() == 250){
            this.treeTrunk.setScaleX(0.198);
        }
    }

    public int getGroundEndLeft() {
        return groundEndLeft;
    }

    public void setGroundEndLeft(int groundEndLeft) {
        this.groundEndLeft = groundEndLeft;
    }

    public int getGroundEndRight() {
        return groundEndRight;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getGroundBottom() {
        return groundBottom;
    }
}
