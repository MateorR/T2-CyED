package ui.Sprites;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

  private final String path;
  public final int SIZE;
  public int[] pixels;
  public BufferedImage image;

  public static SpriteSheet tiles = new SpriteSheet("/assets/classic.png", 256);

  public SpriteSheet(String path, int size) {
    this.path = path;
    SIZE = size;
    pixels = new int[SIZE * SIZE];
    load();
  }

  private void load() {
    try {
      URL spritesPath = SpriteSheet.class.getResource(path);
      assert spritesPath != null;
      image = ImageIO.read(spritesPath);
      int weight = image.getWidth();
      int height = image.getHeight();
      image.getRGB(0, 0, weight, height , pixels, 0, weight);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
