package com.whitewoodcity.xtrike.index;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitewoodcity.flame.SVG;
import com.whitewoodcity.xtrike.Main;
import com.whitewoodcity.flame.pushnpoppane.PushAndPopPane;
import com.whitewoodcity.flame.Navigator;
import com.whitewoodcity.xtrike.util.icons.rankbadge.CrossSwords;
import com.whitewoodcity.xtrike.util.icons.rankbadge.Petals;
import com.whitewoodcity.xtrike.util.icons.rankbadge.Stars;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class ConfigPane extends PushAndPopPane {
  public ConfigPane(Stage stage) {
    super(stage);
  }

  MediaPlayer musicPlayer, soundPlayer;

  public ConfigPane(Stage stage, Map<Object, Object> json) {
    super(stage, json);

    BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/index/bg.png", 1920, 1080, false, true),
      BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
      new BackgroundSize(1.0, 1.0, true, true, false, false));

    setBackground(new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));

    var vol = new Button();
    var svg = SVG.newSVG("M11.5,2.75 C11.5,2.22634895 12.0230228,1.86388952 12.5133347,2.04775015 L18.8913911,4.43943933 C20.1598961,4.91511241 21.0002742,6.1277638 21.0002742,7.48252202 L21.0002742,10.7513533 C21.0002742,11.2750044 20.4772513,11.6374638 19.9869395,11.4536032 L13,8.83332147 L13,17.5 C13,17.5545945 12.9941667,17.6078265 12.9830895,17.6591069 C12.9940859,17.7709636 13,17.884807 13,18 C13,20.2596863 10.7242052,22 8,22 C5.27579485,22 3,20.2596863 3,18 C3,15.7403137 5.27579485,14 8,14 C9.3521238,14 10.5937815,14.428727 11.5015337,15.1368931 L11.5,2.75 Z M8,15.5 C6.02978478,15.5 4.5,16.6698354 4.5,18 C4.5,19.3301646 6.02978478,20.5 8,20.5 C9.97021522,20.5 11.5,19.3301646 11.5,18 C11.5,16.6698354 9.97021522,15.5 8,15.5 Z M13,3.83223733 L13,7.23159672 L19.5002742,9.669116 L19.5002742,7.48252202 C19.5002742,6.75303682 19.0477629,6.10007069 18.3647217,5.84393903 L13,3.83223733 Z",
      Font.getDefault().getSize(), Font.getDefault().getSize(), Color.BLACK);
    vol.setGraphic(svg);

    Slider slider = new Slider();
    slider.setMin(0);
    slider.setMax(1);
    slider.setValue(Main.musicVolume);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.5);
    slider.setMinorTickCount(5);
    slider.setBlockIncrement(0.01);

    vol.setOnAction(event -> {
      if (musicPlayer != null) {
        musicPlayer.dispose();
      }
      try {
        var media = new Media(getClass().getResource("/assets/music/battle0.mp3").toExternalForm());
        musicPlayer = new MediaPlayer(media);
        musicPlayer.setVolume(slider.getValue());
        musicPlayer.play();
        musicPlayer.setOnEndOfMedia(() -> {
          musicPlayer.dispose();
          musicPlayer = null;
        });
      } catch (Exception e) {
        e.printStackTrace();
        musicPlayer = null;
      }
    });

    var hBox0 = new HBox();
    hBox0.setSpacing(10);
    hBox0.getChildren().addAll(vol, slider);

    vol = new Button();
    svg = SVG.newSVG("M308.971 657.987l150.28 165.279a16 16 0 0 0 11.838 5.236c8.837 0 16-7.163 16-16v-600.67a16 16 0 0 0-5.236-11.839c-6.538-5.944-16.657-5.463-22.602 1.075l-150.28 165.279A112 112 0 0 1 226.105 403H177c-17.673 0-32 14.327-32 32v154.333c0 17.674 14.327 32 32 32h49.105a112 112 0 0 1 82.866 36.654zM177 701.333c-61.856 0-112-50.144-112-112V435c0-61.856 50.144-112 112-112h49.105a32 32 0 0 0 23.676-10.472l150.28-165.28c35.668-39.227 96.383-42.113 135.61-6.445a96 96 0 0 1 31.418 71.028v600.671c0 53.02-42.98 96-96 96a96 96 0 0 1-71.029-31.417l-150.28-165.28a32 32 0 0 0-23.675-10.472H177z m456.058-348.336c-18.47-12.118-23.621-36.915-11.503-55.386 12.118-18.471 36.916-23.621 55.387-11.503C752.495 335.675 799 419.908 799 512c0 92.093-46.505 176.325-122.058 225.892-18.471 12.118-43.269 6.968-55.387-11.503-12.118-18.471-6.968-43.268 11.503-55.386C686.303 636.07 719 576.848 719 512c0-64.848-32.697-124.07-85.942-159.003z m92.93-137.323c-18.07-12.71-22.415-37.66-9.706-55.73s37.66-22.415 55.73-9.706C888.942 232.478 960 366.298 960 512s-71.058 279.522-187.988 361.762c-18.07 12.71-43.021 8.364-55.73-9.706-12.709-18.07-8.363-43.02 9.706-55.73C821.838 740.912 880 631.38 880 512c0-119.38-58.161-228.912-154.012-296.326z",
      37.33 * Font.getDefault().getSize() / 33.07, Font.getDefault().getSize(), Color.BLACK);
    vol.setGraphic(svg);

    var slider1 = new Slider();
    slider1.setMin(0);
    slider1.setMax(1);
    slider1.setValue(Main.soundVolume);
    slider1.setShowTickLabels(true);
    slider1.setShowTickMarks(true);
    slider1.setMajorTickUnit(0.5);
    slider1.setMinorTickCount(5);
    slider1.setBlockIncrement(0.01);

    vol.setOnAction(event -> {
      if (soundPlayer != null) {
        soundPlayer.dispose();
      }
      try {
        var media = new Media(getClass().getResource("/audio/finger.wav").toExternalForm());
        soundPlayer = new MediaPlayer(media);
        soundPlayer.setVolume(slider1.getValue());
        soundPlayer.play();
        soundPlayer.setOnEndOfMedia(() -> {
          soundPlayer.dispose();
          soundPlayer = null;
        });
      } catch (Exception e) {
        e.printStackTrace();
        soundPlayer = null;
      }
    });

    var polyline = new Polyline();
    polyline.getPoints().addAll(4.0, 13.0, 9.0, 18.0, 20.0, 7.0);
    polyline.setStrokeLineCap(StrokeLineCap.ROUND);
    polyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
    polyline.setStrokeWidth(2);
    var pane = new StackPane(polyline);
    pane.minWidthProperty().bind(pane.prefWidthProperty());
    pane.maxWidthProperty().bind(pane.prefWidthProperty());
    pane.minHeightProperty().bind(pane.prefHeightProperty());
    pane.maxHeightProperty().bind(pane.prefHeightProperty());
    pane.setPrefHeight(Font.getDefault().getSize());
    pane.setPrefWidth(17.414 / 12.414 * Font.getDefault().getSize());

    var bt = new Button();
    bt.setGraphic(pane);

    bt.setOnMouseClicked(e -> {
      Main.musicVolume = slider.getValue();
      Main.setSoundVolume(slider1.getValue());
      Main.fingerAudio();

      var file = new File("conf", "xtrike.config.json");
      try {
        if (file.exists()) file.delete();
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (file.createNewFile()) {
          var writer = new FileWriter(file);
          ObjectMapper mapper = new ObjectMapper();
          ObjectNode config = mapper.createObjectNode();
          config.put("musicVolume", Main.musicVolume);
          config.put("soundVolume", Main.soundVolume);
          writer.write(config.toString());
          writer.flush();
          writer.close();
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }

      if (musicPlayer != null)
        musicPlayer.dispose();
      if (soundPlayer != null)
        soundPlayer.dispose();
      Navigator.pop();
    });

    var hBox1 = new HBox();
    hBox1.setSpacing(10);
    hBox1.getChildren().addAll(vol, slider1, bt);

    var hbox1 = new HBox();
    hbox1.spacingProperty().bind(hBox0.spacingProperty());
    hbox1.getChildren().addAll(new Label("Java: " + System.getProperty("java.version")));

    var hbox2 = new HBox();
    hbox2.spacingProperty().bind(hBox0.spacingProperty());
    hbox2.getChildren().addAll(new Label("JavaFX: " + System.getProperty("javafx.runtime.version")));

    var vbox = new VBox();
    vbox.setPadding(new Insets(10));
    vbox.spacingProperty().bind(hBox0.spacingProperty());
    vbox.getChildren().addAll(hbox1, hbox2, hBox0, hBox1);

    var hb = new HBox();
    for (int i = 1; i < 6; i++) {
      var demo = new Stars(i);
      demo.heightProperty().bind(stage.getScene().heightProperty().multiply(0.2));
      hb.getChildren().add(demo);
    }

    var hb1 = new HBox();
    for (int i = 1; i < 6; i++) {
      var demo = new Petals(i);
      demo.heightProperty().bind(stage.getScene().heightProperty().multiply(0.2));
      hb1.getChildren().add(demo);
    }

    var demo = new CrossSwords();
    demo.heightProperty().bind(stage.getScene().heightProperty().multiply(0.2));

    vbox.getChildren().addAll(hb, hb1, demo);

    this.getChildren().addAll(vbox);
  }

}
