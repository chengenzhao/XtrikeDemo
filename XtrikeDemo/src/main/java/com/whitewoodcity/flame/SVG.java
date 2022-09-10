package com.whitewoodcity.flame;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class SVG extends Region {
  public SVG(){
    minWidthProperty().bind(prefWidthProperty());
    maxWidthProperty().bind(prefWidthProperty());

    minHeightProperty().bind(prefHeightProperty());
    maxHeightProperty().bind(prefHeightProperty());
  }

  public SVG(double width, double height) {
    this();
    setPrefWidth(width);
    setPrefHeight(height);
  }

  public void setWidth(double width) {
    super.setWidth(width);
    if(!prefWidthProperty().isBound())
      prefWidthProperty().set(width);
  }

  public void setHeight(double height) {
    super.setHeight(height);
    if(!prefHeightProperty().isBound())
      prefHeightProperty().set(height);
  }

  public static SVG newSVG(String path, double width, double height, Paint paint) {
    var svgPath = new SVGPath();
    svgPath.setContent(path);
    final SVG svg = new SVG(width, height);
    svg.setShape(svgPath);
    svg.setFill(paint);
    return svg;
  }

  public static SVG newSVG(String path, Paint paint){
    var svgPath = new SVGPath();
    svgPath.setContent(path);
    final SVG svg = new SVG();
    svg.setShape(svgPath);
    svg.setFill(paint);
    return svg;
  }

  public void setFill(Paint paint){
    this.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
  }

}
