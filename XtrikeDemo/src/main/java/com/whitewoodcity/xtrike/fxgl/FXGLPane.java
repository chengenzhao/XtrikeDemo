package com.whitewoodcity.xtrike.fxgl;

import com.almasb.fxgl.dsl.FXGL;
import com.whitewoodcity.flame.pushnpoppane.PushAndPopPane;
import com.whitewoodcity.xtrike.Main;
import com.whitewoodcity.xtrike.fxgl.gameapplication.BattleGameApplication;
import com.whitewoodcity.xtrike.fxgl.gameapplication.Prelude;
import com.whitewoodcity.xtrike.fxgl.gameapplication.ShootThemUp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Map;

public class FXGLPane extends PushAndPopPane {

  private final com.almasb.fxgl.app.FXGLPane fxglPane;

  public FXGLPane(Stage stage, Map<Object, Object> parameters, FXGLScene fxglScene) {
    super(stage, parameters);

    var game = switch (fxglScene){
      case PRELUDE -> new Prelude();
      default -> new ShootThemUp(parameters);
    };

    fxglPane = BattleGameApplication.embeddedLaunch(game);
    fxglPane.setRenderFill(game.getLoadingBackgroundFill());

    fxglPane.prefWidthProperty().bind(widthProperty());
    fxglPane.prefHeightProperty().bind(heightProperty());
    fxglPane.renderWidthProperty().bind(widthProperty());
    fxglPane.renderHeightProperty().bind(heightProperty());

    getChildren().add(fxglPane);
  }

  @Override
  public void dispose() {
    BattleGameApplication.embeddedShutdown();
    fxglPane.renderHeightProperty().unbind();
    fxglPane.renderWidthProperty().unbind();
    fxglPane.prefWidthProperty().unbind();
    fxglPane.prefHeightProperty().unbind();
    getChildren().clear();
  }

  @Override
  public void disable() {
    super.disable();

    FXGL.getSettings().setGlobalMusicVolume(Math.min(Main.musicVolume,
      Math.min(Main.soundVolume,FXGL.getSettings().getGlobalMusicVolume())));

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(900),
      new KeyValue(FXGL.getSettings().globalMusicVolumeProperty(), 0)));
    timeline.setOnFinished(e -> FXGL.getAudioPlayer().stopAllMusic());
    timeline.play();
  }

  public enum FXGLScene{
    PRELUDE, BATTLE
  }
}
