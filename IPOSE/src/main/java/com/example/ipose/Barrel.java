package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Barrel {
    private Entity barrel;
    private boolean barrelOnPlayer = false;

    public void setNewBarrel(int x, int y){
        this.barrel = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(new Circle(10, Color.BROWN))
                .with(new CollidableComponent(true))
                .type(EntityTypes.BARREL)
                .scale(2, 2)
                .buildAndAttach();
    }

    public void barrelRoll(double playerX){
        if(this.barrelOnPlayer){
            this.barrel.translateY(5);
        }else{
            if(this.barrel.getX() < playerX){
                this.barrel.translateX(5);
            }else{
                this.barrelOnPlayer = true;
            }
        }
    }
}
