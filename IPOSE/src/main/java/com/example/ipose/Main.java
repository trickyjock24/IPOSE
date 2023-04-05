package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.run;
import java.util.ArrayList;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

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
    private TimerAction timerAction;
    private TimerAction timerAction2;
    private String userName;
    private int level;
    private boolean levelScreen = false;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("Donkey Kong");
        settings.setVersion("0.1");
        settings.setDeveloperMenuEnabled(true);
    }

    private void gameEnd(boolean reachedEndOfGame) {
        StringBuilder builder = new StringBuilder();
        if (reachedEndOfGame) {
            builder.append("You have reached the end of the game!\n\n");
            FileManager FM = new FileManager();
            FM.setMaxLevel(this.userName, this.level+1);
        }else{
            builder.append("Game Over!\n\n");
        }
        builder.append("Final score: ")
                .append(FXGL.geti("score"))
                .append("\nFinal level: ")
                .append(this.level);


        FXGL.getDialogService().showMessageBox(builder.toString(), () -> LevelScreen());
    }

    @Override
    protected void initGame(){

    }

    private void createBarrol2(){
        this.timerAction = getGameTimer().runAtInterval(() -> {
            createBarrol();
        }, Duration.seconds(1));
    }

    private void createBarrol(){
        FXGL.inc("score", +50);
        Barrel barrel1 = new Barrel();
        barrel1.setNewBarrel(-120, -11);
        this.barrels.add(barrel1);
        Barrel curentBarrol = null;
        for (int i = 0; i < this.barrels.size(); i++) {
            curentBarrol = this.barrels.get(i);
        }
        Barrel finalCurentBarrol = curentBarrol;
        this.timerAction2 = getGameTimer().runAtInterval(() -> {
            finalCurentBarrol.barrelRoll(this.player1.getPlayer().getX() -30);
        }, Duration.seconds(0.001));
    }

    private void playerToRight(){
        for(Ground ground: this.grounds) {
            if(ground.isActive()) {
                this.player1.playerToRight(ground.getGroundEndRight(), ground.getGroundBottom());
            }
        }
    }

    private void playerToLeft(){
        for (Ground ground: this.grounds) {
            if(ground.isActive()) {
                this.player1.playerToLeft(ground.getGroundEndLeft(), ground.getGroundBottom());
            }
        }
    }

    private void playerSpace(){
        for(Ground ground: this.grounds) {
            if(ground.isActive()) {
                this.player1.playerJump(ground.getGroundBottom());
            }
        }
    }

    private void playerUp(){
        Ground activeNextGround = null;
        int activeNumber = 0;
        for(int i = 0; i < this.grounds.size(); i++){
            if(grounds.get(i).isActive() && this.grounds.size() > i + 1){
                activeNextGround = grounds.get(i + 1);
                activeNumber = i;
            }
        }
        if(activeNextGround != null){
            if(player1.getPlayer().getY() == activeNextGround.getGroundBottom()){
                for(Ground ground: this.grounds) {
                    ground.setActive(false);
                }
                playerLadderTouch = false;
                if(this.grounds.size() > activeNumber + 1){
                    activeNextGround.setActive(true);
                    player1.changeView(player1.getVincent1VoorkantImage(), "Voorkant");
                }
            }else{
                if(this.playerLadderTouch){
                    this.player1.playerClimbLadderUp();
                }
            }
        }
    }

    private void playerDown(){
        for(Ground ground: this.grounds) {
            if(ground.isActive()) {
                if(player1.getPlayer().getY() <= ground.getGroundBottom()){
                    player1.changeView(player1.getVincent1VoorkantImage(), "Voorkant");
                }else{
                    if(this.playerLadderTouch){
                        this.player1.playerClimbLadderDown();
                    }
                }
            }
        }
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, ()->{
            playerToRight();
        });

        FXGL.onKey(KeyCode.RIGHT, ()->{
            playerToRight();
        });

        FXGL.onKey(KeyCode.A, ()->{
            playerToLeft();
        });

        FXGL.onKey(KeyCode.LEFT, ()->{
            playerToLeft();
        });

        FXGL.onKeyDown(KeyCode.SPACE, ()->{
            playerSpace();
        });

        FXGL.onKey(KeyCode.W, ()->{
            playerUp();
        });

        FXGL.onKey(KeyCode.UP, ()->{
            playerUp();
        });

        FXGL.onKey(KeyCode.S, ()->{
            playerDown();
        });

        FXGL.onKey(KeyCode.DOWN, ()->{
            playerDown();
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
            protected void onCollision(Entity player, Entity barrol) {
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
                playerLadderTouch = true;
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity ladder) {
                playerLadderTouch = false;
            }
        });
    }

    protected void initGameUI(){
        Label userLabel = new Label("User: " + this.userName);
        userLabel.setTranslateX(50);
        userLabel.setTranslateY(15);
        userLabel.setStyle("-fx-text-fill: gray");
        FXGL.getGameScene().addUINode(userLabel);

        Label levelLabel = new Label("Level: " + this.level);
        levelLabel.setTranslateX(50);
        levelLabel.setTranslateY(30);
        levelLabel.setStyle("-fx-text-fill: gray");
        FXGL.getGameScene().addUINode(levelLabel);

        Label scoreLabel = new Label("Score: ");
        scoreLabel.setTranslateX(50);
        scoreLabel.setTranslateY(45);
        scoreLabel.setStyle("-fx-text-fill: gray");
        FXGL.getGameScene().addUINode(scoreLabel);

        Label scoreNumber = new Label("0");
        scoreNumber.setTranslateX(85);
        scoreNumber.setTranslateY(45);
        scoreNumber.setStyle("-fx-text-fill: gray");
        scoreNumber.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString());
        FXGL.getGameScene().addUINode(scoreNumber);

        FXGL.getGameScene().setBackgroundColor(Color.rgb(26,26,26));
    }

    private void setLadderForGameLadders(Ladder ladder, int ladderX, int ladderY) {
        ladder.setNewLadder(ladderX, ladderY);
        this.ladders.add(ladder);
    }

    protected void initialiseGame1() {
        initGameUI();
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

        this.setLadderForGameLadders(new Ladder(), 400, 128);

        PowerUp powerUp1 = new PowerUp();
        powerUp1.setNewPowerUp(-10, 120);
        this.powerUps.add(powerUp1);

        PowerUp powerUp2 = new PowerUp();
        powerUp2.setNewPowerUp(550, 510);
        this.powerUps.add(powerUp2);

        this.princes1.setNewPrinces(350, -250);
        this.donkeyKong1.setNewDonkeyKong(-150, -248);

        this.player1.setNewPlayer(-100, 472);
        getGameTimer().runOnceAfter(() -> {
            createBarrol2();
        }, Duration.seconds(0.2));
    }

    protected void initialiseGame2() {
        initGameUI();
    }

    protected void initialiseGame3() {
        initGameUI();
    }

    protected void initialiseExit() {
        FXGL.set("score", 0);
        FXGL.getGameScene().clearGameViews();
        FXGL.getPhysicsWorld().clear();
        this.barrels = new ArrayList<>();
        if(this.player1.getPlayer() != null){
            this.player1.getPlayer().setX(-100);
            this.player1.getPlayer().setY(472);
            this.player1.setPlayerPowerup(false);
            this.player1.changeView(this.player1.getVincent1VoorkantImage(), "Voorkant");
        }
        this.princes1 = new Princes();
        this.donkeyKong1 = new DonkeyKong();
        this.playerLadderTouch = false;
        this.grounds = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        if(this.timerAction != null){
            this.timerAction.expire();
            this.timerAction = null;
            System.out.println("ll");
        }

        if(this.timerAction2 != null){
            this.timerAction2.expire();
            this.timerAction2 = null;
        }
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
            LevelScreen();
        });

        FXGL.getGameScene().addUINode(username);
        FXGL.getGameScene().addUINode(button);
        FXGL.getGameScene().addUINode(TF);
        FXGL.getGameScene().setBackgroundColor(Color.rgb(26,26,26));
    }

    protected void LevelScreen(){
        this.initialiseExit();

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
            this.initialiseGame1();
        });

        b2.setOnAction(actionEvent1 -> {
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);

            this.level = 2;
            this.initialiseGame2();
        });

        b3.setOnAction(actionEvent1 -> {
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);

            this.level = 3;
            this.initialiseGame3();
        });

        if (maxLevel == 1) {
            b3.setVisible(false);
            b2.setVisible(false);
        }

        if (maxLevel == 2) {
            b3.setVisible(false);
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("score", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}