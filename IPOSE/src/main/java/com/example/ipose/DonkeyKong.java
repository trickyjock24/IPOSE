package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;

public class DonkeyKong {
    private Entity donkeyKong;
    private String donkeyKongVoorkantImage = "DonkeyKongVoorkant.png";

    public void setNewDonkeyKong(int x, int y){
        this.donkeyKong = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(this.donkeyKongVoorkantImage)
                .with(new CollidableComponent(true))
                .type(EntityTypes.DONKEYKONG)
                .scale(0.2, 0.2)
                .buildAndAttach();
    }
}
