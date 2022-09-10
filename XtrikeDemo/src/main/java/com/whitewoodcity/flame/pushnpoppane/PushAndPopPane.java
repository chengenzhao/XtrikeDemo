package com.whitewoodcity.flame.pushnpoppane;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;

public non-sealed class PushAndPopPane extends Pane implements PushAndPop {

  private Map<Object, Object> parameters;
  protected Stage stage;

  public PushAndPopPane(Stage stage) {
    this.stage = stage;
    prefWidthProperty().bind(stage.getScene().widthProperty());
    prefHeightProperty().bind(stage.getScene().heightProperty());
  }

  public PushAndPopPane(Stage stage, Map<Object, Object> parameters) {
    this(stage);
    this.parameters = parameters;
  }

  public void disable(){
    this.setDisable(true);
  }

  public void enable(){
    this.setDisable(false);
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
