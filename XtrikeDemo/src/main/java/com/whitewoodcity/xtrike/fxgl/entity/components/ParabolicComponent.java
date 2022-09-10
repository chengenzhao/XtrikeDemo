package com.whitewoodcity.xtrike.fxgl.entity.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;

public class ParabolicComponent extends Component {

  private final double speed = FXGLMath.random(1, 10) + 10;
  private double ySpeed = -FXGLMath.random(1, 10);
  private final double yySpeed = 0.5;

  private boolean stop = false;

  @Override
  public void onUpdate(double tpf) {
    if(stop) return;

    entity.setX(entity.getX() - speed);
    ySpeed += yySpeed;
    entity.setY(entity.getY() + ySpeed);
  }

  public void stop(){
    stop = true;
  }
}
