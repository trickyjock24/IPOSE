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

    public Entity getGround() {
        return ground;
    }

    public void setGround(Entity ground) {
        this.ground = ground;
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

    public void setGroundEndRight(int groundEndRight) {
        this.groundEndRight = groundEndRight;
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

    public void setGroundBottom(int groundBottom) {
        this.groundBottom = groundBottom;
    }
}
