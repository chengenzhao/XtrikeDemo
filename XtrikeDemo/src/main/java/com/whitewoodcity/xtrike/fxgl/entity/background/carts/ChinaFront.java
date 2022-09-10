package com.whitewoodcity.xtrike.fxgl.entity.background.carts;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class ChinaFront extends Component {
  final private Texture front;

  public ChinaFront() {
    var fnt = FXGL.image("backgrounds/carts/china/front.png", 658, 49.5);
    front = new Texture(fnt);
  }

  @Override
  public void onAdded() {
    front.setTranslateX(70);
    front.setTranslateY(-46);
    entity.getViewComponent().addChild(front);
  }
}
