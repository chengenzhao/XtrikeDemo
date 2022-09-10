package com.whitewoodcity.xtrike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitewoodcity.flame.Language;
import com.whitewoodcity.flame.MainInterface;
import com.whitewoodcity.xtrike.fxgl.FXGLPane;
import com.whitewoodcity.xtrike.index.ConfigPane;
import com.whitewoodcity.xtrike.index.LogoPane;
import com.whitewoodcity.xtrike.util.Util;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application implements MainInterface {

  public static ObjectProperty<Language> language = new SimpleObjectProperty<>(Language.ENGLISH);
  public static double musicVolume = 0.5, soundVolume = 1.0;
  public static Map<String, AudioClip> audioClipMap = new HashMap<>();

  @Override
  public void start(Stage stage) {
    var config = new File("conf", "xtrike.config.json");
    if (config.exists()) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        var jsonNode = mapper.readTree(config);
        musicVolume = jsonNode.path("musicVolume").asDouble(0.5);
        setSoundVolume(jsonNode.path("soundVolume").asDouble(1.0));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    stage.setWidth(Screen.getPrimary().getBounds().getWidth() * 3 / 5);
    stage.setHeight(Screen.getPrimary().getBounds().getHeight() * 3 / 5);

    var scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Xtrike");

    routes
      .route("/", (json) -> new LogoPane(stage, json))
      .route("/config", (json) -> new ConfigPane(stage, json))
      .route("/prelude", (json) -> new FXGLPane(stage, json, FXGLPane.FXGLScene.PRELUDE))
      .route("/battlefxgl", (json) -> new FXGLPane(stage, json, FXGLPane.FXGLScene.BATTLE));
    root.getChildren().add(routes.initialRoute().getNode());
    stage.show();
    root.getChildren().get(0).requestFocus();
  }

  public static void main(String[] args) {
    System.setProperty("prism.lcdtext","false");
    Font.loadFont(Main.class.getResourceAsStream("/fonts/Ewert-Regular.ttf"), 12);//Font[name=Ewert, family=Ewert, style=Regular, size=12.0]
    Font.loadFont(Main.class.getResourceAsStream("/fonts/Girassol-Regular.ttf"), 12);//Font[name=Girassol Regular, family=Girassol, style=Regular, size=12.0]
    Font.loadFont(Main.class.getResourceAsStream("/fonts/Lato-Regular.ttf"), 12);//Font[name=Lato Regular, family=Lato, style=Regular, size=12.0]
    Font.loadFont(Main.class.getResourceAsStream("/fonts/Lato-Bold.ttf"), 12);//Font[name=Lato Bold, family=Lato, style=Regular, size=12.0]
    Main.audioClipMap.put("finger", Util.loadAudio("finger.wav"));
    Main.launch(Main.class, args);
  }

  public static void playAudio(String key) {
    if (audioClipMap.containsKey(key))
      audioClipMap.get(key).play();
  }

  public static void fingerAudio() {
    playAudio("finger");
  }

  public static void setSoundVolume(double volume) {
    soundVolume = volume;
    for (var audioClip : audioClipMap.values()) {
      audioClip.setVolume(soundVolume);
    }
  }

}