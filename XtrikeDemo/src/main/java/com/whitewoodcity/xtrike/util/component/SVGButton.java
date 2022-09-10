package com.whitewoodcity.xtrike.util.component;

import com.whitewoodcity.flame.SVG;
import com.whitewoodcity.flame.TranslucentStackPane;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SVGButton extends TranslucentStackPane<SVG> {

  public SVGButton(SVG svg) {
    super(svg,Color.rgb(255, 255, 255, 0.8), Color.rgb(255, 255, 255, 0.6));
  }

  public static SVGButton newSVGButton(String svgPath){
    return SVGButton.newSVGButton(svgPath, 1);
  }

  public static SVGButton newSVGButton(String svgPath, double widthToHeightRatio){
    return newSVGButton(svgPath, widthToHeightRatio, 1);
  }

  public static SVGButton newSVGButton(String svgPath, double widthToHeightRatio, double size){
    var svg = SVG.newSVG(svgPath, Color.DARKORANGE);
    var buttonPane = new SVGButton(svg);

    svg.prefHeightProperty().bind(buttonPane.heightProperty().multiply(0.5*size));
    svg.prefWidthProperty().bind(svg.prefHeightProperty().multiply(widthToHeightRatio));

    buttonPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> svg.setFill(Color.ORANGERED));
    buttonPane.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> svg.setFill(Color.DARKORANGE));
    buttonPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> svg.setFill(Color.ORANGE));
    buttonPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> svg.setFill(Color.DARKORANGE));

    return buttonPane;
  }

  public static SVGButton newSVGButton(String svgPath, Scene scene){
    return newSVGButton(svgPath, 1, scene, 0,0);
  }

  public static SVGButton newSVGButton(String svgPath, Scene scene, double bindX, double bindY){
    return newSVGButton(svgPath, 1, scene, bindX,bindY);
  }

  public static SVGButton newSVGButton(String svgPath, Scene scene, double bindX, double bindY, double svgSize){
    return newSVGButton(svgPath, 1, scene, bindX,bindY, svgSize);
  }

  public static SVGButton newSVGButton(String svgPath, double widthToHeightRatio, Scene scene, double bindX, double bindY){
    return newSVGButton(svgPath, widthToHeightRatio, scene, bindX, bindY, 1);
  }

  public static SVGButton newSVGButton(String svgPath, double widthToHeightRatio, Scene scene, double bindX, double bindY, double svgSize){
    var button = newSVGButton(svgPath, widthToHeightRatio, svgSize);
    button.prefWidthProperty().bind(scene.widthProperty().multiply(0.06));
    button.prefHeightProperty().bind(scene.heightProperty().multiply(0.06));
    button.layoutXProperty().bind(scene.widthProperty().multiply(bindX).subtract(button.prefWidthProperty().divide(2)));
    button.layoutYProperty().bind(scene.heightProperty().multiply(bindY).subtract(button.prefHeightProperty().divide(2)));
    return button;
  }
}
