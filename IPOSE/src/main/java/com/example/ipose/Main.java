package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.ArrayList;


public class Main extends GameApplication {
    private Player player1 = new Player();
    private boolean playerLadderTouch = false;
    private ArrayList<Ground> grounds = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
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

        Ground ground2 = new Ground(-95, 380, 342, false);
        ground2.setNewGround(100, 620, 500, 20);
        this.grounds.add(ground2);

        Ground ground3 = new Ground(5, 540, 212, false);
        ground3.setNewGround(200, 490, 550, 20);
        this.grounds.add(ground3);

        Ground ground4 = new Ground(-95, 490, 82, false);
        ground4.setNewGround(100, 360, 600, 20);
        this.grounds.add(ground4);

        Ground ground5 = new Ground(-145, 540, -48, false);
        ground5.setNewGround(50, 230, 700, 20);
        this.grounds.add(ground5);


        Ladder ladder1 = new Ladder(true);
        ladder1.setNewLadder(500, 648);
        this.ladders.add(ladder1);

        Ladder ladder2 = new Ladder(true);
        ladder2.setNewLadder(250, 518);
        this.ladders.add(ladder2);

        Ladder ladder3 = new Ladder(true);
        ladder3.setNewLadder(450, 388);
        this.ladders.add(ladder3);

        Ladder ladder4 = new Ladder(true);
        ladder4.setNewLadder(650, 258);
        this.ladders.add(ladder4);

        Ladder ladder5 = new Ladder(true);
        ladder5.setNewLadder(300, 258);
        this.ladders.add(ladder5);

        PowerUp powerUp1 = new PowerUp();
        powerUp1.setNewPowerUp(150, 280);
        this.powerUps.add(powerUp1);

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
            for(Ground ground: this.grounds) {
                if(ground.isActive()) {
                    this.player1.playerJump(ground.getGroundBottom());
                }
            }
        });

        FXGL.onKey(KeyCode.W, ()->{
            for(int i = 0; i < this.grounds.size(); i++){
                if(grounds.get(i).isActive() && this.grounds.size() > i + 1){
                    if(player1.getPlayer().getY() == grounds.get(i + 1).getGroundBottom()){
                        for(Ground ground: this.grounds) {
                            ground.setActive(false);
                        }
                        playerLadderTouch = false;
                        if(this.grounds.size() > i + 1){
                            grounds.get(i + 1).setActive(true);
                        }
                    }else{
                        if(this.playerLadderTouch){
                            this.player1.playerClimbLadderUp();
                        }
                    }
                }
            }
        });

        FXGL.onKey(KeyCode.UP, ()->{
            for(int i = 0; i < this.grounds.size(); i++){
                if(grounds.get(i).isActive() && this.grounds.size() > i + 1){
                    if(player1.getPlayer().getY() == grounds.get(i + 1).getGroundBottom()){
                        for(Ground ground: this.grounds) {
                            ground.setActive(false);
                        }
                        playerLadderTouch = false;
                        if(this.grounds.size() > i + 1){
                            grounds.get(i + 1).setActive(true);
                        }
                    }else{
                        if(this.playerLadderTouch){
                            this.player1.playerClimbLadderUp();
                        }
                    }
                }
            }
        });

        FXGL.onKey(KeyCode.S, ()->{
            for(Ground ground: this.grounds) {
                if(ground.isActive()) {
                    if(player1.getPlayer().getY() <= ground.getGroundBottom()){

                    }else{
                        if(this.playerLadderTouch){
                            this.player1.playerClimbLadderDown();
                        }
                    }
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
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.POWERUP) {
            @Override
            protected void onCollision(Entity player, Entity powerUp) {
                powerUp.removeFromWorld();
                player1.setPlayerPowerup(true);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.LADDER) {
            @Override
            protected void onCollisionBegin(Entity player, Entity ladder) {
                System.out.println(player);
                System.out.println(ladder);

                playerLadderTouch = true;
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity ladder) {
                System.out.println(player);
                System.out.println(ladder);
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