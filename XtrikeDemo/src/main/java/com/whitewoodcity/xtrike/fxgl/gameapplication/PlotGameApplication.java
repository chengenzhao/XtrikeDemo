package com.whitewoodcity.xtrike.fxgl.gameapplication;

import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.LinkedList;
import java.util.Objects;

public interface PlotGameApplication {
  record Script(String from, String to, String script){
    public Script{
      Objects.requireNonNull(from);
      Objects.requireNonNull(script);
    }

    public Script(String from, String script){
      this(from, null, script);
    }
  }

  class ScriptsList extends LinkedList<Script>{
    public ScriptsList append(Script script){
      this.add(script);
      return this;
    }
  }

  class Dialog extends TextFlow{
    private Text name = new Text();
    private Text text = new Text();

    public Dialog(){
      name.setFill(Color.WHITE);
      name.setEffect(new Bloom());

      text.setFill(Color.WHITE);
      text.setEffect(new Bloom());

      this.getChildren().addAll(name,new Text(System.lineSeparator()), text);
    }

    public Text getName() {
      return name;
    }

    public Text getText() {
      return text;
    }
  }
}
