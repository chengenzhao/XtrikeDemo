package com.whitewoodcity.xtrike.fxgl.entity.units.characters;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import com.whitewoodcity.xtrike.fxgl.entity.components.FloatingComponent;
import com.whitewoodcity.xtrike.fxgl.entity.components.GroundComponent;
import javafx.scene.image.Image;

public class Tessa extends Component {

  private final Texture body;

  public Tessa() {
    Image bodyImg = FXGL.image("units/character/tessa/posture.png", 89.5, 170);

    body = new Texture(bodyImg);
  }

  @Override
  public void onAdded() {
//    physics.setBodyType(BodyType.DYNAMIC);
    entity.getViewComponent().addChild(body);
  }

  @Override
  public void onUpdate(double tpf) {

  }

  public static Entity tessa(SpawnData data) {

    return FXGL.entityBuilder()
      .at(data.getX(), data.getY())
      .type(Type.TESSA)
      .collidable()
      .bbox(new HitBox(BoundingShape.box(89, 170)))
      .with(new EffectComponent())
      .with(new FloatingComponent(0.6,0.05))
      .with(new Tessa())
      .zIndex(-89*170)
      .build();
  }
}
