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
    private String vincent2VoorkantImage = "Vincent2 Voorkant.png";
    private String vincent3VoorkantImage = "Vincent3 Voorkant.png";
    private String vincent1AchterkantImage = "Vincent1 Achterkant.png";
    private String vincent2AchterkantImage = "Vincent2 Achterkant.png";
    private String vincent3AchterkantImage = "Vincent3 Achterkant.png";

    public void setNewPlayer(){
        this.player = FXGL.entityBuilder()
                .at(-100, 472)
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
        }
    }

    public void playerToLeft(int End, int Bottom){
        if(this.playerKant != "Achterkant"){
            changeView(this.vincent1AchterkantImage, "Achterkant");
        }

        if(this.player.getX() > End && this.player.getY() == Bottom){
            this.player.translateX(-5);
        }
    }

    public void playerJump(){
        this.player.translateY(-45);
        getGameTimer().runOnceAfter(() -> {
            this.player.translateY(45);
        }, Duration.seconds(0.22));
    }

    public void playerClimbLadderUp(){
        this.player.translateY(-5);
    }

    public void playerClimbLadderDown(){
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






//    private static final float MOVE_SPEED = 3f;
//    private static final int MIN_LADDER_AFSTAND = 20;
//
//    // Dit is de hoogte van de ladder die gebruikt word voor de ladder berekeningen
//    // Als je de grootte van de ladder veranderd verander deze waarde ook
//    // Ik heb dit gedaan door: (speler coordinaat bovenaan de ladder - speler coordinaat onderin de ladder)
//    private static final int PLAYER_HEIGHT = 120;
//    private boolean isCliming = false;
//
//
//    public void jump() {
//        // DIT MOET NAAR DE PLAYER KLASSE
//        this.entity.translateY(-40);
//        getGameTimer().runOnceAfter(() -> {
//            this.entity.translateY(40);
//        }, Duration.seconds(0.2));
//
//    }

//
//    public void move(int direction) { // 1 of -1
//        if (!isCliming) {
//            entity.translateX(MOVE_SPEED * direction);
//        }
//    }
//
//
//    public void climb(int direction, ArrayList<Entity> ladders) { // 1 = up, -1 = down
//        // Bereken de afstand naar de ladder toe:
//        ArrayList<HashMap<Double, Double>> distances = new ArrayList<>(); // Een HashMap is net als een dictionary :)
//
//        for (Entity i : ladders) { // We gaan door alle ladders heen
//            double distanceX = Math.abs(i.getX() - entity.getX());
//
//            double distanceY = 69;
//            if (direction == 1) {
//                distanceY = (i.getBottomY() * - 1) - (-1 * entity.getBottomY());
//            }
//            else {
//                // ZORG ERVOOR DAT JE ALTIJD OP DE HOOGTE BENT VAN DE PLAYER_HEIGHT
//                distanceY = (i.getY() + PLAYER_HEIGHT) - entity.getY(); // i.getHeight() is 0 dus tis broken :). De Height is ongeveer 120 bij een grootte van 2x2
//            }
//
//
//            HashMap<Double, Double> lol = new HashMap<>();
//            lol.put(distanceX, distanceY);
//            distances.add(lol);
//        }
//
//        // Pakt de HashMap met de kleinste x-afstand
//        // Als je dit niet helemaal begrijpt vraag het aan chatgpt want kan best confusing zijn
//        HashMap<Double, Double> smallestHashMap = distances.stream()
//                .min(Comparator.comparingDouble(map -> map.keySet()
//                        .stream()
//                        .findFirst()
//                        .orElse(Double.MAX_VALUE)))
//                .orElse(null);
//
//        if (smallestHashMap != null) {
//
//            double smallestX = smallestHashMap. // De kleinste x-afstand
//                    keySet().
//                    iterator().
//                    next();
//
//            // Als de speler bovenaan de ladder is, niet verder omhoog klimmen
//            if (direction == 1 && smallestHashMap.get(smallestX) <= 0) {
//                isCliming = false;
//                return;
//            }
//
//            // Als de speler onderaan de ladder is, niet verder omlaag klimmen
//            if (direction == -1 && smallestHashMap.get(smallestX) <= 0) {
//                isCliming = false;
//                return;
//            }
//
//            // Als de afstand tot de ladder kleiner is dan de minimum ladder afstand, verplaats de speler
//            if (smallestX < MIN_LADDER_AFSTAND) {
//                entity.translateY(2 * -direction);
//                isCliming=true;
//                return;
//            }
//            else {
//                isCliming=false;
//            }
//        }
//    }





}
