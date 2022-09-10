package com.whitewoodcity.xtrike.fxgl.entity.units.characters;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import com.whitewoodcity.xtrike.fxgl.entity.components.DestroyableComponent;
import com.whitewoodcity.xtrike.fxgl.entity.components.GroundComponent;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Clancy extends GroundComponent {

  private boolean movingRight = false;
  private boolean movingLeft = false;
  private boolean upward = false;
  private boolean firing = false;

  private final AnimatedTexture upper, lower, flare;
  private final AnimationChannel idle, tilt, vertical, fire, animIdle, animRun, animJump;

  private long firingCount = 0;

  public Clancy() {

    Image upperImage = FXGL.image("units/gi/upper.png", 700, 175);
    Image runImage = FXGL.image("units/gi/run.png", 1440, 110);
    Image jumpImage = FXGL.image("units/gi/jump.png", 1200, 110);
    Image flareImage = FXGL.image("units/gi/fire.png", 2250, 125);

    idle = new AnimationChannel(upperImage, 4, 175, 175, Duration.seconds(1), 0, 0);
    fire = new AnimationChannel(upperImage, 4, 175, 175, Duration.seconds(1), 1, 1);
    tilt = new AnimationChannel(upperImage, 4, 175, 175, Duration.seconds(1), 2, 2);
    vertical = new AnimationChannel(upperImage, 4, 175, 175, Duration.seconds(1), 3, 3);
    animIdle = new AnimationChannel(jumpImage, 10,120, 110, Duration.seconds(1), 0, 0);
    animRun = new AnimationChannel(runImage, 12,120, 110, Duration.seconds(0.5), 0, 11);
    animJump = new AnimationChannel(jumpImage, 10,120, 110, Duration.seconds(1.5), 1, 9);
    var flareChannel = new AnimationChannel(flareImage, 15, 150, 125, Duration.seconds(0.5), 0, 12);

    upper = new AnimatedTexture(idle);
    upper.setTranslateX(-5);
    upper.setTranslateY(-80);
    lower = new AnimatedTexture(animIdle);
    lower.setTranslateX(-15);
    lower.setTranslateY(20);
    flare = new AnimatedTexture(flareChannel);
  }

  @Override
  public void onAdded() {
    physics.setBodyType(BodyType.DYNAMIC);
    entity.getViewComponent().addChild(lower);
    entity.getViewComponent().addChild(upper);

//    var rect = new Rectangle(0,0,10,10);
//    rect.setFill(Color.RED);
//    entity.getViewComponent().addChild(rect);

//    var emitter = ParticleEmitters.newExplosionEmitter(300);
//
//    emitter.setMaxEmissions(Integer.MAX_VALUE);
//    emitter.setNumParticles(10);
//    emitter.setEmissionRate(0.01);
//    emitter.setSize(1, 2);
//    emitter.setScaleFunction(i -> FXGLMath.randomPoint2D().multiply(0.01));
//    emitter.setExpireFunction(i -> Duration.seconds(random(0.25, 2.5)));
//    emitter.setAccelerationFunction(() -> Point2D.ZERO);
//    emitter.setVelocityFunction(i -> FXGLMath.randomPoint2D().multiply(random(1, 45)));
//    emitter.setColor(Color.WHITE);
//    emitter.setEndColor(Color.TRANSPARENT);
//
//    entity.addComponent(new ParticleComponent(emitter));
  }

  @Override
  public void update(double tpf) {

    if (firing) {
      firingCount++;
    } else {
      firingCount = 0;
    }

    if (firingCount % 4 == 1) {
      if((movingLeft||movingRight)&&upward)
        FXGL.spawn("Bullet", entity.getX() + entity.getScaleX() * 125, entity.getY() + (-5*entity.getScaleX() - 80) + Math.random() * 3, entity.getScaleX()*2);
      else if(upward)
        FXGL.spawn("Bullet", entity.getX() - 10 + entity.getScaleX() * 28 + (Math.random() * 6 - 3) * 0.5, entity.getY() - 100 + Math.random() * 3, 3);
      else {
        FXGL.spawn("Bullet", entity.getX() + entity.getScaleX() * 135, entity.getY() + 10 + Math.random() * 3, entity.getScaleX());
      }
    }

    if(movingRight&&movingLeft){
      physics.setVelocityX(0);
      entity.setScaleX(1);
    } else if (movingRight) {
      physics.setVelocityX(500);
      entity.setScaleX(1);
    } else if (movingLeft) {
      physics.setVelocityX(-500);
      entity.setScaleX(-1);
    }

    if(movingRight||movingLeft){
      if (upward)
        upper.loopAnimationChannel(tilt);
      else if (firing) {
        upper.loopAnimationChannel(fire);
      }else
        upper.loopAnimationChannel(idle);

      if(upward){
        flare.setRotate(-35);
        flare.setTranslateX(45);
        flare.setTranslateY(-95);
      }else if(firing){
        flare.setRotate(0);
        flare.setTranslateX(60);
        flare.setTranslateY(-35);
      }
    }else{
      if (upward) {
        upper.loopAnimationChannel(vertical);
        flare.setRotate(-90);
        flare.setTranslateX(-5);
        flare.setTranslateY(-110);
      } else {
        if (firing) {
          upper.loopAnimationChannel(fire);
          flare.setRotate(0);
          flare.setTranslateX(60);
          flare.setTranslateY(-35);
        }else
          upper.loopAnimationChannel(idle);
      }
      physics.setVelocityX(0);
    }

    if (isStandingOnSth()) {
      if (physics.getVelocityX() != 0) {
        if (lower.getAnimationChannel() != animRun) {
          lower.loopAnimationChannel(animRun);
        }
      } else {
        lower.loopAnimationChannel(animIdle);
      }
    }
  }

  public void moveRight() {
    movingRight = true;
  }

  public void moveLeft() {
    movingLeft = true;
  }

  public void stopMovingRight() {
    movingRight = false;
  }

  public void stopMovingLeft() {
    movingLeft = false;
  }

  public void jump() {
    if (isStandingOnSth()) {
      physics.setVelocityY(-720);
      lower.playAnimationChannel(animJump);
    }
  }

  public void fire() {
    firing = true;
    flare.loop();
    if(!entity.getViewComponent().getChildren().contains(flare))
      entity.getViewComponent().addChild(flare);
  }

  public void stopFire() {
    firing = false;
    flare.stop();
    entity.getViewComponent().removeChild(flare);
  }

  public void upward() {
    upward = true;
  }

  public void stopUpward() {
    upward = false;
  }

  public static Entity player(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);

    return FXGL.entityBuilder()
      .at(data.getX(), data.getY())
      .type(Type.PLAYER)
      .collidable()
      .bbox(new HitBox(BoundingShape.box(80, 120)))
//      .view(new Rectangle(80,120, Color.RED))
      .with(physics)
      .with(new EffectComponent())
      .with(new Clancy())
      .with(new HealthIntComponent(1000))
      .with(new DestroyableComponent(DestroyableComponent.DestroyType.FADE_OUT))
      .zIndex(-80*120)
      .build();
  }

}