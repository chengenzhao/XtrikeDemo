package com.whitewoodcity.xtrike.fxgl.handler.collisionhandler;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.physics.CollisionHandler;
import com.whitewoodcity.xtrike.fxgl.entity.Type;
import com.whitewoodcity.xtrike.fxgl.entity.viewcomponent.PromptView;
import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PromptOnceCollisionHandler extends CollisionHandler {

  UserAction userAction;
  TriggerListener listener = new TriggerListener();
  PromptView promptView;

  public PromptOnceCollisionHandler(Type a, Type b, PromptView promptView, UserAction userAction) {
    super(a, b);
    this.promptView = promptView;
    this.userAction = userAction;
  }

  public PromptOnceCollisionHandler(Type a, Type b, UserAction userAction) {
    super(a, b);

    promptView = new PromptView("\u23CE ENTER", 30);
    promptView.getText().setEffect(new Bloom());
    promptView.setCache(true);
    promptView.setCacheHint(CacheHint.SCALE);

    this.userAction = userAction;
  }

  Entity prompt;

  @Override
  protected void onCollisionBegin(Entity player, Entity tessa) {
    prompt = getGameWorld().create("prompt", new SpawnData(tessa.getRightX(), tessa.getY()));
    prompt.getViewComponent().addChild(promptView);

    spawnWithScale(prompt, Duration.seconds(1), Interpolators.ELASTIC.EASE_OUT());
    listener.setOnTrigger(()->{
      Platform.runLater(()->this.onCollisionEnd(player,tessa));
      getPhysicsWorld().removeCollisionHandler(this);
      userAction.run(player, tessa);
    });
    getInput().addTriggerListener(listener);
  }

  @Override
  protected void onCollisionEnd(Entity player, Entity tessa) {
    if(prompt !=null)
      despawnWithScale(prompt, Duration.seconds(1), Interpolators.ELASTIC.EASE_IN());
    getInput().removeTriggerListener(listener);
  }

  public PromptView getPromptView() {
    return promptView;
  }

  public void setPromptView(PromptView promptView) {
    this.promptView = promptView;
  }

  @FunctionalInterface
  public interface UserAction{
    void run(Entity player, Entity tessa);
  }
}

class TriggerListener extends com.almasb.fxgl.input.TriggerListener{
  Runnable runnable;

  @Override
  protected void onActionBegin(Trigger trigger) {
    if(trigger instanceof KeyTrigger keyTrigger &&
      keyTrigger.getKey() == KeyCode.ENTER){
      if(runnable!=null) runnable.run();
    }
  }

  public void setOnTrigger(Runnable runnable){
    this.runnable = runnable;
  }
}
