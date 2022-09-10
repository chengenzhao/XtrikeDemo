package com.whitewoodcity.xtrike.fxgl.entity.background;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import static com.whitewoodcity.xtrike.fxgl.gameapplication.ShootThemUp.gameWidth;

public class Ground extends Component {
  private final Image image;
  private final double ratio = 0.625;

  public Ground(int mission) {
    String symbol = switch (mission){
      case 1 -> "castle";
      case 2 -> "church";
      default -> "palace";
    };
    image = FXGL.image("backgrounds/"+symbol+"/back.png", 2400*ratio, 800*ratio);
  }

  @Override
  public void onAdded() {
    for(int i = -1; i< gameWidth /(2400*ratio); i++){
      var texture = new Texture(image);
      texture.setTranslateY(-800*ratio);
      texture.setTranslateX(i*(2400*ratio));
      entity.getViewComponent().addChild(texture);
    }
  }

  int offsetX = 0;
  @Override
  public void onUpdate(double tpf) {
    offsetX+=40*ratio;
    offsetX = offsetX%((int)(2400*ratio));
    entity.setX(offsetX);
  }
}
