package com.example.ipose;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;


public class Player extends Component  {
    private Entity player;
    private String playerKant = "Voorkant";
    private String vincent1VoorkantImage = "Vincent1 Voorkant.png";
    private String vincent1VoorkantFlipImage = "Vincent1 VoorkantFlip.png";
    private String vincent1AchterkantImage = "VincentAchterkant.png";

    private boolean playerPowerup = false;

    public void setNewPlayer(int x, int y){
        this.player = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(this.vincent1VoorkantImage)
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .scale(0.12, 0.12)
                .buildAndAttach();
    }

    public void playerToRight(int End, int Bottom){
        if(this.playerKant != "Voorkant"){
            changeView(this.vincent1VoorkantImage, "Voorkant");
        }
        if (this.player.getX() < End && this.player.getY() == Bottom) {
            this.player.translateX(5);
            System.out.println(this.player.getX());
        }
    }

    public void playerToLeft(int End, int Bottom){
        if(this.playerKant != "VoorkantFlip"){
            changeView(this.vincent1VoorkantFlipImage, "VoorkantFlip");
        }

        if(this.player.getX() > End && this.player.getY() == Bottom){
            this.player.translateX(-5);
            System.out.println(this.player.getX());
        }
    }

    public void playerJump(int Bottom){
        if(this.player.getY() == Bottom){
            this.player.translateY(-45);
            getGameTimer().runOnceAfter(() -> {
                this.player.translateY(45);
            }, Duration.seconds(0.25));
        }
    }

    public void playerClimbLadderUp(){
        if(this.playerKant != "Achterkant"){
            changeView(this.vincent1AchterkantImage, "Achterkant");
        }
        this.player.translateY(-5);
    }

    public void playerClimbLadderDown(){
        if(this.playerKant != "Achterkant"){
            changeView(this.vincent1AchterkantImage, "Achterkant");
        }
        this.player.translateY(5);
    }




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

    public boolean isPlayerPowerup() {
        return playerPowerup;
    }

    public void setPlayerPowerup(boolean playerPowerup) {
        this.playerPowerup = playerPowerup;
        this.player.getViewComponent().setOpacity(0.5);
    }
}
