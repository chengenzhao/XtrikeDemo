package com.whitewoodcity.xtrike.fxgl.entity.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.whitewoodcity.xtrike.fxgl.gameapplication.BattleGameApplication;

public class MovingForwardComponent extends Component {
  private double velocity;
  private final double range;
  private final int freeze;

  public MovingForwardComponent(double velocity, double range) {
    this(velocity, range, 1);
  }

  public MovingForwardComponent(double velocity, double range, int freeze) {
    this.velocity = velocity;
    this.range = range;
    this.freeze = freeze;
  }

  private Status status = Status.RUN;
  private int statusCount = 0;

  @Override
  public void onUpdate(double tpf) {

    if(status == Status.IDLE) return;

    if(status == Status.FIRE && statusCount%freeze!=0){
      statusCount++;
      return;
    }

//    var player = FXGL.<GameApplication>getAppCast().getPlayer();
//    var x = (player!=null && player.isActive()) ? player.getX() : 0;

    var x = FXGL.<BattleGameApplication>getAppCast().getPlayerX();

    if(FXGLMath.abs(x - entity.getX()) > range){
      setStatus(Status.RUN);
      entity.getComponentOptional(PhysicsComponent.class)
        .ifPresentOrElse(physicsComponent -> physicsComponent.setVelocityX(- velocity),
          ()-> entity.setX(entity.getX() - velocity*tpf));
    }else{
      entity.getComponentOptional(PhysicsComponent.class)
        .ifPresent(physicsComponent -> physicsComponent.setVelocityX(0));
      setStatus(Status.FIRE);
    }
  }
  private void setStatus(Status status){
    if(this.status == status){
      statusCount++;
    }else{
      this.status = status;
      statusCount = 0;
    }
  }

  public Status getStatus() {
    return status;
  }

  public int getStatusCount() {
    return statusCount;
  }

  public void stop(){
    setStatus(Status.IDLE);
  }

  public void ruin(){
    setStatus(Status.RUIN);
  }
}
