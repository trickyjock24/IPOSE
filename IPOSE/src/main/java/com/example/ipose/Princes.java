package com.example.ipose;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;

public class Princes {
    private Entity princes;
    private String princesVoorkantImage = "PrincesVoorkant.png";
    private String princesFLIPImage = "PrincesVoorkantFlip.png";

    public void setNewPrinces(int x, int y, String type){
        String image = "";
        if(type == "Flip"){
            image = this.princesFLIPImage;
        }else{
            image = this.princesVoorkantImage;
        }
        this.princes = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(image)
                .with(new CollidableComponent(true))
                .type(EntityTypes.PRINCES)
                .scale(0.12, 0.12)
                .buildAndAttach();
    }
}
