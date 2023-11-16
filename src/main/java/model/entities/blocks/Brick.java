package model.entities.blocks;

import javafx.scene.image.Image;
import model.entities.Entity;
import ui.Sprites.Sprite;

import static ui.BombermanGame.block;
import static ui.BombermanGame.listKill;

public class Brick extends Entity {

  public Brick(int x, int y, Image img) {
    super(x, y, img);
  }

  private void checkHidden() {
    for (Entity entity : block) {
      if (entity instanceof Brick)
        if (listKill[entity.getX() / 32][entity.getY() / 32] == 4) {
          entity.setImg(Sprite.grass.getFxImage());
        }
    }
  }

  @Override
  public void update() {
    checkHidden();
  }
}
