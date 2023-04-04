package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.Map;
import com.almasb.fxgl.app.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class Main extends GameApplication {
    private Player player1 = new Player();
    private boolean playerLadderTouch = false;
    private ArrayList<Ground> grounds = new ArrayList<>();
    private ArrayList<Ladder> ladders = new ArrayList<>();


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("Donkey Kong");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame(){
        Ground ground1 = new Ground(-145, 540, 472, true);
        ground1.setNewGround(50, 750, 700, 20);
        this.grounds.add(ground1);

        Ground ground2 = new Ground(-145, 540, 342, false);
        ground2.setNewGround(50, 620, 700, 20);
        this.grounds.add(ground2);

        Ladder ladder1 = new Ladder(true);
        ladder1.setNewLadder(450, 648);
        this.ladders.add(ladder1);

        this.player1.setNewPlayer();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, ()->{
            for (Ground ground: this.grounds) {
                if (ground.isActive()) {
                    this.player1.playerToRight(ground.getGroundEndRight(), ground.getGroundBottom());
                }
            }
        });

        FXGL.onKey(KeyCode.RIGHT, ()->{
            for (Ground ground: this.grounds) {
                if (ground.isActive()) {
                    this.player1.playerToRight(ground.getGroundEndRight(), ground.getGroundBottom());
                }
            }
        });

        FXGL.onKey(KeyCode.A, ()->{
            for (Ground ground: this.grounds) {
                if(ground.isActive()) {
                    this.player1.playerToLeft(ground.getGroundEndLeft(), ground.getGroundBottom());
                }
            }
        });

        FXGL.onKey(KeyCode.LEFT, ()->{
            for(Ground ground: this.grounds) {
                if(ground.isActive()) {
                    this.player1.playerToLeft(ground.getGroundEndLeft(), ground.getGroundBottom());
                }
            }
        });

        FXGL.onKeyDown(KeyCode.SPACE, ()->{
            if(!this.playerLadderTouch) {
                this.player1.playerJump();
            }
        });

        FXGL.onKey(KeyCode.W, ()->{
            int nextGround = 0;
            for(Ground ground: this.grounds) {
                if(nextGround == 1){
                    System.out.println(ground.getGroundBottom());
                    if(player1.getPlayer().getY() == ground.getGroundBottom()){

                    }else{
                        if(this.playerLadderTouch){
                            this.player1.playerClimbLadderUp();
                        }
                    }
                }
                if(ground.isActive()) {
                    nextGround = 1;
                }
            }
        });

        FXGL.onKey(KeyCode.UP, ()->{
            int nextGround = 0;
            for(Ground ground: this.grounds) {
                if(nextGround == 1){
                    System.out.println(ground.getGroundBottom());
                    if(player1.getPlayer().getY() == ground.getGroundBottom()){
                        for(Ground ground2: this.grounds) {
                            ground2.setActive(false);
                        }
                        ground.setActive(true);
                    }else{
                        if(this.playerLadderTouch){
                            this.player1.playerClimbLadderUp();
                        }
                    }
                }
                if(ground.isActive()) {
                    nextGround = 1;
                }
            }
        });

        FXGL.onKey(KeyCode.S, ()->{
            for(Ground ground: this.grounds) {
                if(ground.isActive()) {
//                    if(player1.getPlayer().getY() <= ground.getGroundBottom()){
//
//                    }else{
//                        if(this.playerLadderTouch){
//                            this.player1.playerClimbLadderDown();
//                        }
//                    }
                    this.player1.playerClimbLadderDown();
                    System.out.println("ll");
                }
            }
        });

        FXGL.onKey(KeyCode.DOWN, ()->{
            for(Ground ground: this.grounds) {
                if(ground.isActive()) {
                    if(player1.getPlayer().getY() >= ground.getGroundBottom()){

                    }else{
                        if(this.playerLadderTouch){
                            this.player1.playerClimbLadderDown();
                        }
                    }
                }
            }
        });
    }

        @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.LADDER) {
//            @Override
//            protected void onCollision(Entity player, Entity ladder) {
//                FXGL.inc("ladderTouch", 1);
//                ladder1.removeFromWorld();
//                Ladder1Cimb = true;
//            }

            @Override
            protected void onCollisionBegin(Entity player, Entity ladder) {
                System.out.println(player);
                System.out.println(ladder);

                playerLadderTouch = true;
//
//                setPlayerLadderTouch(true);
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity ladder) {
                System.out.println(player);
                System.out.println(ladder);
//                setPlayerLadderTouch(false);
                playerLadderTouch = false;
            }
        });
    }


    @Override
    protected void initUI() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        // nothing to do here
    }

    public static void main(String[] args) {
        launch(args);
    }


}