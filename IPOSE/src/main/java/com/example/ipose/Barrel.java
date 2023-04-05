package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Barrel {
    private Entity barrel;
    private boolean barrelOnPlayer = false;

    private String barrolImage = "barrol-removebg-preview.png";

    public void setNewBarrel(int x, int y){
        this.barrel = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(barrolImage)
                .with(new CollidableComponent(true))
                .type(EntityTypes.BARREL)
                .scale(0.1, 0.1)
                .buildAndAttach();
    }

    public void barrelRoll(double playerX){
        if(this.barrelOnPlayer){
            this.barrel.translateY(5);
        }else{
            if(this.barrel.getX() < playerX){
                this.barrel.translateX(5);
                this.barrel.rotateBy(5);
            }else{
                this.barrelOnPlayer = true;
            }
        }
    }
}
