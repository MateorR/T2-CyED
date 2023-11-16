package model.entities.item;

import javafx.scene.image.Image;
import model.entities.Entity;
import model.entities.blocks.Bomb;
import ui.Sprites.Sprite;

import static ui.BombermanGame.*;

public class FlameItem extends Items {

  public FlameItem(int x, int y, Image img) {
    super(x, y, img);
  }

  public FlameItem(boolean received) {
    super(received);
  }

  public FlameItem() {
  }

  @Override
  public void update() {
    for (Entity entity : block)
      if (entity instanceof FlameItem && !this.received)
        if (listKill[entity.getX() / 32][entity.getY() / 32] == 4)
          entity.setImg(Sprite.powerup_flames.getFxImage());

    if (!this.received)
      if (player.getX() == this.getX() && player.getY() == this.getY()) {
        this.setImg(Sprite.grass.getFxImage());
        this.received = true;
        Bomb.powerBomb += 2;
      }
  }
}