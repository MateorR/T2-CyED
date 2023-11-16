package model.entities.blocks;

import javafx.scene.image.Image;
import model.entities.Entity;

public class Portal extends Entity {
  public static boolean isPortal = false;

  public Portal(int x, int y, Image img) {
    super(x, y, img);
  }

  @Override
  public void update() {
  }
}