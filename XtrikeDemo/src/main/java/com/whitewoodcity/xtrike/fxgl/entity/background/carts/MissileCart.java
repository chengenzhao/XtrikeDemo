package com.whitewoodcity.xtrike.fxgl.entity.background.carts;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class MissileCart extends Component {
  private final Texture texture;

  public MissileCart() {
    var bck = FXGL.image("backgrounds/carts/missile/missile.png", 1799.0/2, 861.0/2);
    texture = new Texture(bck);
  }

  @Override
  public void onAdded() {
    texture.setTranslateY(-430.5 + 86);
    texture.setTranslateX(-60);
    entity.getViewComponent().addChild(texture);
  }
}
