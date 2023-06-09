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

    private String grounImage = "ground-removebg-preview.png";

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
