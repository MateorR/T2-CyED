package model.entities.animal;

import javafx.scene.image.Image;
import ui.control.Move;
import ui.Sprites.Sprite;

import static ui.BombermanGame.enemy;
import static ui.BombermanGame.width;

public class Kondoria extends Animal {

  private static int swapKill = 1;
  private static int countKill = 0;
  private static boolean dir;

  public Kondoria(int x, int y, Image img) {
    super(x, y, img);
  }

  public Kondoria(int isMove, int swap, String direction, int count, int countToRun) {
    super(4, 1, "up", 0, 0);
  }

  public Kondoria(boolean life) {
    super(life);
  }

  public Kondoria() {
  }

  private void killKondoria(Animal animal) {
    if (countKill % 16 == 0) {
      if (swapKill == 1) {
        animal.setImg(Sprite.kondoria_dead.getFxImage());
        swapKill = 2;
      } else if (swapKill == 2) {
        animal.setImg(Sprite.player_dead3.getFxImage());
        swapKill = 3;
      } else {
        animal.setLife(false);
        enemy.remove(animal);
        swapKill = 1;
      }
    }
  }

  @Override
  public void update() {
    countKill++;
    for (Animal animal : enemy) {
      if (animal instanceof Kondoria && !animal.life)
        killKondoria(animal);
    }

    if (this.getY() % 16 == 0 && this.getX() % 16 == 0) {
      if (this.getX() / 32 <= 1 || this.getX() / 32 >= width - 2)
        dir = !dir;

      if (dir)
        Move.left(this);
      else
        Move.right(this);
    }
  }
}
