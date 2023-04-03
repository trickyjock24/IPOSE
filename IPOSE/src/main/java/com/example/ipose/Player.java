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
        ArrayList<HashMap<Double, Double>> distances = new ArrayList<>();

        for (Entity i : ladders) {
            double distanceX = Math.abs(i.getX() - entity.getX());

            double distanceY = 69;
            if (direction == 1) {
                distanceY = (i.getBottomY() * - 1) - (-1 * entity.getBottomY());
            }
            else {
                distanceY = entity.getBottomY() - i.getBottomY();
            }
            System.out.println(distanceY);

            HashMap<Double, Double> lol = new HashMap<>();
            lol.put(distanceX, distanceY);
            distances.add(lol);
        }

        // Pakt de dictionary van de korste x-afstand
        HashMap<Double, Double> smallestHashMap = distances.stream()
                .min(Comparator.comparingDouble(map -> map.keySet().stream().findFirst().orElse(Double.MAX_VALUE)))
                .orElse(null);

        if (smallestHashMap != null) {
            double smallestX = smallestHashMap. // De kortste x-afstand
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
