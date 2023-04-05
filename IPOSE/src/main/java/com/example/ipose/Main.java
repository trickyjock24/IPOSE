package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class Main extends GameApplication {
    private Player player1 = new Player();
    private Princes princes1 = new Princes();
    private DonkeyKong donkeyKong1 = new DonkeyKong();
    private boolean playerLadderTouch = false;
    private ArrayList<Ground> grounds = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Ladder> ladders = new ArrayList<>();

    private String userName;

    private int level;


    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setMainMenuEnabled(true);
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("Donkey Kong");
        settings.setVersion("0.1");
    }

    private void gameEnd(boolean reachedEndOfGame) {
        StringBuilder builder = new StringBuilder();
        builder.append("Game Over!\n\n");
        if (reachedEndOfGame) {
            builder.append("You have reached the end of the game!\n\n");
            FileManager FM = new FileManager();
            FM.setMaxLevel(this.userName, this.level+1);
        }

        builder.append("Final score: ")
                .append(FXGL.geti("score"))
                .append("\nFinal level: ")
                .append(FXGL.geti("level"));
        FXGL.getDialogService().showMessageBox(builder.toString(), () -> FXGL.getGameController().gotoMainMenu());
    }

    @Override
    protected void initGame(){

    }

    protected void initialiseGame() {
        Label levelLabel = new Label("Level: " + this.level);
        Label userLabel = new Label("User: " + this.userName);

        userLabel.setTranslateX(50);
        userLabel.setTranslateY(70);
        userLabel.setStyle("-fx-text-fill: white");

        levelLabel.setTranslateX(50);
        levelLabel.setTranslateY(20);
        levelLabel.setStyle("-fx-text-fill: white");
        FXGL.getGameScene().addUINode(levelLabel);
        FXGL.getGameScene().addUINode(userLabel);

        Label scoreLabel = new Label("Score: ");
        Label scoreNumber = new Label("0");
        scoreLabel.setTranslateX(50);
        scoreLabel.setTranslateY(50);
        scoreLabel.setStyle("-fx-text-fill: white");
        scoreNumber.setTranslateX(100);
        scoreNumber.setTranslateY(50);
        scoreNumber.setStyle("-fx-text-fill: white");
        scoreNumber.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString());
        FXGL.getGameScene().addUINode(scoreLabel);
        FXGL.getGameScene().addUINode(scoreNumber);

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

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.PRINCES) {
            @Override
            protected void onCollision(Entity player, Entity powerUp) {
                gameEnd(true);
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
        Label username = new Label("LOGIN:");
        username.setStyle("-fx-text-fill: gray");
        username.setTranslateX(300);
        username.setTranslateY(250);
        username.setFont(new Font(70)); // set font size to 30

        TextField TF = new TextField();
        TF.setStyle("-fx-text-fill: gray");
        TF.setTranslateX(250);
        TF.setTranslateY(450);
        TF.setPrefWidth(350);
        TF.setPrefHeight(20);

        Button button = new Button("LOGIN");
        button.setStyle("-fx-text-fill: gray");
        button.setTranslateX(300);
        button.setTranslateY(520);
        button.setPrefSize(200, 20);



        button.setOnAction(actionEvent -> {
            this.userName = TF.getText();
            System.out.println("Usesrname is: " + this.userName);

            TF.setVisible(false);
            button.setVisible(false);
            username.setVisible(false);

            FileManager FM = new FileManager();
            int maxLevel = FM.getMaxLevel(this.userName);


            Button b1 = new Button("LEVEL 1");
            b1.setStyle("-fx-text-fill: gray");
            b1.setTranslateX(160);
            b1.setTranslateY(230);
            b1.setPrefSize(450, 100);


            Button b2 = new Button("LEVEL 2");
            b2.setStyle("-fx-text-fill: gray");
            b2.setTranslateX(160);
            b2.setTranslateY(350);
            b2.setPrefSize(450, 100);


            Button b3 = new Button("LEVEL 3");
            b3.setStyle("-fx-text-fill: gray");
            b3.setTranslateX(160);
            b3.setTranslateY(480);
            b3.setPrefSize(450, 100);


            FXGL.getGameScene().addUINode(b1);
            FXGL.getGameScene().addUINode(b3);
            FXGL.getGameScene().addUINode(b2);

            b1.setOnAction(actionEvent1 -> {
                b1.setVisible(false);
                b2.setVisible(false);
                b3.setVisible(false);

                this.level = 1;
                this.initialiseGame();
            });

            b2.setOnAction(actionEvent1 -> {
                b1.setVisible(false);
                b2.setVisible(false);
                b3.setVisible(false);

                this.level = 2;
                this.initialiseGame();
            });

            b3.setOnAction(actionEvent1 -> {
                b1.setVisible(false);
                b2.setVisible(false);
                b3.setVisible(false);

                this.level = 3;
                this.initialiseGame();
            });


            if (maxLevel == 1) {
                b3.setVisible(false);
                b2.setVisible(false);
            }

            if (maxLevel == 2) {
                b3.setVisible(false);
            }

        });


        FXGL.getGameScene().addUINode(username);
        FXGL.getGameScene().addUINode(button);
        FXGL.getGameScene().addUINode(TF);



//
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("level", 0);
        vars.put("score", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}