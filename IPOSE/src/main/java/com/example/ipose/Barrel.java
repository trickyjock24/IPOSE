package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
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
        this.barrel.getBoundingBoxComponent().clearHitBoxes();
        HitBox hitBox = new HitBox(new Point2D(70, 70), BoundingShape.circle(150));
        this.barrel.getBoundingBoxComponent().addHitBox(hitBox);
    }

    public void barrelRoll(double playerX, boolean end){
        if(end){
            System.out.println(this.barrel.getX());
            if(this.barrel.getX() > 520){
                this.barrel.translateY(5);
            }else{
                this.barrel.translateX(5);
                this.barrel.rotateBy(5);
            }
        }else{
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
}
