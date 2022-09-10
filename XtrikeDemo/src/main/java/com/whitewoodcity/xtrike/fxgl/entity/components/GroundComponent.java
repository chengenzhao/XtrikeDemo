package com.whitewoodcity.xtrike.fxgl.entity.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public abstract class GroundComponent extends Component {

  protected PhysicsComponent physics;
  private double previousVelocityY = 0;
  private double previousX = -1;

  @Override
  public final void onUpdate(double tpf) {
    update(tpf);
    afterUpdate();
  }

  public abstract void update(double tpf);

  private void afterUpdate(){
    previousVelocityY = physics.getVelocityY();
    previousX = entity.getX();
  }

  protected boolean isStandingOnSth() {
    return Math.abs(previousVelocityY) < 0.005 && Math.abs(physics.getVelocityY()) < 0.005;
  }

  protected boolean isBlocked(){
    return Math.abs(entity.getX() - previousX) < 0.005;
  }
}
