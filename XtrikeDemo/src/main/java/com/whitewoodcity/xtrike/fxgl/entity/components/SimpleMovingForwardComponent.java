package com.whitewoodcity.xtrike.fxgl.entity.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class SimpleMovingForwardComponent  extends Component {
  private double velocity;

  boolean stop = false;

  public SimpleMovingForwardComponent(double velocity) {
    this.velocity = velocity;
  }

  @Override
  public void onUpdate(double tpf) {
    if(stop) return;
    entity.getComponentOptional(PhysicsComponent.class)
      .ifPresentOrElse(physicsComponent -> physicsComponent.setVelocityX(- velocity),
        ()-> entity.setX(entity.getX() - velocity*tpf));
  }

  public void stop(){
    stop = true;
  }
}
