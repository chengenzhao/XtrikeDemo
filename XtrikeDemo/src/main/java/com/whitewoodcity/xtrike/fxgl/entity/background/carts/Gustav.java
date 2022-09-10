package com.whitewoodcity.xtrike.fxgl.entity.background.carts;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class Gustav extends Component {
  private final Texture texture;

  public Gustav() {
    var bck = FXGL.image("backgrounds/carts/gustav/gustav.png", 1789.0/2, 895.0/2);
    texture = new Texture(bck);
  }

  @Override
  public void onAdded() {
    texture.setTranslateY(-447.5 + 86);
    texture.setTranslateX(-60);
    entity.getViewComponent().addChild(texture);
  }
}
