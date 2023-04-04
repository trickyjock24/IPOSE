//package com.example.ipose;
//
//import com.almasb.fxgl.app.GameApplication;
//import com.almasb.fxgl.app.GameSettings;
//import com.almasb.fxgl.dsl.FXGL;
//import com.almasb.fxgl.entity.Entity;
//import com.almasb.fxgl.entity.components.CollidableComponent;
//import com.almasb.fxgl.physics.CollisionHandler;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.util.Duration;
//import java.util.Map;
//
//import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
//import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
//
//public class HelloApplication2 extends GameApplication {
//    private Player player1 = new Player();
//    private Ground ground1 = new Ground();
//    private String LadderImage = "ladder.png";
//
//
//    @Override
//    protected void initSettings(GameSettings settings){
//        settings.setWidth(800);
//        settings.setHeight(800);
//        settings.setTitle("testGame");
//    }
//
//    @Override
//    protected void initGame(){
////        this.ladder1 = FXGL.entityBuilder()
////                .at(450, 648)
////                .viewWithBBox(this.LadderImage)
////                .with(new CollidableComponent(true))
////                .type(EntityTypes.LADDER)
////                .scale(1.8, 1.8)
////                .buildAndAttach();
//
//
////        this.ground1.setNewGround();
//        this.player1.setNewPlayer();
//    }
//
//    @Override
//    protected void initInput(){
//        FXGL.onKey(KeyCode.D, ()->{
//            this.player1.playerToRight(this.ground1.getGroundEndRight());
//        });
//
//        FXGL.onKey(KeyCode.RIGHT, ()->{
//            this.player1.playerToRight(this.ground1.getGroundEndRight());
//        });
//
//        FXGL.onKey(KeyCode.A, ()->{
//            this.player1.playerToLeft(this.ground1.getGroundEndLeft());
//        });
//
//        FXGL.onKey(KeyCode.LEFT, ()->{
//            this.player1.playerToLeft(this.ground1.getGroundEndLeft());
//        });
//
//        FXGL.onKeyDown(KeyCode.SPACE, ()->{
//            this.player1.playerJump();
//        });
//
////        FXGL.onKey(KeyCode.W, ()->{
////            playerClimbLadderUp();
////        });
////
////        FXGL.onKey(KeyCode.UP, ()->{
////            playerClimbLadderUp();
////        });
////
////        FXGL.onKey(KeyCode.S, ()->{
////            playerClimbLadderDown();
////        });
////
////        FXGL.onKey(KeyCode.DOWN, ()->{
////            playerClimbLadderDown();
////        });
//    }
//
//    @Override
//    protected void initPhysics(){
//        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.LADDER) {
////            @Override
////            protected void onCollision(Entity player, Entity ladder) {
//////                FXGL.inc("ladderTouch", 1);
//////                ladder1.removeFromWorld();
////                Ladder1Cimb = true;
////            }
//
//            @Override
//            protected void onCollisionBegin(Entity player, Entity ladder) {
////                private test(){
////
////                }
////                Ladder1Cimb = true;
//            }
//
//            @Override
//            protected void onCollisionEnd(Entity player, Entity ladder) {
////                Ladder1Cimb = false;
//            }
//        });
//    }
//
//
//
//    @Override
//    protected void initUI(){
////        Label myText = new Label("hi there");
////        myText.setTranslateX(200);
////        myText.setTranslateY(200);
////        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("ladderTouch").asString());
//
//        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
////        FXGL.getGameScene().addUINode(myText);
//    }
//
//    @Override
//    protected void initGameVars(Map<String, Object> vars){
////        vars.put("ladderTouch", 0);
//    }
//
//    public static void main(String [] args){
//        launch(args);
//    }
//}