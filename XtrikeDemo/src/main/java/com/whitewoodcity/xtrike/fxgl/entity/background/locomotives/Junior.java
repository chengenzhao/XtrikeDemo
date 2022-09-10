package com.whitewoodcity.xtrike.fxgl.entity.background.locomotives;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

public class Junior extends Locomotive{

  private Texture decoration;

  public Junior() {
    var bdImg = FXGL.image("backgrounds/locomotives/junior/body.png", 1100, 350);
    var lvImg = FXGL.image("backgrounds/locomotives/junior/lever.png", 9000, 150);
    var whImg = FXGL.image("backgrounds/locomotives/junior/wheel.png",2200,200);
    lever = new AnimationChannel(lvImg, 15, 600, 150, Duration.seconds(0.25), 0, 14);
    wheel = new AnimationChannel(whImg, 2, 1100, 200, Duration.seconds(0.1), 0, 1);
    lv = new AnimatedTexture(lever);//new AnimationChannel(lvImg, 15, (int)(1200*ratio), (int)(300*ratio), Duration.seconds(0.25), 0, 0)
    wh = new AnimatedTexture(wheel);//new AnimationChannel(whImg, 2, (int)(3000*ratio), (int)(400*ratio), Duration.seconds(0.1), 0, 0)
    bd = new Texture(bdImg);

    var decorationImg = FXGL.image("backgrounds/locomotives/junior/decoration.png",75,75);
    decoration = new Texture(decorationImg);
  }

  @Override
  public void onAdded() {

    entity.getViewComponent().addChild(wh);
    wh.setTranslateX(-125);
    wh.setTranslateY(-185+309);
    entity.getViewComponent().addChild(lv);
    lv.setTranslateX(75);
    lv.setTranslateY(-135+309);
    entity.getViewComponent().addChild(bd);
    bd.setTranslateX(-125);
    bd.setTranslateY(-340+309);

    lv.loop();
    wh.loop();

    decoration.setTranslateY(-60);
    decoration.setTranslateX(-5);
    entity.getViewComponent().addChild(decoration);

  }
}
