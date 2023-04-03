package com.example.ipose;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {
    private Entity player;


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
        });

        onKey(KeyCode.S, () -> {
            // Bereken de afstand tussen de speler en ladder d.m.v stelling van pythagoras
            // Als afstand kleiner is dan x ga de ladder op
        });
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(300, 300)
                .view(new Rectangle(25, 25, Color.BLUE))
                .with(new Player())
                .buildAndAttach();
    }

    @Override
    protected void initUI() {
        // nothing to do here
    }

    public static void main(String[] args) {
        launch(args);
    }
}