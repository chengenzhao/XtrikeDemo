package com.whitewoodcity.xtrike.fxgl.handler.collisionhandler;

import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import com.whitewoodcity.xtrike.fxgl.entity.components.DestroyableComponent;
import com.whitewoodcity.xtrike.fxgl.entity.effect.BrightEffect;
import javafx.util.Duration;

public class BulletCollisionHandler extends CollisionHandlerWithPlayerHitEffect {
  /**
   * The order of types determines the order of entities in callbacks.
   *
   * @param a entity type of the being hit entity
   */
  public BulletCollisionHandler(Object a) {
    super(a, Type.BULLET);
  }

  @Override
  protected void onCollisionBegin(Entity entity, Entity bullet) {
    super.onCollisionBegin(entity,bullet);
    bullet.removeFromWorld();
    entity.getComponentOptional(HealthIntComponent.class).ifPresent(healthIntComponent -> {
      bullet.getComponentOptional(HealthIntComponent.class).ifPresent(damage -> healthIntComponent.damage(damage.getMaxValue()));
      bullet.getComponentOptional(HealthDoubleComponent.class).ifPresent(damage -> healthIntComponent.damage((int) damage.getMaxValue()));
      if (healthIntComponent.isZero()) {
        entity.getComponentOptional(DestroyableComponent.class).ifPresent(DestroyableComponent::destroy);
      } else {
        entity.getComponentOptional(EffectComponent.class).ifPresent((effectComponent) ->
          effectComponent.startEffect(new BrightEffect(Duration.seconds(1.0 / 60))));
      }
    });
    entity.getComponentOptional(HealthDoubleComponent.class).ifPresent(healthDoubleComponent -> {
      bullet.getComponentOptional(HealthIntComponent.class).ifPresent(damage -> healthDoubleComponent.damage(damage.getMaxValue()));
      bullet.getComponentOptional(HealthDoubleComponent.class).ifPresent(damage -> healthDoubleComponent.damage((int) damage.getMaxValue()));
      if (healthDoubleComponent.isZero()) {
        entity.getComponentOptional(DestroyableComponent.class).ifPresent(DestroyableComponent::destroy);
      } else {
        entity.getComponentOptional(EffectComponent.class).ifPresent((effectComponent) ->
          effectComponent.startEffect(new BrightEffect(Duration.seconds(1.0 / 60))));
      }
    });
  }
}
