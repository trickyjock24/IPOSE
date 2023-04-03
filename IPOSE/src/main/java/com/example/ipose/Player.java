package com.example.ipose;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.UserAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;


public class Player extends Component  {
    private static final int MOVE_SPEED = 5;
    private static final int minLadderAfstand = 15;

    public void move(int direction) { // 1 of -1
        entity.translateX(MOVE_SPEED * direction);
    }


    public void climb(int direction, ArrayList<Entity> ladders) { // 1 = up, 2 = down
        // Bereken de afstand naar de ladder toe:
        ArrayList<HashMap<Double, Double>> distances = new ArrayList<>(); // Een HashMap is net als een dictionary :)

        for (Entity i : ladders) { // We gaan door alle ladders heen
            double distanceX = Math.abs(i.getX() - entity.getX());

            double distanceY = 69;
            if (direction == 1) {
                distanceY = (i.getBottomY() * - 1) - (-1 * entity.getBottomY());
            }
            else {
                // DE CODE IN DEZE ELSE STATEMENT IS BROKEN
                // DIT IS DE REDEN DAT DE LADDER OMLAAG ZO KUT DOET
                distanceY = i.getY() - i.getHeight() - entity.getY(); // i.getHeight() is 0 dus tis broken :). De Height is ongeveer 120 bij een grootte van 2x2

                System.out.println("DISTANCE IS: " + distanceY);
                System.out.println("LADDER HEIGHT IS: " + i.getHeight());
                System.out.println("ENTITY BOTTOM Y: " + entity.getBottomY());
                System.out.println("LADDER BOTTOM Y: " + i.getBottomY());
                System.out.println("ENTITY Y: " + entity.getY());
                System.out.println("LADDER Y: " + i.getY());
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
                return;
            }

            // Als de speler onderaan de ladder is, niet verder omlaag klimmen
            if (direction == -1 && smallestHashMap.get(smallestX) >= 0) {
                return;
            }

            // Als de afstand tot de ladder kleiner is dan de minimum ladder afstand, verplaats de speler
            if (smallestX < minLadderAfstand) {
                entity.translateY(2 * -direction);
            }
        }
    }


}
