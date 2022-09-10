package com.whitewoodcity.flame.pushnpoppane;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Map;

public non-sealed class PushAndPopStackPane extends StackPane implements PushAndPop {
  public void disable(){
    this.setDisable(true);
  }

  public void enable(){
    this.setDisable(false);
  }

  private Map<Object, Object> parameters;
  protected Stage stage;

  public PushAndPopStackPane(Stage stage) {
    this.stage = stage;
    prefWidthProperty().bind(stage.getScene().widthProperty());
    prefHeightProperty().bind(stage.getScene().heightProperty());
  }

  public PushAndPopStackPane(Stage stage, Map<Object, Object> parameters) {
    this(stage);
    this.parameters = parameters;
  }

  public void dispose(){
    prefWidthProperty().unbind();
    prefHeightProperty().unbind();
  }

  public Map<Object, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<Object, Object> parameters) {
    this.parameters = parameters;
  }
}
