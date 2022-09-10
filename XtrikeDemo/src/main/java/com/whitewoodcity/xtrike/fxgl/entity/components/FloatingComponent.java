package com.whitewoodcity.xtrike.fxgl.entity.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;

public class FloatingComponent extends Component {

  double maxSeep;
  double currentSpeed = maxSeep;
  double acceleration;
  int direction = -1;

  boolean stop = false;

  public FloatingComponent() {
    this(1.2, 0.1);
  }

  public FloatingComponent(double maxSeep, double acceleration) {
    this.maxSeep = maxSeep;
    this.acceleration = acceleration;
  }

  @Override
  public void onUpdate(double tpf) {
    if(stop) return;

    currentSpeed += direction * acceleration;
    if(FXGLMath.abs(currentSpeed) + 0.005 > maxSeep ){
      direction *= -1;//reverse direction
    }

    entity.setY(entity.getY()+currentSpeed);
  }

  public void stop(){
    stop = true;
  }
}
