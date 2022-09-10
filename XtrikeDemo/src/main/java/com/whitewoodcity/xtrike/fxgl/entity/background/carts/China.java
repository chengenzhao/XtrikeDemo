package com.whitewoodcity.xtrike.fxgl.entity.background.carts;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class China extends Component {
  private final Texture back;

  public China() {
    var bck = FXGL.image("backgrounds/carts/china/back.png", 806, 425);
    back = new Texture(bck);
  }

  @Override
  public void onAdded() {
    back.setTranslateY(-425 + 86);
    back.setTranslateX(-60);
    entity.getViewComponent().addChild(back);
  }
}

