package com.whitewoodcity.xtrike.fxgl.entity.ammo;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Bullet extends Component {

  AnimatedTexture texture;
//  static Image img = FXGL.image("player/bullet.png", 800*ratio, 50*ratio);

  public Bullet() {
    Image img = FXGL.image("units/gi/bullet.png", 400, 25);
    var channel = new AnimationChannel(img, 4, 100, 25, Duration.seconds(0.2), 0, 3);
    texture = new AnimatedTexture(channel);
    texture.loop();
  }

  @Override
  public void onAdded() {
    entity.getViewComponent().addChild(texture);
  }

  @Override
  public void onRemoved() {
    super.onRemoved();
  }

  @Override
  public void onUpdate(double tpf) {
    super.onUpdate(tpf);
  }

  public static final Point2D DEGREE_35_POINT = new Point2D(FXGLMath.cosDeg(35), - FXGLMath.sinDeg(35));
  public static final Point2D DEGREE_145_POINT = new Point2D(FXGLMath.cosDeg(145), - FXGLMath.sinDeg(145));

  public static Entity newBullet(SpawnData data) {
    var x = data.getX();
    var y = data.getY();
    var z = data.getZ();//magic number here, not the real z value but an index for different angle
    var point = switch ((int)z){
      case 3 -> new Point2D(0,-1);
      case 2 -> DEGREE_35_POINT;
      case -2 -> DEGREE_145_POINT;
      case -1 -> new Point2D(-1,0);
      default -> new Point2D(1,0);
    };

    return entityBuilder()
      .at(x, y)
      .type(Type.BULLET)
      .bbox(new HitBox(BoundingShape.box(100, 25)))
//      .view(new Rectangle(200*ratio, 50*ratio, Color.WHITE))
      .with(new ProjectileComponent(point, 1750))
      .with(new ExpireCleanComponent(Duration.seconds(0.5)))
//      .with(new OffscreenCleanComponent())
      .with(new HealthIntComponent(15))
      .with(new Bullet())
      .collidable()
      .build();
  }
}
