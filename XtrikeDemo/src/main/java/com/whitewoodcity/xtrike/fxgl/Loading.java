package com.whitewoodcity.xtrike.fxgl;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.ui.FontFactory;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;

public class Loading extends LoadingScene {

  private Paint fill;

  public Loading(Paint fill){
    this.fill = fill;
  }

  FontFactory fontFactory = getAssetLoader().loadFont("BlackOpsOne-Regular.ttf");
  Text progressLabel;
  StackPane stackPane;
  int progress = 0;
  DoubleProperty progressProp = new SimpleDoubleProperty();

  @Override
  public void onCreate() {

    super.onCreate();
    this.setCursor(Cursor.DEFAULT);

    progress = 0;
    progressLabel = new Text(progress + "%");
    progressLabel.setFill(Color.WHITE);
    progressLabel.setTextAlignment(TextAlignment.CENTER);
    progressLabel.setFont(fontFactory.newFont(100));

    stackPane = new StackPane();
    stackPane.setAlignment(Pos.CENTER);
    stackPane.setPrefWidth(FXGL.getAppWidth());
    stackPane.setPrefHeight(FXGL.getAppHeight());
    stackPane.getChildren().add(progressLabel);

    this.getContentRoot().getChildren().addAll(stackPane);
  }

  @Override
  public void onDestroy() {
    progressProp.unbind();
    this.getContentRoot().getChildren().clear();
    super.onDestroy();
  }

  @Override
  public void onExitingTo(Scene nextState) {
    super.onExitingTo(nextState);

    var pane = new Pane();
    var bg = new Rectangle(0, 0, FXGL.getAppWidth(), FXGL.getAppHeight());

    bg.setFill(fill);

    var text = new Text("100%");
    text.setFill(Color.WHITE);
    text.setTextAlignment(TextAlignment.CENTER);
    text.setFont(fontFactory.newFont(100));
    var stackPane = new StackPane(text);
    stackPane.setAlignment(Pos.CENTER);
    stackPane.setPrefWidth(FXGL.getAppWidth());
    stackPane.setPrefHeight(FXGL.getAppHeight());

    pane.getChildren().addAll(bg, stackPane);

    nextState.getContentRoot().getChildren().add(pane);
    FadeTransition ft = new FadeTransition(Duration.millis(1000), pane);
    ft.setFromValue(1.0);
    ft.setToValue(.0);
    ft.play();
    ft.setOnFinished(e -> Platform.runLater(() -> nextState.getContentRoot().getChildren().remove(pane)));
  }

  @Override
  protected void onUpdate(double tpf) {
    super.onUpdate(tpf);

    if (progressProp.getValue() < 0 || progressProp.getValue() > 1) {
      progress++;
      if (progress > 100) progress = 100;
    } else {
      progress = (int) (progressProp.getValue() * 100);
    }

    progressLabel.setText(progress + "%");

  }

  public void setProgress(double progress, double max) {
    Platform.runLater(() -> progressProp.setValue(progress / max));
  }
}
