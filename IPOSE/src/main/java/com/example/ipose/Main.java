package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.run;
import java.util.ArrayList;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.play;


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
    private boolean playerEnd = false;
    private boolean playerSecontToEnd = false;

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
            FileManager FM = new FileManager();
            FM.setMaxLevel(this.userName, this.level + 1);
            int score = FXGL.geti("score");
            FM.setNewScore(this.userName, this.level, String.valueOf(score));
            Score[] highestScore = FM.getHighScores(this.level);
            if(this.level == 3){
                builder.append("You have reached the end of the game!\n\nHighest score was: ")
                        .append(highestScore[0].getScore())
                        .append("pt")
                        .append("\n\n");
            }else{
                builder.append("You have reached the level!\n\nHighest score was: ")
                        .append(highestScore[0].getScore())
                        .append("pt")
                        .append("\n\n");
            }
            if (score > highestScore[0].getScore()) {
                builder.append("YOU BROKE THE HIGH SCORE!\n\n");
            }
            int index = 1;

            for (Score i : highestScore) {
                if (i != null) {
                    builder.append(index)
                            .append(". ")
                            .append(i.getUsername())
                            .append(": ").append(i.getScore())
                            .append("pt")
                            .append("\n\n");
                    index++;
                }
                else {
                    break;
                }

            }
        }else{
            builder.append("Game Over!\n\n");
        }
        builder.append("Final score: ").append(FXGL.geti("score"));
        FXGL.getDialogService().showMessageBox(builder.toString(), this::LevelScreen);
    }

    @Override
    protected void initGame(){

    }

    private void createBarrol2(double time){
        this.timerAction = getGameTimer().runAtInterval(this::createBarrol, Duration.seconds(time));
    }

    private void createBarrol(){
        if(!this.playerEnd){
            FXGL.inc("score", +50);
            Barrel barrel1 = new Barrel();
            barrel1.setNewBarrel(-160, -11);
            this.barrels.add(barrel1);
            Barrel curentBarrol = null;
            for (Barrel barrel : this.barrels) {
                curentBarrol = barrel;
            }
            Barrel finalCurentBarrol = curentBarrol;
            this.timerAction2 = getGameTimer().runAtInterval(() -> {
                finalCurentBarrol.barrelRoll(this.player1.getPlayer().getX() -30, this.playerSecontToEnd);
            }, Duration.seconds(0.001));
        }
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
                    if(activeNumber == 3){
                        this.playerSecontToEnd = true;
                    }
                }
            }else{
                if(this.playerLadderTouch){
                    this.player1.playerClimbLadderUp();
                }
            }
        }else{
            this.playerEnd = true;
        }
    }

    private void playerDown(){
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
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, this::playerToRight);

        FXGL.onKey(KeyCode.RIGHT, this::playerToRight);

        FXGL.onKey(KeyCode.A, this::playerToLeft);

        FXGL.onKey(KeyCode.LEFT, this::playerToLeft);

        FXGL.onKeyDown(KeyCode.SPACE, this::playerSpace);

        FXGL.onKey(KeyCode.W, this::playerUp);

        FXGL.onKey(KeyCode.UP, this::playerUp);

        FXGL.onKey(KeyCode.S, this::playerDown);

        FXGL.onKey(KeyCode.DOWN, this::playerDown);
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.POWERUP) {
            @Override
            protected void onCollision(Entity player, Entity powerUp) {
                player1.setPlayerPowerup(true);
                powerUp.removeFromWorld();

                FXGL.getAudioPlayer().pauseAllMusic();
                FXGL.getAudioPlayer().stopAllSounds();
                Sound gameSound = FXGL.getAssetLoader().loadSound("smb_powerup.wav");
                FXGL.getAudioPlayer().playSound(gameSound);

                getGameTimer().runOnceAfter(() -> {
                    FXGL.getAudioPlayer().resumeAllMusic();
                }, Duration.seconds(1));
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.PRINCES) {
            @Override
            protected void onCollision(Entity player, Entity princes) {
                FXGL.getAudioPlayer().stopAllMusic();
                FXGL.getAudioPlayer().stopAllSounds();
                Sound gameSound = FXGL.getAssetLoader().loadSound("smb_stage_clear.wav");
                FXGL.getAudioPlayer().playSound(gameSound);
                gameEnd(true);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BARREL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity barrol) {
                if(player1.isPlayerPowerup()){
                    player1.setPlayerPowerup(false);
                    FXGL.getAudioPlayer().pauseAllMusic();
                    FXGL.getAudioPlayer().stopAllSounds();
                    Sound gameSound = FXGL.getAssetLoader().loadSound("smb3_sound_effects_bump.wav");
                    FXGL.getAudioPlayer().playSound(gameSound);
                    getGameTimer().runOnceAfter(() -> {
                        FXGL.getAudioPlayer().resumeAllMusic();
                    }, Duration.seconds(0.5));
                }else{
                    FXGL.getAudioPlayer().stopAllMusic();
                    FXGL.getAudioPlayer().stopAllSounds();
                    Sound gameSound = FXGL.getAssetLoader().loadSound("smb_mariodie.wav");
                    FXGL.getAudioPlayer().playSound(gameSound);
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
        FXGL.getGameScene().setBackgroundRepeat("background.jpg");

        Music gameMusic = FXGL.getAssetLoader().loadMusic("Super Mario Bros. Theme Song [TubeRipper.com].mp3");
        FXGL.getAudioPlayer().loopMusic(gameMusic);

        Label userLabel = new Label("User: " + this.userName);
        userLabel.setTranslateX(50);
        userLabel.setTranslateY(15);
        userLabel.setStyle("-fx-text-fill: white");
        FXGL.getGameScene().addUINode(userLabel);

        Label levelLabel = new Label("Level: " + this.level);
        levelLabel.setTranslateX(50);
        levelLabel.setTranslateY(30);
        levelLabel.setStyle("-fx-text-fill: white");
        FXGL.getGameScene().addUINode(levelLabel);

        Label scoreLabel = new Label("Score: ");
        scoreLabel.setTranslateX(50);
        scoreLabel.setTranslateY(45);
        scoreLabel.setStyle("-fx-text-fill: white");
        FXGL.getGameScene().addUINode(scoreLabel);

        Label scoreNumber = new Label("0");
        scoreNumber.setTranslateX(85);
        scoreNumber.setTranslateY(45);
        scoreNumber.setStyle("-fx-text-fill: white");
        scoreNumber.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString());
        FXGL.getGameScene().addUINode(scoreNumber);
    }

    private void setLadderForGameLadders(Ladder ladder, int ladderX, int ladderY, boolean last) {
        ladder.setNewLadder(ladderX, ladderY, last);
        this.ladders.add(ladder);
    }

    protected void initialiseGame1() {
        initGameUI();
        Ground ground1 = new Ground(-145, 560, 472, true);
        ground1.setNewGround(50, 750, 700, 20);
        this.grounds.add(ground1);

        Ground ground2 = new Ground(5, 510, 342, false);
        ground2.setNewGround(200, 620, 500, 20);
        this.grounds.add(ground2);

        Ground ground3 = new Ground(-35, 560, 212, false);
        ground3.setNewGround(160, 490, 600, 20);
        this.grounds.add(ground3);

        Ground ground4 = new Ground(-95, 510, 82, false);
        ground4.setNewGround(100, 360, 600, 20);
        this.grounds.add(ground4);

        Ground ground5 = new Ground(-145, 560, -48, false);
        ground5.setNewGround(50, 230, 700, 20);
        this.grounds.add(ground5);

        Ground ground6 = new Ground(195, 450, -178, false);
        ground6.setNewGround(400, 100, 250, 20);
        this.grounds.add(ground6);

        this.setLadderForGameLadders(new Ladder(), 300, 648, false);
        this.setLadderForGameLadders(new Ladder(), 650, 518, false);
        this.setLadderForGameLadders(new Ladder(), 250, 388, false);
        this.setLadderForGameLadders(new Ladder(), 450, 258, false);
        this.setLadderForGameLadders(new Ladder(), 600, 128, true);

        PowerUp powerUp1 = new PowerUp();
        powerUp1.setNewPowerUp(320, 240);
        this.powerUps.add(powerUp1);

        this.princes1.setNewPrinces(220, -250, "");
        this.donkeyKong1.setNewDonkeyKong(-150, -248);

        this.player1.setNewPlayer(-100, 472);
        getGameTimer().runOnceAfter(() -> {
            createBarrol2(2);
        }, Duration.seconds(0.12));
    }

    protected void initialiseGame2() {
        initGameUI();
        Ground ground1 = new Ground(-145, 560, 472, true);
        ground1.setNewGround(50, 750, 700, 20);
        this.grounds.add(ground1);

        Ground ground2 = new Ground(-95, 410, 342, false);
        ground2.setNewGround(100, 620, 500, 20);
        this.grounds.add(ground2);

        Ground ground3 = new Ground(-35, 470, 212, false);
        ground3.setNewGround(160, 490, 500, 20);
        this.grounds.add(ground3);

        Ground ground4 = new Ground(-95, 510, 82, false);
        ground4.setNewGround(100, 360, 600, 20);
        this.grounds.add(ground4);

        Ground ground5 = new Ground(-145, 560, -48, false);
        ground5.setNewGround(50, 230, 700, 20);
        this.grounds.add(ground5);

        Ground ground6 = new Ground(165, 410, -178, false);
        ground6.setNewGround(360, 100, 250, 20);
        this.grounds.add(ground6);

        this.setLadderForGameLadders(new Ladder(), 400, 128, true);
        this.setLadderForGameLadders(new Ladder(), 500, 648, false);
        this.setLadderForGameLadders(new Ladder(), 250, 518, false);
        this.setLadderForGameLadders(new Ladder(), 450, 388, false);
        this.setLadderForGameLadders(new Ladder(), 200, 258, false);
        this.setLadderForGameLadders(new Ladder(), 650, 258, false);

        PowerUp powerUp1 = new PowerUp();
        powerUp1.setNewPowerUp(-10, 120);
        this.powerUps.add(powerUp1);

        PowerUp powerUp2 = new PowerUp();
        powerUp2.setNewPowerUp(550, 510);
        this.powerUps.add(powerUp2);

        this.princes1.setNewPrinces(350, -250, "Flip");
        this.donkeyKong1.setNewDonkeyKong(-150, -248);

        this.player1.setNewPlayer(-100, 472);
        getGameTimer().runOnceAfter(() -> {
            createBarrol2(1);
        }, Duration.seconds(0.01));
    }

    protected void initialiseGame3() {
        initGameUI();
        Ground ground1 = new Ground(-145, 560, 472, true);
        ground1.setNewGround(50, 750, 700, 20);
        this.grounds.add(ground1);
        Ground ground2 = new Ground(-95, 510, 342, false);
        ground2.setNewGround(100, 620, 600, 20);
        this.grounds.add(ground2);
        Ground ground3 = new Ground(-45, 560, 212, false);
        ground3.setNewGround(150, 490, 600, 20);
        this.grounds.add(ground3);
        Ground ground4 = new Ground(5, 520, 82, false);
        ground4.setNewGround(200, 360, 500, 20);
        this.grounds.add(ground4);
        Ground ground5 = new Ground(-145, 560, -48, false);
        ground5.setNewGround(50, 230, 700, 20);
        this.grounds.add(ground5);
        Ground ground6 = new Ground(195, 450, -178, false);
        ground6.setNewGround(400, 100, 250, 20);
        this.grounds.add(ground6);
        this.setLadderForGameLadders(new Ladder(), 650, 648, false);
        this.setLadderForGameLadders(new Ladder(), 250, 518, false);
        this.setLadderForGameLadders(new Ladder(), 400, 388, false);
        this.setLadderForGameLadders(new Ladder(), 650, 388, false);
        this.setLadderForGameLadders(new Ladder(), 500, 258, false);
        this.setLadderForGameLadders(new Ladder(), 600, 128, true);
        PowerUp powerUp1 = new PowerUp();
        powerUp1.setNewPowerUp(450, 240);
        this.powerUps.add(powerUp1);
        PowerUp powerUp2 = new PowerUp();
        powerUp2.setNewPowerUp(130, 115);
        this.powerUps.add(powerUp2);
        this.princes1.setNewPrinces(220, -250, "");
        this.donkeyKong1.setNewDonkeyKong(-150, -248);
        this.player1.setNewPlayer(-100, 472);
        getGameTimer().runOnceAfter(() -> {
            createBarrol2(0.65);
        }, Duration.seconds(0.0005));
    }

    protected void initialiseExit() {
        FXGL.getGameScene().setBackgroundRepeat("0x0.jpg");
        FXGL.getAudioPlayer().stopAllMusic();
        FXGL.getAudioPlayer().stopAllSounds();
        FXGL.set("score", 0);
        FXGL.getGameScene().clearGameViews();
        FXGL.getPhysicsWorld().clear();
        FXGL.getGameScene().clearUINodes();
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
        this.playerEnd = false;
        this.playerSecontToEnd = false;

        if(this.timerAction != null){
            this.timerAction.expire();
            this.timerAction = null;
        }

        if(this.timerAction2 != null){
            this.timerAction2.expire();
            this.timerAction2 = null;
        }
    }

    @Override
    protected void initUI() {
        FXGL.getGameScene().setBackgroundRepeat("0x0.jpg");
        Label username = new Label("LOGIN:");
        username.setStyle("-fx-text-fill: white");
        username.setTranslateX(300);
        username.setTranslateY(250);
        username.setFont(Font.font("Monospace", 70));
        TextField TF = new TextField();
        TF.setTranslateX(250);
        TF.setTranslateY(450);
        TF.setPrefWidth(300);
        TF.setPrefHeight(20);
        TF.setStyle("-fx-background-color: white;");

        Button button = new Button("LOGIN");
        button.setTextFill(Color.WHITE);
        button.setTranslateX(300);
        button.setTranslateY(520);
        button.setPrefSize(200, 20);
        button.setStyle("-fx-background-color: rgb(110, 74, 33);");
        button.setFont(Font.font("Monospace"));
        button.setOnAction(actionEvent -> {
            this.userName = TF.getText();
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
        b1.setTextFill(Color.WHITE);
        b1.setTranslateX(160);
        b1.setTranslateY(230);
        b1.setPrefSize(450, 100);
        b1.setFont(Font.font("Monospace", 36));
        b1.setStyle("-fx-background-color: rgb(110, 74, 33);");
        Button b2 = new Button("LEVEL 2");
        b2.setTextFill(Color.WHITE);
        b2.setTranslateX(160);
        b2.setTranslateY(350);
        b2.setPrefSize(450, 100);
        b2.setFont(Font.font("Monospace", 36));
        b2.setStyle("-fx-background-color: rgb(110, 74, 33);");
        Button b3 = new Button("LEVEL 3");
        b3.setTextFill(Color.WHITE);
        b3.setTranslateX(160);
        b3.setTranslateY(480);
        b3.setPrefSize(450, 100);
        b3.setFont(Font.font("Monospace", 36));
        b3.setStyle("-fx-background-color: rgb(110, 74, 33);");

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