package com.whitewoodcity.xtrike.fxgl.entity.background.locomotives;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

public class Locomotive extends Component {
  private PhysicsComponent physics;

  protected AnimatedTexture lv, wh;
  protected AnimationChannel lever, wheel;
  protected Texture bd;

  private Texture decoration;

  public Locomotive() {
    var bdImg = FXGL.image("backgrounds/locomotives/medium/body.png", 1050, 340);
    var lvImg = FXGL.image("backgrounds/locomotives/medium/lever.png", 10500, 150);
    var whImg = FXGL.image("backgrounds/locomotives/medium/wheels.png",2100,150);
    lever = new AnimationChannel(lvImg, 15, 700, 150, Duration.seconds(0.25), 0, 14);
    wheel = new AnimationChannel(whImg, 2, 1050, 150, Duration.seconds(0.1), 0, 1);
    lv = new AnimatedTexture(lever);//new AnimationChannel(lvImg, 15, (int)(1200*ratio), (int)(300*ratio), Duration.seconds(0.25), 0, 0)
    wh = new AnimatedTexture(wheel);//new AnimationChannel(whImg, 2, (int)(3000*ratio), (int)(400*ratio), Duration.seconds(0.1), 0, 0)
    bd = new Texture(bdImg);

    var pgsImg = FXGL.image("backgrounds/locomotives/medium/decoration.png",57,64);
    decoration = new Texture(pgsImg);
  }

  @Override
  public void onAdded() {
    physics.setBodyType(BodyType.STATIC);
    entity.getViewComponent().addChild(wh);
    wh.setTranslateX(-125);
    wh.setTranslateY(-150+309);
    entity.getViewComponent().addChild(lv);
    lv.setTranslateX(25);
    lv.setTranslateY(-150+309);
    entity.getViewComponent().addChild(bd);
    bd.setTranslateX(-125);
    bd.setTranslateY(-340+309);

    lv.loop();
    wh.loop();

    decoration.setTranslateY(-57);
    decoration.setTranslateX(-10);
    entity.getViewComponent().addChild(decoration);
  }
}