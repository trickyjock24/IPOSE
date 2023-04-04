package com.example.ipose;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;


public class Player extends Component  {
    // Dit is de hoogte van de ladder die gebruikt word voor de ladder berekeningen
    // Als je de grootte van de ladder veranderd verander deze waarde ook
    // Ik heb dit gedaan door: (speler coordinaat bovenaan de ladder - speler coordinaat onderin de ladder)
    private static final int PLAYER_HEIGHT = 120;
    private static final int MIN_LADDER_AFSTAND = 20;

    private float moveSpeed = 3f;
    private boolean isClimbing = false;


    public void jump() {
        // DIT MOET NAAR DE PLAYER KLASSE
        this.entity.translateY(-40);
        getGameTimer().runOnceAfter(() -> {
            this.entity.translateY(40);
        }, Duration.seconds(0.2));

    }


    public void move(int direction) { // 1 of -1
        if (!isClimbing) {
            entity.translateX(moveSpeed * direction);
        }
    }


    public void climb(int direction, ArrayList<Entity> ladders) { // 1 = up, -1 = down
        // Bereken de afstand naar de ladder toe:
        ArrayList<HashMap<Double, Double>> distances = new ArrayList<>(); // Een HashMap is net als een dictionary :)

        for (Entity i : ladders) { // We gaan door alle ladders heen
            double distanceX = Math.abs(i.getX() - entity.getX());

            double distanceY = 69;
            if (direction == 1) {
                distanceY = (i.getBottomY() * - 1) - (-1 * entity.getBottomY());
            }
            else {
                // ZORG ERVOOR DAT JE ALTIJD OP DE HOOGTE BENT VAN DE PLAYER_HEIGHT
                distanceY = (i.getY() + PLAYER_HEIGHT) - entity.getY(); // i.getHeight() is 0 dus tis broken :). De Height is ongeveer 120 bij een grootte van 2x2
            }


            HashMap<Double, Double> lol = new HashMap<>();
            lol.put(distanceX, distanceY);
            distances.add(lol);
        }

        // Pakt de HashMap met de kleinste x-afstand
        // Als je dit niet helemaal begrijpt vraag het aan chatgpt want kan best confusing zijn
        HashMap<Double, Double> smallestHashMap = distances.stream()
                .min(Comparator.comparingDouble(map -> map.keySet()
                        .stream()
                        .findFirst()
                        .orElse(Double.MAX_VALUE)))
                .orElse(null);

        if (smallestHashMap != null) {

            double smallestX = smallestHashMap. // De kleinste x-afstand
                    keySet().
                    iterator().
                    next();

            // Als de speler bovenaan de ladder is, niet verder omhoog klimmen
            if (direction == 1 && smallestHashMap.get(smallestX) <= 0) {
                isClimbing = false;
                return;
            }

            // Als de speler onderaan de ladder is, niet verder omlaag klimmen
            if (direction == -1 && smallestHashMap.get(smallestX) <= 0) {
                isClimbing = false;
                return;
            }

            // Als de afstand tot de ladder kleiner is dan de minimum ladder afstand, verplaats de speler
            if (smallestX < MIN_LADDER_AFSTAND) {
                entity.translateY(2 * -direction);
                isClimbing =true;
                return;
            }
            else {
                isClimbing =false;
            }
        }
    }

    // ROEP DEZE METHODE OP OM DE POWERUP TE ACTIVEREN.
    public void enablePowerup(Powerup powerup) {
        System.out.println("POWERUP IS GEACTIVEERD!");
        powerup.startPowerup();
    }


    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }



}
