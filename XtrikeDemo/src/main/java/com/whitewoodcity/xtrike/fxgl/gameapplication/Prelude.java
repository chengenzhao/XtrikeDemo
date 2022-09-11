package com.whitewoodcity.xtrike.fxgl.gameapplication;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.logging.Logger;
import com.whitewoodcity.xtrike.Main;
import com.whitewoodcity.xtrike.fxgl.GameMenu;
import com.whitewoodcity.xtrike.fxgl.Loading;
import com.whitewoodcity.xtrike.fxgl.entity.Factory;
import com.whitewoodcity.xtrike.fxgl.entity.units.characters.Clancy;
import com.whitewoodcity.xtrike.fxgl.handler.collisionhandler.PromptOnceCollisionHandler;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.Scanner;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.whitewoodcity.xtrike.fxgl.entity.Type.PLAYER;
import static com.whitewoodcity.xtrike.fxgl.entity.Type.TESSA;
import static com.whitewoodcity.xtrike.fxgl.input.InputUtil.onKey;

public class Prelude extends GameApplication implements PlotGameApplication, GetLoadingBackgroundFill {

  private static final Logger log = Logger.get(Prelude.class);
  public static double gameWidth = 4200, gameHeight = 1000.0;
  final private Stop[] stops = new Stop[]{new Stop(0, Color.web("bfd1df")), new Stop(0.5, Color.web("3a74a6")), new Stop(0.9, Color.web("010425"))};
  final private Paint fill = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
  private Rectangle dialogBackground, dialogFrame;
  private Loading loading;
  private Entity player;
  private boolean isTalking = false;
  private Duration standardDuration = Duration.seconds(.3);

  @Override
  protected void initSettings(GameSettings settings) {
    var screenWidth = Screen.getPrimary().getBounds().getWidth();
    var screenHeight = Screen.getPrimary().getBounds().getHeight();
    settings.setWidth((int) (screenWidth / screenHeight * 1000));
    settings.setHeight(1000);
    settings.setFontUI("BlackOpsOne-Regular.ttf");
    settings.setSceneFactory(new SceneFactory() {
      @Override
      public FXGLMenu newGameMenu() {
        return new GameMenu();
      }

      @Override
      public LoadingScene newLoadingScene() {
        loading = new Loading(fill);
        return loading;
      }
    });
  }

  @Override
  protected void initGame() {
    getGameWorld().addEntityFactory(new Factory());

    var screenWidth = Screen.getPrimary().getBounds().getWidth();
    var screenHeight = Screen.getPrimary().getBounds().getHeight();

    //background
    var bgImage = image("backgrounds/castle/mountain.png");
    var bgImageView = new ImageView(bgImage);

    var width = screenWidth / screenHeight * 1000;
    var height = 1000;

    bgImageView.setFitWidth(width);
    bgImageView.setFitHeight(height);
    Platform.runLater(() -> getGameScene().getContentRoot().getChildren().add(0, bgImageView));

    //dialog
    dialogBackground = new Rectangle(1, 1);
    dialogBackground.setFill(Color.web("030534aa"));//c2c3c9
    dialogBackground.setVisible(false);
    Platform.runLater(() -> addUINode(dialogBackground));

    dialogFrame = new Rectangle(1, 1);
    dialogFrame.setFill(Color.web("3978edcc"));//f2f2f2
    dialogFrame.setVisible(false);
    Platform.runLater(() -> addUINode(dialogFrame));

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
    spawn("Ground", new SpawnData().put("mission", 1));
    loading.setProgress(22, 100);
    spawn("GroundFront", new SpawnData().put("mission", 1));
    loading.setProgress(23, 100);
    spawn("Tree", new SpawnData().put("mission", 1));
    loading.setProgress(24, 100);

    spawn("Tessa", new SpawnData(1700, 600));
    loading.setProgress(25, 100);

    player = spawn("Player", new SpawnData(1000, 600));
    loading.setProgress(26, 100);

    var viewport = getGameScene().getViewport();
    viewport.setBounds(0, 0, (int) gameWidth, (int) gameHeight);
    viewport.bindToEntity(player, getAppWidth() / 10.0, 0);
    loading.setProgress(27, 100);

    var selfTalking = new ScriptsList().append(new Script("???", """
      Where am I? What happened?
      A train?
      There is a girl over there, may be she knows some thing.
      """));
    loading.setProgress(28, 100);

    runOnce(() -> showDialog(player.getX() - viewport.getX(), player.getY(), selfTalking), Duration.seconds(2));

    getAudioPlayer().loopMusic(getAssetLoader().loadMusic("train0.mp3"));
    new Transition() {
      {
        setCycleDuration(standardDuration);
      }

      protected void interpolate(double frac) {
        getSettings().setGlobalMusicVolume(frac * Main.soundVolume);
      }
    }.play();
    loading.setProgress(29, 100);

    getAssetLoader().loadMusic("text-blip.mp3");
    loading.setProgress(30,100);
  }

  @Override
  protected void initPhysics() {
    getPhysicsWorld().setGravity(0, 960);

    var promptOnceHandler = new PromptOnceCollisionHandler(PLAYER, TESSA,
      (player, tessa) -> showDialog(tessa.getRightX() - getGameScene().getViewport().getX(), tessa.getY(),
        new ScriptsList()
          .append(new Script("Girl", "???", """
            Oh, hello, new traveller! 
            Nice to meet you, what's your name?
            """))
          .append(new Script("???", "Girl", "Clancy,and you are?"))
          .append(new Script("Girl", "Clancy", "My name is Tessa."))
          .append(new Script("Clancy", "Tessa", "Hi."))
          .append(new Script("Tessa", "Clancy", "Hi."))
      ));
    var view = promptOnceHandler.getPromptView();
    view.textColorProperty().set(Color.WHITE);
    view.backgroundColorProperty().set(Color.web("3978edcc"));

    getPhysicsWorld().addCollisionHandler(promptOnceHandler);
  }

  @Override
  protected void initInput() {
    getGameScene().setCursor(Cursor.DEFAULT);

    onKey(() -> {
        if (player.isActive() && !isTalking) player.getComponent(Clancy.class).moveRight();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopMovingRight();
      }, KeyCode.D, KeyCode.RIGHT);

    onKey(() -> {
        if (player.isActive() && !isTalking) player.getComponent(Clancy.class).moveLeft();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopMovingLeft();
      }, KeyCode.A, KeyCode.LEFT);

    onKey(() -> {
      if (player.isActive() && !isTalking) player.getComponent(Clancy.class).jump();
    }, KeyCode.SPACE);

    onKey(() -> {
        if (player.isActive() && !isTalking) player.getComponent(Clancy.class).fire();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopFire();
      }, KeyCode.J, KeyCode.K, KeyCode.L,
      KeyCode.U, KeyCode.I, KeyCode.O, KeyCode.Y, KeyCode.H, KeyCode.N, KeyCode.M, KeyCode.P);

    onKey(() -> {
        if (player.isActive() && !isTalking) player.getComponent(Clancy.class).upward();
      },
      () -> {
        if (player.isActive()) player.getComponent(Clancy.class).stopUpward();
      }, KeyCode.W, KeyCode.UP);
  }

  @Override
  protected void onUpdate(double tpf) {
    if (FXGLMath.random(0, 500) == 0) {
      spawn("Tree", new SpawnData().put("mission", 1));
    }
  }

  @Override
  public Paint getLoadingBackgroundFill() {
    return fill;
  }

  public void showDialog(double x, double y, ScriptsList scripts) {
    if (isTalking) return;
    isTalking = true;

    var screenWidth = Screen.getPrimary().getBounds().getWidth();
    var screenHeight = Screen.getPrimary().getBounds().getHeight();
    double width = screenWidth / screenHeight * 1000;
    double height = 1000;

    var transition = new Transition() {
      {
        setCycleDuration(standardDuration);
        dialogBackground.setOpacity(1);
        dialogFrame.setOpacity(1);
        dialogBackground.setVisible(true);
        dialogFrame.setVisible(true);
      }

      @Override
      protected void interpolate(double frac) {
        dialogBackground.setWidth(width * frac);
        dialogBackground.setHeight(height / 3 * frac);
        dialogBackground.setTranslateX(-x * frac + x);
        dialogBackground.setTranslateY((height / 3 * 2 - y) * frac + y);
        dialogBackground.setArcWidth(50 * frac);
        dialogBackground.setArcHeight(50 * frac);

        dialogFrame.setWidth((width - height * .1 / 3) * frac);
        dialogFrame.setHeight(height / 3 * .9 * frac);
        dialogFrame.setTranslateX((height * .05 / 3 - x) * frac + x);
        dialogFrame.setTranslateY((height * 2.05 / 3 - y) * frac + y);
        dialogFrame.setArcHeight(50 * frac);
        dialogFrame.setArcWidth(50 * frac);
      }
    };
    transition.setOnFinished(e -> processScript(scripts, height, width));
    transition.play();
  }

  public void hideDialog() {
    isTalking = false;
    Timeline timeline = new Timeline(new KeyFrame(standardDuration,
      new KeyValue(dialogBackground.opacityProperty(), 0),
      new KeyValue(dialogFrame.opacityProperty(), 0)));
    timeline.setOnFinished(e -> {
      dialogBackground.setVisible(false);
      dialogFrame.setVisible(false);
    });
    timeline.play();
  }

  private void processScript(ScriptsList list, double height, double width) {
    if (list.size() > 0) {
      var script = list.remove(0);
      var from = switch (script.from().toLowerCase()) {
        case "tessa", "girl" -> "units/character/tessa/mug.png";
        default -> "units/character/clancy/mug.png";
      };

      var image = image(from);
      var view = new ImageView(image);
      view.setPreserveRatio(true);
      view.setFitHeight(image.getHeight() * .5);
      view.setLayoutY(height * 2 / 3 - view.getFitHeight());
      view.setOpacity(0);
      addUINode(view);

      var font = switch (script.from().toLowerCase()) {
        case "tessa", "girl" -> getAssetLoader().loadFont("Carattere-Regular.ttf").newFont(80);
        case "clancy" -> getAssetLoader().loadFont("PassionsConflict-Regular.ttf").newFont(80);
        default -> getAssetLoader().loadFont("Lato-Regular.ttf").newFont(50);
      };
      Dialog textFlow = new Dialog();
      textFlow.getName().setText(script.from());
      textFlow.getName().setFont(font);
      textFlow.setTranslateX(height * .1 / 3);
      textFlow.setTranslateY(height * 2.1 / 3);
      textFlow.setMaxWidth(width - height * .2 / 3);
      textFlow.setOpacity(0);
      addUINode(textFlow);

      final ImageView view0;
      if (script.to() != null) {
        var to = switch (script.to().toLowerCase()) {
          case "tessa", "girl" -> "units/character/tessa/mug.png";
          default -> "units/character/clancy/mug.png";
        };
        image = image(to);
        view0 = new ImageView(image);
        view0.setPreserveRatio(true);
        view0.setScaleX(-1);
        view0.setFitHeight(image.getHeight() * .5);
        view0.setFitWidth(image.getWidth() * .5);
        view0.setLayoutY(height * 2 / 3 - view0.getFitHeight());
        view0.setLayoutX(width - view0.getFitWidth());
        view0.setOpacity(0);
        addUINode(view0);
      } else {
        view0 = null;
      }

      var viewFt = new FadeTransition(standardDuration, view);
      viewFt.setToValue(1);
      var dialogFt = new FadeTransition(standardDuration, textFlow);
      dialogFt.setToValue(1);
      EventHandler<ActionEvent> onFinished = (e -> {
        var singleScript = script.script();

        processLineByLine(new Scanner(singleScript), textFlow.getText(), () -> {
          var ft = new FadeTransition(standardDuration, view);
          ft.setToValue(0);
          ft.setOnFinished(ee -> removeUINode(view));
          ft.play();

          ft = new FadeTransition(standardDuration, textFlow);
          ft.setToValue(0);
          ft.setOnFinished(ee -> removeUINode(textFlow));
          ft.play();

          if (view0 != null) {
            ft = new FadeTransition(standardDuration, view0);
            ft.setToValue(0);
            ft.setOnFinished(ee -> removeUINode(view0));
            ft.play();
          }

          processScript(list, height, width);
        });
      });

      var pt = new ParallelTransition(viewFt, dialogFt);
      if (view0 != null) {
        var ft = new FadeTransition(standardDuration, view0);
        ft.setToValue(1);
        pt.getChildren().add(ft);
      }
      pt.setOnFinished(onFinished);
      pt.play();
    } else {
      hideDialog();
    }
  }

  private void processLineByLine(Scanner scanner, Text text, Runnable setOnFinished) {
    if (scanner.hasNext()) {
      String line = scanner.nextLine();
      var blip = getAssetLoader().loadMusic("text-blip.mp3");
      blip.getAudio().setVolume(Main.soundVolume);
      getAudioPlayer().playMusic(blip);
      var font = getAssetLoader().loadFont("Lato-Regular.ttf").newFont(50);
      text.setFont(font);
      final Animation animation = new Transition() {
        {
          setCycleDuration(Duration.millis(line.length() * 1000 / 40));
        }

        protected void interpolate(double frac) {
          final int length = line.length();
          final int n = Math.round(length * (float) frac);
          text.setText(line.substring(0, n));
        }
      };

      animation.setOnFinished(e -> {
        getAudioPlayer().stopMusic(blip);
        getInput().addTriggerListener(new TriggerListener() {
          @Override
          protected void onActionBegin(Trigger trigger) {
            if (trigger instanceof KeyTrigger) {
              Platform.runLater(() -> {
                getInput().removeTriggerListener(this);
                processLineByLine(scanner, text, setOnFinished);
              });
            }
          }
        });
      });
      animation.play();
    } else {
      scanner.close();
      setOnFinished.run();
    }
  }
}
