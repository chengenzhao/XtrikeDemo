package com.whitewoodcity.xtrike.fxgl.handler.collisionhandler;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CollisionHandlerWithPlayerHitEffect extends CollisionHandler {

  public CollisionHandlerWithPlayerHitEffect(Object a, Object b) {
    super(a, b);
  }

  @Override
  protected void onCollisionBegin(Entity entity, Entity ammo) {
    super.onCollisionBegin(entity, ammo);

    if(entity.getType() == Type.PLAYER){
      RadialGradient gradient = new RadialGradient(0, 0,  0.5, 0.5, 1, true,  CycleMethod.NO_CYCLE,
        new Stop(0, Color.TRANSPARENT),
        new Stop(1, Color.RED));

      var rect = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(),gradient);

      FXGL.getGameScene().addUINode(rect);

      FadeTransition ft = new FadeTransition(Duration.millis(500), rect);
      ft.setFromValue(1.0);
      ft.setToValue(0.0);

      ft.setOnFinished(e -> FXGL.getGameScene().removeUINodes(rect));

      ft.play();
    }
  }
}
