package com.whitewoodcity.xtrike.fxgl.entity.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import com.whitewoodcity.xtrike.fxgl.entity.effect.FadeOutEffect;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class DestroyableComponent extends Component {
  public enum DestroyType{
    FADE_OUT, OTHERS, GROUND_EXPLOSION, AIR_EXPLOSION, SHELL_EXPLOSION
  }

  private final DestroyType destroyType;

  private Runnable lambda;

  private EffectComponent effectComponent;

  public DestroyableComponent(Runnable lambda) {
    destroyType = DestroyType.OTHERS;

    this.lambda = lambda;
  }

  public DestroyableComponent(DestroyType destroyType) {
    this.destroyType = destroyType;
  }

  boolean exploded = false;

  public void destroy(){
    if (entity.getType() == Type.TANK_M7){
      FXGL.getGameScene().getViewport().shake(10,0);
    }

    switch (destroyType){
      case FADE_OUT -> {
        if(!effectComponent.hasEffect(FadeOutEffect.class))
          effectComponent.startEffect(new FadeOutEffect(Duration.seconds(1.0)));
      }
      case GROUND_EXPLOSION -> {
        if(!exploded){
          exploded = true;
          stopEntity();
//          entity.removeComponent(MovingForwardComponent.class);
//          entity.removeComponent(PhysicsComponent.class);
//          entity.removeComponent(CollidableComponent.class);
          var img = FXGL.image("explosions/ground.png", 5700, 250);
          var exploChannel = new AnimationChannel(img, Duration.seconds(1), 19);
          var exploTexture = new AnimatedTexture(exploChannel).play();
          exploTexture.setOnCycleFinished(()-> entity.removeFromWorld());
          entity.getViewComponent().clearChildren();
          exploTexture.setTranslateY(entity.getHeight() - 232.5);
          exploTexture.setTranslateX(entity.getWidth()/2 - 150);
          entity.getViewComponent().addChild(exploTexture);
          exploTexture.play();
        }
      }
      case AIR_EXPLOSION ->{
        if(!exploded) {
          exploded = true;
          stopEntity();
//          entity.removeComponent(MovingForwardComponent.class);
//          entity.removeComponent(SimpleMovingForwardComponent.class);
//          entity.removeComponent(PhysicsComponent.class);
//          entity.removeComponent(FloatingComponent.class);
//          entity.removeComponent(CollidableComponent.class);
          var img = FXGL.image("explosions/air.png", 3780, 270);
          var exploChannel = new AnimationChannel(img,  Duration.seconds(0.5), 14);
          var exploTexture = new AnimatedTexture(exploChannel);
          exploTexture.setOnCycleFinished(() -> entity.removeFromWorld());
          entity.getViewComponent().clearChildren();
          exploTexture.setTranslateY(entity.getHeight()/2 - 135);
          exploTexture.setTranslateX(entity.getWidth()/2 - 135);
          entity.getViewComponent().addChild(exploTexture);
          entity.setRotation(0);
          entity.setScaleX(1);
          entity.setScaleY(1);
          exploTexture.play();
//          FXGL.getGameScene().getViewport().shake(10,0);
        }
      }
      case SHELL_EXPLOSION ->  {
        if(!exploded) {
          exploded = true;
          entity.getComponentOptional(ProjectileComponent.class).ifPresent(projectile -> {
            projectile.setDirection(new Point2D(1,0));
            projectile.setSpeed(0);
          });
          entity.getComponentOptional(ParabolicComponent.class).ifPresent(ParabolicComponent::stop);
          var img = FXGL.image("explosions/shell.png", 1800, 220);
          var exploChannel = new AnimationChannel(img, Duration.seconds(0.5), 9);
          var exploTexture = new AnimatedTexture(exploChannel);
          exploTexture.setOnCycleFinished(() -> entity.removeFromWorld());
          entity.getViewComponent().clearChildren();
          exploTexture.setTranslateY(entity.getHeight() - 175);
          exploTexture.setTranslateX(-entity.getWidth() / 2);
          entity.getViewComponent().addChild(exploTexture);
          entity.setRotation(0);
          entity.setScaleX(1);
          entity.setScaleY(1);
          exploTexture.play();
        }
      }
      case OTHERS -> lambda.run();
    }
  }

  private void stopEntity(){
    entity.getComponentOptional(MovingForwardComponent.class).ifPresent(MovingForwardComponent::stop);
    entity.getComponentOptional(MovingAlwaysForwardComponent.class).ifPresent(MovingAlwaysForwardComponent::stop);
    entity.getComponentOptional(SimpleMovingForwardComponent.class).ifPresent(SimpleMovingForwardComponent::stop);
    entity.getComponentOptional(FloatingComponent.class).ifPresent(FloatingComponent::stop);
  }
}
