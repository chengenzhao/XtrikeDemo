package com.whitewoodcity.xtrike.fxgl.input;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

public class InputUtil {
  public static void onKey(Entity nullableEntity, Runnable keyDownFunc, Runnable keyUpFunc, KeyCode... keyCodes){
    for(var keyCode:keyCodes){
      FXGL.getInput().addAction(new UserAction(keyCode.name()) {
        @Override
        protected void onActionBegin() {
          if(nullableEntity!=null)
            keyDownFunc.run();
        }

        @Override
        protected void onActionEnd() {
          if(nullableEntity!=null)
            keyUpFunc.run();
        }
      }, keyCode);
    }
  }

  public static void onKey(Entity nullableEntity,Runnable keyDownFunc, KeyCode... keyCodes){
    onKey(nullableEntity,keyDownFunc,()->{},keyCodes);
  }

  public static void onKey(Runnable keyDownFunc, Runnable keyUpFunc, KeyCode... keyCodes){
    for(var keyCode:keyCodes){

      FXGL.getInput().addAction(new UserAction(keyCode.name()) {
        @Override
        protected void onActionBegin() {
          keyDownFunc.run();
        }

        @Override
        protected void onActionEnd() {
          keyUpFunc.run();
        }
      }, keyCode);
    }
  }

  public static void onKey(Runnable keyDownFunc, KeyCode... keyCodes){
    onKey(keyDownFunc,()->{},keyCodes);
  }
}