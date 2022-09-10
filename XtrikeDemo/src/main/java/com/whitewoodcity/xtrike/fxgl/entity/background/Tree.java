package com.whitewoodcity.xtrike.fxgl.entity.background;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import static com.whitewoodcity.xtrike.fxgl.gameapplication.ShootThemUp.gameWidth;

public class Tree extends Component {
  private final Image image;
  private final double ratio = 0.625;

  public final double width, height;

  public Tree(int mission) {
    int type = FXGL.random(0,1);
    String symbol = switch (mission){
      case 0,2 -> {
        type = FXGL.random(0,3);
        width = switch (type){
          case 1 -> 607;
          case 2 -> 596;
          case 3 -> 744;
          default -> 595;
        } * ratio;
        height = switch (type){
          case 1 -> 758;
          case 2 -> 928;
          case 3 -> 661;
          default -> 745;
        } * ratio;
        yield "day";
      }
      default ->  {
        width = switch (type){
          case 1 -> 706;
          default -> 619;
        } * ratio;
        height = switch (type){
          case 1 -> 858;
          default -> 745;
        } * ratio;
        yield "night";
      }
    };

    image = FXGL.image("backgrounds/trees/"+symbol+"/"+type+".png",width,height);

  }

  @Override
  public void onAdded() {
    var texture = new Texture(image);

    texture.setTranslateY(-height-250*ratio);
//    System.out.println(height);

    entity.getViewComponent().addChild(texture);
  }

//  double offsetX = -TREE_WIDTH;

  @Override
  public void onUpdate(double tpf) {
//    offsetX += 20;
    entity.setX(entity.getX() + 40*ratio);
    if(entity.getX() < -width - 10 || entity.getX() > gameWidth + width+10)
      entity.removeFromWorld();
  }
}