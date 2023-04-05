package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUp {
    private Entity powerup;

    public void setNewPowerUp(int x, int y){
        this.powerup = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(new Circle(5, Color.GREEN))
                .with(new CollidableComponent(true))
                .type(EntityTypes.POWERUP)
                .scale(2, 2)
                .buildAndAttach();
    }
}
