package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class Main extends GameApplication {
    private Entity player;
    private ArrayList<Entity> ladders = new ArrayList<>(); // VOEG ALLE LADDERS HIERAAN TOE

    private String playerVoorkant = "Vincent1-removebg-preview.png";
    private String playerAchterkant = "Vincent1-removebg-previewachterkant.png";
    private String playerKant = "Voorkant";

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Donkey Kong");
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {
        // Input is helaas nog hier

        onKey(KeyCode.D, () -> {
            player.getComponent(Player.class).move(1); // move right

            if(this.playerKant != "Voorkant"){
                changeView(this.playerVoorkant, "Voorkant");
            }
            player.translateX(5);

        });

        onKey(KeyCode.A, () -> {
            player.getComponent(Player.class).move(-1); // move left

            if(this.playerKant != "Achterkant"){
                changeView(this.playerAchterkant, "Achterkant");
            }
            player.translateX(-5);


        });

        onKey(KeyCode.W, () -> {
            // Bereken de afstand tussen de speler en ladder d.m.v stelling van pythagoras
            // Als afstand kleiner is dan x ga de ladder op
            player.getComponent(Player.class).climb(1, this.ladders);
        });

        onKey(KeyCode.S, () -> {
            // Bereken de afstand tussen de speler en ladder d.m.v stelling van pythagoras
            // Als afstand kleiner is dan x ga de ladder op
            player.getComponent(Player.class).climb(-1, this.ladders);

        });

        FXGL.onKeyDown(KeyCode.SPACE, ()->{
            // DIT MOET NAAR DE PLAYER KLASSE
            player.translateY(-40);
            getGameTimer().runOnceAfter(() -> {
                player.translateY(40);
            }, Duration.seconds(0.2));
        });
    }

    public void changeView(String image, String kant){
        playerKant = kant;
        player.getViewComponent().clearChildren();
        player.getViewComponent().addChild(texture(image));
    }

    @Override
    protected void initGame() {

        Entity ladder = FXGL.entityBuilder()
                .at(500, 180)
                .view("ladder.png")
                .with(new Player())
                .scale(2, 2) // BIJ DEZE WAARDE IS DE HEIGHT ONGEVEER 120
                .buildAndAttach();


        player = FXGL.entityBuilder()
                .at(300, 300)
                .view("vincent-spijkers-pixilart.png")
                .with(new Player())
                .scale(0.10, 0.10)
                .buildAndAttach();



        this.ladders.add(ladder);


    }

    @Override
    protected void initUI() {
        // nothing to do here
    }

    public static void main(String[] args) {
        launch(args);
    }
}