package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {
    private Entity player;
    private ArrayList<Entity> ladders = new ArrayList<>(); // VOEG ALLE LADDERS HIERAAN TOE


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
        });

        onKey(KeyCode.A, () -> {
            player.getComponent(Player.class).move(-1); // move left
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
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(300, 300)
                .view("vincent-spijkers-pixilart.png")
                .with(new Player())
                .buildAndAttach();

        Entity ladder = FXGL.entityBuilder()
                .at(500, 220)
                .view(new Rectangle(30, 100, Color.BLUE))
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