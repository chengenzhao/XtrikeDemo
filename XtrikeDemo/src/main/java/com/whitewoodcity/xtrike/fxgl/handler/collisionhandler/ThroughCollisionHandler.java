package com.whitewoodcity.xtrike.fxgl.handler.collisionhandler;

import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.whitewoodcity.xtrike.fxgl.entity.components.DestroyableComponent;
import com.whitewoodcity.xtrike.fxgl.entity.effect.BrightEffect;
import javafx.util.Duration;

public class ThroughCollisionHandler extends CollisionHandlerWithPlayerHitEffect {

  /**
   * The order of types determines the order of entities in callbacks.
   *
   * @param entity entity type of the first entity
   * @param shell entity type of the second entity
   */
  public ThroughCollisionHandler(Object entity, Object shell) {
    super(entity, shell);
  }

  @Override
  protected void onCollision(Entity entity, Entity shell) {
    super.onCollision(entity, shell);
    shell.getComponentOptional(HealthIntComponent.class).ifPresent(dmgComponent -> {
      var damage = dmgComponent.getMaxValue()/3;
      entity.getComponentOptional(HealthIntComponent.class).ifPresent(healthIntComponent -> healthIntComponent.damage(damage));
      entity.getComponentOptional(HealthDoubleComponent.class).ifPresent(healthDoubleComponent -> healthDoubleComponent.damage(damage));
    });
    shell.getComponentOptional(HealthDoubleComponent.class).ifPresent(dmgComponent -> {
      var damage = dmgComponent.getMaxValue()/3;
      entity.getComponentOptional(HealthIntComponent.class).ifPresent(healthIntComponent -> healthIntComponent.damage((int)damage));
      entity.getComponentOptional(HealthDoubleComponent.class).ifPresent(healthDoubleComponent -> healthDoubleComponent.damage(damage));
    });

    entity.getComponentOptional(HealthIntComponent.class).ifPresent(health->{
      if(health.isZero()){
        entity.getComponentOptional(DestroyableComponent.class)
          .ifPresent(DestroyableComponent::destroy);
      }else{
        entity.getComponentOptional(EffectComponent.class).ifPresent((effectComponent) ->
          effectComponent.startEffect(new BrightEffect(Duration.seconds(1.0 / 60))));
      }
    });
    entity.getComponentOptional(HealthDoubleComponent.class).ifPresent(health->{
      if(health.isZero()){
        entity.getComponentOptional(DestroyableComponent.class)
          .ifPresent(DestroyableComponent::destroy);
      }else{
        entity.getComponentOptional(EffectComponent.class).ifPresent((effectComponent) ->
          effectComponent.startEffect(new BrightEffect(Duration.seconds(1.0 / 60))));
      }
    });
  }
}
