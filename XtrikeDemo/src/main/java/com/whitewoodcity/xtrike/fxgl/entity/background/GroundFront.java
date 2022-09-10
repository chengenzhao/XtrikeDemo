package com.whitewoodcity.xtrike.fxgl.entity.background;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import static com.whitewoodcity.xtrike.fxgl.gameapplication.ShootThemUp.gameWidth;

public class GroundFront extends Component {
  private final Image image;
  private final double ratio = 0.625;
  private double height;
  private String symbol = "palace";

  public GroundFront(int mission) {
    height = switch (mission){
      case 1 -> {
        symbol = "castle";
        yield 276*ratio;
      }
      case 2 -> {
        symbol = "church";
        yield 259*ratio;
      }
      default -> 255*ratio;
    };
    image = FXGL.image("backgrounds/"+symbol+"/front.png", 2400*ratio, height);
  }

  @Override
  public void onAdded() {
    for (int i = -1; i < gameWidth / (2400*ratio); i++) {
      var texture = new Texture(image);
      texture.setTranslateY(-height);
      texture.setTranslateX(i * 2400*ratio);
      entity.getViewComponent().addChild(texture);
    }
  }

  int offsetX = 0;

  @Override
  public void onUpdate(double tpf) {
    offsetX += 40*ratio;
    offsetX = offsetX %((int)(2400*ratio));
    entity.setX(offsetX);
  }
}
