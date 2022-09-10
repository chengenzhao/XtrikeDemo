package com.whitewoodcity.xtrike.fxgl.entity;

import com.almasb.fxgl.dsl.components.LiftComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.whitewoodcity.xtrike.fxgl.entity.ammo.*;
import com.whitewoodcity.xtrike.fxgl.entity.background.Ground;
import com.whitewoodcity.xtrike.fxgl.entity.background.GroundFront;
import com.whitewoodcity.xtrike.fxgl.entity.background.Tree;
import com.whitewoodcity.xtrike.fxgl.entity.background.carts.*;
import com.whitewoodcity.xtrike.fxgl.entity.background.locomotives.Junior;
import com.whitewoodcity.xtrike.fxgl.entity.units.characters.Clancy;
import com.whitewoodcity.xtrike.fxgl.entity.units.characters.Tessa;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.whitewoodcity.xtrike.fxgl.gameapplication.ShootThemUp.gameHeight;
import static com.whitewoodcity.xtrike.fxgl.gameapplication.ShootThemUp.gameWidth;

public class Factory implements EntityFactory {
  @Spawns("Screen")
  public Entity screenBorder(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(0f));

    double w = gameWidth;
    double h = gameHeight;
    double thickness = 500.0;
    return entityBuilder().bbox(new HitBox(new Point2D(-thickness, 0.0), BoundingShape.box(thickness, h)))
        .bbox(new HitBox(new Point2D(w, 0.0), BoundingShape.box(thickness, h)))
        .bbox(new HitBox(new Point2D(0.0, -thickness), BoundingShape.box(w, thickness)))
        .bbox(new HitBox(new Point2D(0.0, h), BoundingShape.box(w, thickness)))
        .bbox(new HitBox(new Point2D(0.0, h-200), BoundingShape.box(80, 100)))
        .with(physics)
        .build();
  }
  @Spawns("Ground")
  public Entity ground(SpawnData data) {
    var physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(1));

    return entityBuilder()
        .at(0, 1000)
        .bbox(new HitBox(BoundingShape.box(gameWidth, 100)))
//      .view(new Rectangle(MyGameView.gameWidth, 100, Color.BLUE))
        .with(physics)
        .with(new Ground(data.<Integer>get("mission")))
        .zIndex(Integer.MIN_VALUE)
        .build();
  }
  @Spawns("GroundFront")
  public Entity groundFront(SpawnData data) {
    return entityBuilder()
        .at(0, 1000)
        .with(new GroundFront(data.<Integer>get("mission")))
        .zIndex(Integer.MAX_VALUE)
        .build();
  }
  @Spawns("Tree")
  public Entity tree(SpawnData data) {
    int mission = data.<Integer>get("mission");//Util.parseWithDefault(data.get("type"),0);
    var tree = new Tree(mission);
    return entityBuilder()
        .at(-tree.width, 1000)
        .with(tree)
        .zIndex(Integer.MIN_VALUE+1)
        .build();
  }

  @Spawns("Player")
  public Entity newPlayer(SpawnData data) {
    return Clancy.player(data);
  }

  @Spawns("China")
  public Entity china(SpawnData data){
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(1f));

    return entityBuilder()
        .at(data.getX(),data.getY())
        .type(Type.CART)
        .collidable()
        .bbox(new HitBox(BoundingShape.box(800, 90)))
//      .view(new Rectangle(800, 90, Color.RED))
        .with(physics)
        .with(new China())
        .zIndex(Integer.MIN_VALUE+2)
        .build();
  }
  @Spawns("ChinaFront")
  public Entity chinaFront(SpawnData data){
    return entityBuilder()
        .at(data.getX(),data.getY())
        .with(new ChinaFront())
        .zIndex(Integer.MAX_VALUE-1)
        .build();
  }
  @Spawns("Gustav")
  public Entity gustav(SpawnData data){
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(1f));

    return entityBuilder()
        .at(data.getX(),data.getY())
        .type(Type.CART)
        .collidable()
        .bbox(new HitBox(BoundingShape.box(800, 90)))
//      .view(new Rectangle(800, 90, Color.RED))
        .with(physics)
        .with(new Gustav())
        .zIndex(Integer.MIN_VALUE+2)
        .build();
  }
  @Spawns("Trifort")
  public Entity trifort(SpawnData data){
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(1f));

    return entityBuilder()
        .at(data.getX(),data.getY())
        .type(Type.CART)
        .collidable()
        .bbox(new HitBox(BoundingShape.box(800, 90)))
//      .view(new Rectangle(800, 90, Color.RED))
        .with(physics)
        .with(new Trifort())
        .zIndex(Integer.MIN_VALUE+2)
        .build();
  }
  @Spawns("MissileCart")
  public Entity missileCart(SpawnData data){
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(1f));

    return entityBuilder()
        .at(data.getX(),data.getY())
        .type(Type.CART)
        .collidable()
        .bbox(new HitBox(BoundingShape.box(800, 90)))
//      .view(new Rectangle(800, 90, Color.RED))
        .with(physics)
        .with(new MissileCart())
        .zIndex(Integer.MIN_VALUE+2)
        .build();
  }
  @Spawns("Bullet")
  public Entity newBullet(SpawnData data) {
    return Bullet.newBullet(data);
  }

  @Spawns("Locomotive")
  public Entity locomotive(SpawnData data) {
    var type = data.<Integer>get("type");//Util.parseWithDefault(data.get("type"),0);

    var loco = new Junior();

    var hitbox = new HitBox(BoundingShape.box(850, 290));

    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.STATIC);
    physics.setFixtureDef(new FixtureDef().friction(0.01f));

    return entityBuilder()
        .at(data.getX(),data.getY())
        .type(Type.LOCOMOTIVE)
        .collidable()
        .bbox(hitbox)
//      .view(new Rectangle(850, 290, Color.RED))
        .with(physics)
        .with(loco)
        .zIndex(Integer.MIN_VALUE+2)
        .build();
  }

  @Spawns("Tessa")
  public Entity tessa(SpawnData data){
    return Tessa.tessa(data);
  }
  @Spawns("prompt")
  public Entity prompt(SpawnData data) {
    var lift = new LiftComponent();
    lift.setGoingUp(true);
    lift.yAxisDistanceDuration(6, Duration.seconds(0.76));

    return entityBuilder(data)
      .with(lift)
      .zIndex(100)
      .build();
  }
}
