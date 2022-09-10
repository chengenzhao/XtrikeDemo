package com.whitewoodcity.xtrike.fxgl.entity.background.carts;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class Trifort extends Component {
  private final Texture texture;

  public Trifort() {
    var bck = FXGL.image("backgrounds/carts/trifort/trifort.png", 850, 413);
    texture = new Texture(bck);
  }

  @Override
  public void onAdded() {
    texture.setTranslateY(-413 + 86);
    texture.setTranslateX(-60);
    entity.getViewComponent().addChild(texture);
  }
}
