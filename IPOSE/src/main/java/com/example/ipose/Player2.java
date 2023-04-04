package com.example.ipose;

import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class Player2 {
    private Entity player;
    private String playerKant = "Voorkant";
    private String vincent1VoorkantImage = "Vincent1 Voorkant.png";
    private String vincent2VoorkantImage = "Vincent2 Voorkant.png";
    private String vincent3VoorkantImage = "Vincent3 Voorkant.png";
    private String vincent1AchterkantImage = "Vincent1 Achterkant.png";
    private String vincent2AchterkantImage = "Vincent2 Achterkant.png";
    private String vincent3AchterkantImage = "Vincent3 Achterkant.png";

    public void setNewPlayer(){
        this.player = FXGL.entityBuilder()
                .at(-100, 472)
                .viewWithBBox(this.vincent1VoorkantImage)
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .scale(0.12, 0.12)
                .buildAndAttach();
    }

    public void playerToRight(int End){
        if(this.playerKant != "Voorkant"){
            changeView(this.vincent1VoorkantImage, "Voorkant");
        }
//        if(this.player.getY() < 467){
//
//        }else {
            if (this.player.getX() < End) {
                this.player.translateX(5);
            }
//        }
    }

    public void playerToLeft(int End){
        if(this.playerKant != "Achterkant"){
            changeView(this.vincent1AchterkantImage, "Achterkant");
        }
//        if(this.player.getY() < 467){
//
//        }else{
            if(this.player.getX() > End){
                this.player.translateX(-5);
            }
//        }
    }

    public void playerJump(){
//        if(!this.Ladder1Cimb) {
            this.player.translateY(-45);
            getGameTimer().runOnceAfter(() -> {
                this.player.translateY(45);
            }, Duration.seconds(0.22));
//        }
    }

//    public void playerClimbLadderUp(){
//        if(this.Ladder1Cimb){
//            this.player1.translateY(-5);
//        }
////        System.out.println(this.Ladder1Cimb);
//    }
//
//    public void playerClimbLadderDown(){
//        if(this.Ladder1Cimb && this.player1.getY() < 472){
//            this.player1.translateY(5);
//        }
//    }

    public void changeView(String image, String kant){
        this.playerKant = kant;
        this.player.getViewComponent().clearChildren();
        this.player.getViewComponent().addChild(texture(image));
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public String getPlayerKant() {
        return playerKant;
    }

    public void setPlayerKant(String playerKant) {
        this.playerKant = playerKant;
    }


}
