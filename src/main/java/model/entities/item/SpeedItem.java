package model.entities.item;

import javafx.scene.image.Image;
import model.entities.Entity;
import ui.Sprites.Sprite;

import static ui.BombermanGame.*;

public class SpeedItem extends Items {
  public static int speed = 1;

  public SpeedItem(int x, int y, Image img) {
    super(x, y, img);
  }

  public SpeedItem() {
  }

  public SpeedItem(boolean received) {
    super(received);
  }

  @Override
  public void update() {
    for (Entity entity : block)
      if (entity instanceof SpeedItem && !this.received)
        if (listKill[entity.getX() / 32][entity.getY() / 32] == 4)
          entity.setImg(Sprite.powerup_speed.getFxImage());

    if (!this.received)
      if (player.getX() == this.getX() && player.getY() == this.getY()) {
        this.setImg(Sprite.grass.getFxImage());
        this.received = true;
        speed = 2;
      }
  }
}