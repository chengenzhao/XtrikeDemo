package com.whitewoodcity.xtrike.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitewoodcity.xtrike.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;

public class Util {

  public static Image loadOriginalImage(String imageUrl) {
    return new Image(imageUrl);
  }

  public static Image loadOriginalImage(String imageUrl, int x, int y, int w, int h) {
    Image image = loadOriginalImage(imageUrl);
    WritableImage writableImage = new WritableImage(w, h);
    PixelWriter pixelWriter = writableImage.getPixelWriter();

    // Determine the color of each pixel in a specified row
    for (int readY = 0; readY < writableImage.getHeight(); readY++) {
      for (int readX = 0; readX < writableImage.getWidth(); readX++) {
        Color color = image.getPixelReader().getColor(x + readX, y + readY);
        pixelWriter.setColor(readX, readY, color);
      }
    }

    return writableImage;
  }

  private static Rotate rotate = new Rotate();

  /**
   * Draws an image on a graphics context.
   * <p>
   * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
   * (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
   *
   * @param gc    the graphics context the image is to be drawn on.
   * @param angle the angle of rotation.
   * @param tlx   the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
   * @param tly   the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
   * @param px    the x pivot co-ordinate for the rotation (in canvas co-ordinates).
   * @param py    the y pivot co-ordinate for the rotation (in canvas co-ordinates).
   */
  public static void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlx, double tly, double px, double py) {
    drawRotatedImage(gc, image, rotate, angle, tlx, tly, px, py, image.getWidth(), image.getHeight());
  }

  public static void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlx, double tly, double px, double py, double width, double height) {
    drawRotatedImage(gc, image, rotate, angle, tlx, tly, px, py, width, height);
  }

  public static void drawRotatedImage(GraphicsContext gc, Image image, Rotate rotate, double angle, double tlx, double tly, double px, double py, double width, double height) {
    gc.save(); // saves the current state on stack, including the current transform
    rotate.setAngle(angle);
    rotate.setPivotX(px);
    rotate.setPivotY(py);
    gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
    gc.drawImage(image, tlx, tly, width, height);
    gc.restore(); // back to original state (before rotation)
  }

  public static String md5(String string) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(string.getBytes());
      String hex = new BigInteger(1, digest.digest()).toString(16);
      // 补齐BigInteger省略的前置0
      return new String(new char[32 - hex.length()]).replace("\0", "0") + hex;
    } catch (Exception e) {
      e.printStackTrace();
      return string;
    }
  }

  public static Map<String, String> loadConfig(String configFileName) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = Main.class.getResourceAsStream("/config/" + configFileName + "/" + Main.language.getValue().toString() + ".json");
    return mapper.readValue(is, Map.class);
  }

  public static int parseWithDefault(Object o, int def) {
    try {
      return Integer.parseInt(o.toString());
    }
    catch (NumberFormatException e) {
      // It's OK to ignore "e" here because returning a default value is the documented behaviour on invalid input.
      return def;
    }
  }

  public static AudioClip loadAudio(String fileName){
    return new AudioClip(Main.class.getResource("/audio/"+fileName).toExternalForm());
  }
}
