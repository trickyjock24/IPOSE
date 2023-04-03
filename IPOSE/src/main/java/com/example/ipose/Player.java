package com.example.ipose;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.UserAction;


public class Player extends Component  {

    private static final int MOVE_SPEED = 5;

    public void move(int direction) { // 1 of -1
        entity.translateX(MOVE_SPEED * direction);
    }

}
