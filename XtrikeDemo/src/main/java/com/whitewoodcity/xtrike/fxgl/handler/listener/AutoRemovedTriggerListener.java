package com.whitewoodcity.xtrike.fxgl.handler.listener;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import javafx.application.Platform;

public class AutoRemovedTriggerListener extends TriggerListener {

  @FunctionalInterface
  public interface SetOnAction {
    boolean run(Trigger trigger);//return true to remove this trigger listener
  }

  SetOnAction onBegin, onAction, onEnd;

  public AutoRemovedTriggerListener(SetOnAction onBegin) {
    this.onBegin = onBegin;
  }

  public AutoRemovedTriggerListener(SetOnAction onBegin, SetOnAction onAction) {
    this.onBegin = onBegin;
    this.onAction = onAction;
  }

  public AutoRemovedTriggerListener(SetOnAction onBegin, SetOnAction onAction, SetOnAction onEnd) {
    this.onBegin = onBegin;
    this.onAction = onAction;
    this.onEnd = onEnd;
  }

  @Override
  protected void onActionBegin(Trigger trigger) {
    if (onBegin != null && onBegin.run(trigger))
      remove();
  }

  @Override
  protected void onAction(Trigger trigger) {
    if (onAction != null && onAction.run(trigger))
      remove();
  }

  @Override
  protected void onActionEnd(Trigger trigger) {
    if (onEnd != null && onEnd.run(trigger))
      remove();
  }

  public void remove() {
    Platform.runLater(() -> FXGL.getInput().removeTriggerListener(this));
  }
}
