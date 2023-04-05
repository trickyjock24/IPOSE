package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.run;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;



public class Main extends GameApplication {
    private Player player1 = new Player();
    private Princes princes1 = new Princes();
    private DonkeyKong donkeyKong1 = new DonkeyKong();
    private boolean playerLadderTouch = false;
    private ArrayList<Ground> grounds = new ArrayList<>();
    private ArrayList<Ladder> ladders = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Barrel> barrels = new ArrayList<>();


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("Donkey Kong");
        settings.setVersion("0.1");
    }

    private void gameEnd(boolean reachedEndOfGame) {
        StringBuilder builder = new StringBuilder();
        if (reachedEndOfGame) {
            builder.append("You have reached the end of the game!\n\n");
        }else{
            builder.append("Game Over!\n\n");
        }
        builder.append("Final score: ")
                .append(FXGL.geti("score"))
                .append("\nFinal level: ")
                .append(FXGL.geti("level"));
        FXGL.getDialogService().showMessageBox(builder.toString(), () -> FXGL.getGameController().gotoMainMenu());
    }

    @Override
    protected void initGame(){
        Ground ground1 = new Ground(-145, 540, 472, true);
        ground1.setNewGround(50, 750, 700, 20);
        this.grounds.add(ground1);

        Ground ground2 = new Ground(-95, 390, 342, false);
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

        Ground ground6 = new Ground(165, 400, -178, false);
        ground6.setNewGround(360, 100, 250, 20);
        this.grounds.add(ground6);

        Ladder ladder1 = new Ladder();
        ladder1.setNewLadder(500, 648);
        this.ladders.add(ladder1);

        Ladder ladder2 = new Ladder();
        ladder2.setNewLadder(250, 518);
        this.ladders.add(ladder2);

        Ladder ladder3 = new Ladder();
        ladder3.setNewLadder(450, 388);
        this.ladders.add(ladder3);

        Ladder ladder4 = new Ladder();
        ladder4.setNewLadder(200, 258);
        this.ladders.add(ladder4);

        Ladder ladder5 = new Ladder();
        ladder5.setNewLadder(650, 258);
        this.ladders.add(ladder5);

        Ladder ladder6 = new Ladder();
        ladder6.setNewLadder(400, 128);
        this.ladders.add(ladder6);

        PowerUp powerUp1 = new PowerUp();
        powerUp1.setNewPowerUp(150, 280);
        this.powerUps.add(powerUp1);

        PowerUp powerUp2 = new PowerUp();
        powerUp2.setNewPowerUp(700, 450);
        this.powerUps.add(powerUp2);

        this.princes1.setNewPrinces(350, -250);
        this.donkeyKong1.setNewDonkeyKong(-150, -248);

        this.player1.setNewPlayer(-100, 472);
        getGameTimer().runOnceAfter(() -> {
            createBarrol2();
        }, Duration.seconds(0.2));
    }

    private void createBarrol2(){
        double randomNum = ThreadLocalRandom.current().nextDouble(0.2, 1.5);
        run(() -> createBarrol(), Duration.seconds(1));
    }

    private void createBarrol(){
        FXGL.inc("score", +50);
        Barrel barrel1 = new Barrel();
        barrel1.setNewBarrel(100, 220);
        this.barrels.add(barrel1);
        Barrel curentBarrol = null;
        for(int i = 0; i < this.barrels.size(); i++){
            curentBarrol = this.barrels.get(i);
        }
        Barrel finalCurentBarrol = curentBarrol;
        run(() -> finalCurentBarrol.barrelRoll(this.player1.getPlayer().getX() +215), Duration.seconds(0.001));
    }


    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.Q, ()->{
            for(int i = 0; i < this.barrels.size(); i++){
                this.barrels.get(i).barrelRoll(this.player1.getPlayer().getX() +215);
            }
        });

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
            Ground activeNextGround = null;
            int activeNumber = 0;
            for(int i = 0; i < this.grounds.size(); i++){
                if(grounds.get(i).isActive() && this.grounds.size() > i + 1){
                    activeNextGround = grounds.get(i + 1);
                    activeNumber = i;
                }
            }

            if(player1.getPlayer().getY() == activeNextGround.getGroundBottom()){
                for(Ground ground: this.grounds) {
                    ground.setActive(false);
                }
                playerLadderTouch = false;
                System.out.println(this.grounds.size() > activeNumber + 1);
                System.out.println(this.grounds.size());
                System.out.println(activeNumber + 1);
                if(this.grounds.size() > activeNumber + 1){
                    activeNextGround.setActive(true);
                    player1.changeView(player1.getVincent1VoorkantImage(), "Achterkant");
                    System.out.println("lllll");
                }
            }else{
                if(this.playerLadderTouch){
                    this.player1.playerClimbLadderUp();
                }
            }
        });

        FXGL.onKey(KeyCode.UP, ()->{
            Ground activeNextGround = null;
            int activeNumber = 0;
            for(int i = 0; i < this.grounds.size(); i++){
                if(grounds.get(i).isActive() && this.grounds.size() > i + 1){
                    activeNextGround = grounds.get(i + 1);
                    activeNumber = i;
                }
            }

            if(player1.getPlayer().getY() == activeNextGround.getGroundBottom()){
                for(Ground ground: this.grounds) {
                    ground.setActive(false);
                }
                playerLadderTouch = false;
                System.out.println(this.grounds.size() > activeNumber + 1);
                System.out.println(this.grounds.size());
                System.out.println(activeNumber + 1);
                if(this.grounds.size() > activeNumber + 1){
                    activeNextGround.setActive(true);
                    player1.changeView(player1.getVincent1VoorkantImage(), "Achterkant");
                    System.out.println("lllll");
                }
            }else{
                if(this.playerLadderTouch){
                    this.player1.playerClimbLadderUp();
                }
            }
        });

        FXGL.onKey(KeyCode.S, ()->{
            for(Ground ground: this.grounds) {
                if(ground.isActive()) {
                    if(player1.getPlayer().getY() <= ground.getGroundBottom()){
                        player1.changeView(player1.getVincent1VoorkantImage(), "Achterkant");
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
                        player1.changeView(player1.getVincent1VoorkantImage(), "Achterkant");
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

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.PRINCES) {
            @Override
            protected void onCollision(Entity player, Entity powerUp) {
                gameEnd(true);
            }
        });

            FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BARREL) {
                @Override
                protected void onCollisionEnd(Entity player, Entity barrol) {
                    if(player1.isPlayerPowerup()){
                        player1.setPlayerPowerup(false);
                    }else{
                        gameEnd(false);
                    }
                }
            });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.LADDER) {
            @Override
            protected void onCollisionBegin(Entity player, Entity ladder) {
//                System.out.println(player);
//                System.out.println(ladder);
                playerLadderTouch = true;
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity ladder) {
//                System.out.println(player);
//                System.out.println(ladder);
                playerLadderTouch = false;
            }
        });
    }


    @Override
    protected void initUI() {
        Label levelLabel = new Label("Level: ");
        Label levelNumber = new Label("1");
        levelLabel.setTranslateX(50);
        levelLabel.setTranslateY(20);
        levelLabel.setStyle("-fx-text-fill: gray");
        levelNumber.setTranslateX(100);
        levelNumber.setTranslateY(20);
        levelNumber.setStyle("-fx-text-fill: gray");
        levelNumber.textProperty().bind(FXGL.getWorldProperties().intProperty("level").asString());
        FXGL.getGameScene().addUINode(levelLabel);
        FXGL.getGameScene().addUINode(levelNumber);

        Label scoreLabel = new Label("Score: ");
        Label scoreNumber = new Label("0");
        scoreLabel.setTranslateX(50);
        scoreLabel.setTranslateY(40);
        scoreLabel.setStyle("-fx-text-fill: gray");
        scoreNumber.setTranslateX(100);
        scoreNumber.setTranslateY(40);
        scoreNumber.setStyle("-fx-text-fill: gray");
        scoreNumber.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString());
        FXGL.getGameScene().addUINode(scoreLabel);
        FXGL.getGameScene().addUINode(scoreNumber);

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("level", 1);
        vars.put("score", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}