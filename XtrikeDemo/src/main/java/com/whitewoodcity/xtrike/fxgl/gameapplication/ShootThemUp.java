package com.whitewoodcity.xtrike.fxgl.gameapplication;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.ui.ProgressBar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitewoodcity.flame.Navigator;
import com.whitewoodcity.xtrike.Main;
import com.whitewoodcity.xtrike.fxgl.GameMenu;
import com.whitewoodcity.xtrike.fxgl.Loading;
import com.whitewoodcity.xtrike.fxgl.entity.Factory;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import com.whitewoodcity.xtrike.fxgl.handler.collisionhandler.BulletCollisionHandler;
import com.whitewoodcity.xtrike.fxgl.handler.collisionhandler.SmallExplodableCollisionHandler;
import com.whitewoodcity.xtrike.fxgl.handler.collisionhandler.ThroughCollisionHandler;
import com.whitewoodcity.xtrike.fxgl.entity.units.characters.Clancy;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.whitewoodcity.xtrike.util.Util.loadConfig;
import static com.whitewoodcity.xtrike.fxgl.input.InputUtil.onKey;
import static java.lang.Math.max;

public class ShootThemUp extends BattleGameApplication implements GetLoadingBackgroundFill{

  private static final Logger log = Logger.get(ShootThemUp.class);

  private final Map<Object, Object> parameters;

  public ShootThemUp(Map<Object, Object> parameters) {
    this.parameters = parameters;
  }

  public Map<Object, Object> getParameters() {
    return parameters;
  }

  private int getMission() {
    var mission = parameters.get("mission");
    mission = mission == null ? "0" : mission;
    return Integer.parseInt(mission.toString());
  }

  public static double gameWidth = 4200, gameHeight = 1000.0;
  private Loading loading;

  private Stop[] stops = new Stop[]{new Stop(0, Color.web("3372b5")), new Stop(0.5, Color.web("b3e7fa")), new Stop(0.9, Color.web("a4c9bd"))};
  private Paint fill = new LinearGradient(0, 0, 1, 1, true,CycleMethod.NO_CYCLE, stops);

  @Override
  protected void initSettings(GameSettings settings) {
    var screenWidth = Screen.getPrimary().getBounds().getWidth();
    var screenHeight = Screen.getPrimary().getBounds().getHeight();
    settings.setWidth((int) (screenWidth / screenHeight * 1000));
    settings.setHeight(1000);
    settings.setFontUI("high_level_black.ttf");
    settings.setSceneFactory(new SceneFactory() {
      @Override
      public FXGLMenu newGameMenu() {
        var menu = new GameMenu();

        try {
          var menuStrings = loadConfig("fxgl");
          menu.setMenuStrings(menuStrings);
        } catch (Exception e) {
          log.fatal(e.getMessage(), e);
        }

        return menu;
      }

      @Override
      public LoadingScene newLoadingScene() {
        loading = new Loading(fill);
        return loading;
      }
    });
  }

  @Override
  protected void initInput() {
    getGameScene().setCursor(Cursor.DEFAULT);

    onKey(() -> {
        if (player.isActive()) player.getComponent(Clancy.class).moveRight();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopMovingRight();
      }, KeyCode.D, KeyCode.RIGHT);

    onKey(() -> {
        if (player.isActive()) player.getComponent(Clancy.class).moveLeft();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopMovingLeft();
      }, KeyCode.A, KeyCode.LEFT);

    onKey(() -> {
      if (player.isActive()) player.getComponent(Clancy.class).jump();
    }, KeyCode.SPACE);

    onKey(() -> {
        if (player.isActive()) player.getComponent(Clancy.class).fire();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopFire();
      }, KeyCode.J, KeyCode.K, KeyCode.L,
      KeyCode.U, KeyCode.I, KeyCode.O, KeyCode.Y, KeyCode.H, KeyCode.N, KeyCode.M, KeyCode.P);

    onKey(() -> {
        if (player.isActive()) player.getComponent(Clancy.class).upward();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopUpward();
      }, KeyCode.W, KeyCode.UP);

  }

  private Entity player;
  private Music bgm, whiteNoise;

  @Override
  protected void initGame() {
    getGameWorld().addEntityFactory(new Factory());

    getGameScene().getContentRoot().getChildren().add(0, getBackground());

    loading.setProgress(10, 100);
    player = spawn("Player", 1000, 0);
    loading.setProgress(11, 100);

    loading.setProgress(14, 100);
    spawn("China", 3400, 775);
    loading.setProgress(15, 100);
    spawn("ChinaFront", 3400, 775);
    loading.setProgress(16, 100);
    spawn("MissileCart", 2570, 775);
    loading.setProgress(17, 100);
    spawn("Trifort", 1770, 775);
    loading.setProgress(18, 100);
    spawn("Gustav", 930, 775);
    loading.setProgress(19, 100);
    spawn("Locomotive", new SpawnData(100, 563).put("type", 1));
    loading.setProgress(20, 100);

    spawn("Screen");
    loading.setProgress(21, 100);
    spawn("Ground", new SpawnData().put("mission", getMission()));
    loading.setProgress(22, 100);
    spawn("GroundFront", new SpawnData().put("mission", getMission()));
    loading.setProgress(23, 100);
    spawn("Tree", new SpawnData().put("mission", getMission()));
    loading.setProgress(24, 100);

    loading.setProgress(25, 100);
    var boss = switch (getMission()) {
      case 0 -> spawn("Chinook", gameWidth - 800, 50);
      case 1 -> spawn("Siegfried", gameWidth - 800, 300);
      default -> spawn("Latv", gameWidth - 450, 570);
    };
    loading.setProgress(26, 100);
    try {
      getSettings().setGlobalMusicVolume(1);
      bgm = FXGL.getAssetLoader().loadMusic("battle0.mp3");
      whiteNoise = FXGL.getAssetLoader().loadMusic("train0.mp3");
      if(Main.musicVolume > .01) FXGL.getAudioPlayer().loopMusic(bgm);
      if(Main.soundVolume > .01) FXGL.getAudioPlayer().loopMusic(whiteNoise);
      final Animation animation = new Transition() {
        {
          setCycleDuration(Duration.seconds(1));
        }
        protected void interpolate(double frac) {
          bgm.getAudio().setVolume(Main.musicVolume * frac);
          whiteNoise.getAudio().setVolume(Main.soundVolume * frac);
        }
      };
      animation.play();
    } catch (Throwable e) {
      e.printStackTrace();
      log.fatal(e.getMessage(), e.fillInStackTrace());
    }
    loading.setProgress(30, 100);
    var viewport = getGameScene().getViewport();
    viewport.setBounds(0, 0, (int) gameWidth, (int) gameHeight);
    viewport.bindToEntity(player, getAppWidth() / 10.0, 0);

    player.setOnNotActive(() -> conclude(false));
    boss.setOnNotActive(() -> conclude(true));

    //show hp bar
    var hpBar = new ProgressBar();
    hpBar.setHeight(25);
    hpBar.setFill(Color.GREEN.brighter());
    hpBar.setTraceFill(Color.TRANSPARENT);
    hpBar.setLayoutX(50);
    hpBar.setLayoutY(50);
    hpBar.setBackgroundFill(Color.rgb(0, 0, 0, 0.5));

//    GenericBarViewComponent healthBar = new GenericBarViewComponent( 50, 50, Color.GREEN, 0.0, player.getComponent(HealthIntComponent.class).getMaxValue());
//    healthBar.valueProperty().bind(player.getComponent(HealthIntComponent.class).valueProperty());

    hpBar.maxValueProperty().bind(player.getComponent(HealthIntComponent.class).maxValueProperty());
    hpBar.currentValueProperty().bind(player.getComponent(HealthIntComponent.class).valueProperty());

    addUINode(hpBar);
  }

  @Override
  protected void initPhysics() {
    getPhysicsWorld().setGravity(0, 960);
    var bulletCollisionHandler = new BulletCollisionHandler(Type.TANK_M7);
    getPhysicsWorld().addCollisionHandler(bulletCollisionHandler);
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.HELICOPTER_APACHE));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.LAUNCHER));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.F3));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.LATV));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.BATTLESHIP_SIEGFRIED));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.BATTLESHIP_YAMATO));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.BATTLESHIP_FENRIR));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.BATTLESHIP_BRYNHILD));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.HELICOPTER_CHINOOK));
    getPhysicsWorld().addCollisionHandler(new BulletCollisionHandler(Type.SPACE_MARINE));
    getPhysicsWorld().addCollisionHandler(bulletCollisionHandler.copyFor(Type.PLAYER, Type.CHINOOK_BULLET));
    getPhysicsWorld().addCollisionHandler(bulletCollisionHandler.copyFor(Type.PLAYER, Type.SPACE_MARINE_BULLET));
    var smallExplodableCollisionHandler = new SmallExplodableCollisionHandler(Type.PLAYER, Type.TANK_M7_SHELL);
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler);
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.CART, Type.BATTLESHIP_YAMATO_SHELL));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.LOCOMOTIVE, Type.TANK_M7_SHELL));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.LOCOMOTIVE, Type.BATTLESHIP_YAMATO_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.PLAYER, Type.HELICOPTER_APACHE_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.LOCOMOTIVE, Type.HELICOPTER_APACHE_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.CART, Type.HELICOPTER_APACHE_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.PLAYER, Type.F3_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.LOCOMOTIVE, Type.F3_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.PLAYER, Type.LAUNCHER_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.LOCOMOTIVE, Type.LAUNCHER_MISSILE));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.LOCOMOTIVE, Type.BATTLESHIP_YAMATO_SHELL));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.PLAYER, Type.BATTLESHIP_YAMATO_SHELL));
    getPhysicsWorld().addCollisionHandler(smallExplodableCollisionHandler.copyFor(Type.PLAYER, Type.BATTLESHIP_YAMATO_MISSILE));
    getPhysicsWorld().addCollisionHandler(bulletCollisionHandler.copyFor(Type.CART, Type.LASER_BEAM));
    var throughHandler = new ThroughCollisionHandler(Type.PLAYER, Type.LASER_BEAM);
    getPhysicsWorld().addCollisionHandler(throughHandler);
  }

  @Override
  protected void onUpdate(double tpf) {
    if (FXGLMath.random(0, 500) == 0) {
      spawn("Tree", new SpawnData().put("mission", getMission()));
      switch (FXGLMath.random(0, 20)) {
        case 0, 1 -> spawn("TankM7", gameWidth - 300, 500);
        case 2, 3 -> spawn("Apache", gameWidth - 300, 100);
        case 4, 5 -> spawn("Launcher", gameWidth - 300, 500);
        case 6 -> spawn("Yamato", gameWidth - 300, 400);
        case 7, 8 -> spawn("SpaceMarine", gameWidth - 300, 500);
        default -> spawn("F3", gameWidth - 200, player.getY() - 400);
      }
    }
  }

  @Override
  public double getPlayerX() {
    if (player == null || !player.isActive())
      return 0;
    else
      return player.getX() + player.getWidth() / 2;
  }

  @Override
  public double getPlayerY() {
    if (player == null || !player.isActive())
      return 500;
    else
      return player.getY() + player.getHeight() / 2;
  }

  private boolean concluded = false;

  private void conclude(boolean win) {
    if (concluded) return;
    concluded = true;

    final StackPane resultPane = new StackPane();

    resultPane.setAlignment(Pos.CENTER);
    resultPane.setPrefWidth(getAppWidth());
    resultPane.setPrefHeight(getAppHeight());
    var rect = new Rectangle();
    rect.setWidth(getAppWidth());
    rect.setHeight(getAppHeight());
    rect.setFill(Color.TRANSPARENT);

    var length = Duration.seconds(1);

    FillTransition fillTransition = new FillTransition(length);
    fillTransition.setToValue(Color.rgb(0, 0, 0, 0.7));
    fillTransition.setShape(rect);
    fillTransition.setOnFinished(e -> {
      var result = image("backgrounds/" + (win ? "victory.png" : "defeat.png"));
      var view = new ImageView(result);
      view.effectProperty().bind(
        Bindings.when(view.hoverProperty())
          .then((win) ? new Glow() : new InnerShadow())
          .otherwise(new Glow(0))
      );
      view.setOnMouseClicked(event -> {
        if (win) {
          var progress = parameters.get("progress") == null ? 0 : Integer.parseInt(parameters.get("progress").toString());
          parameters.put("progress", max(progress, getMission() + 1));

          if (parameters.containsKey("money")) {
            try {
              var money = Double.parseDouble(parameters.get("money").toString());
              parameters.put("money", (int) (money + 200));
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          } else {
            parameters.put("money", 200);
          }

          //auto save
          new Thread(() -> {
            var file = new File("saves", "autosave.sav");
            try {
              if (file.exists()) file.delete();
              if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
              if (file.createNewFile()) {
                var writer = new FileWriter(file);
                var map = parameters;
                map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                writer.write(new ObjectMapper().writeValueAsString(map));
                writer.flush();
                writer.close();
              }
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          }).start();
        }

        getInput().setRegisterInput(false);
        Navigator.replaceNamed("/map", parameters);
      });
      view.setPreserveRatio(true);
      view.setFitWidth(500);
      resultPane.getChildren().addAll(view);
    });
    resultPane.getChildren().addAll(rect);
    fillTransition.play();

    final Animation animation = new Transition() {
      {
        setCycleDuration(Duration.seconds(1));
      }
      protected void interpolate(double frac) {
        bgm.getAudio().setVolume(Main.musicVolume * (1 - frac));
      }
    };
    animation.setOnFinished(e -> {
      bgm.getAudio().stop();
      Music music = getAssetLoader().loadMusic(win ? "victory.wav" : "lose.wav");
      getAudioPlayer().playMusic(music);
      music.getAudio().setVolume(Main.musicVolume);
    });
    animation.play();
    getInput().setRegisterInput(false);

    addUINode(resultPane);
  }

  private Node getBackground() {
    var screenWidth = Screen.getPrimary().getBounds().getWidth();
    var screenHeight = Screen.getPrimary().getBounds().getHeight();

    var symbol = switch (getMission()) {
      case 1 -> "castle";
      case 2 -> "church";
      default -> "palace";
    };
    var bgImage = FXGL.image("backgrounds/" + symbol + "/mountain.png");

    var canvas = new Canvas();

    var width = (int) (screenWidth / screenHeight * 1000);
    var height = 1000;

    canvas.setWidth(width);
    canvas.setHeight(height);

    var gc = canvas.getGraphicsContext2D();

    gc.drawImage(bgImage, 0, 0, width, height);

    return canvas;
  }

  @Override
  public Paint getLoadingBackgroundFill() {
    return fill;
  }
}
