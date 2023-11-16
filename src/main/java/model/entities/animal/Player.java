package model.entities.animal;


import javafx.scene.image.Image;
import ui.Sprites.Sprite;
import ui.control.Menu;

import static ui.BombermanGame.authorView;
import static ui.BombermanGame.enemy;
import static ui.BombermanGame.listKill;
import static ui.BombermanGame.player;
import static ui.BombermanGame.running;

public class Player extends Animal {
  public static int swapKill = 1;
  private static int countKill = 0;

  public Player(int isMove, int swap, String direction, int count, int countToRun) {
    super(8, 1, "down", 0, 0);
  }

  public Player() {
  }

  public Player(int x, int y, Image img) {
    super(x, y, img);
  }

  private void killBomber(Animal animal) {
    if (countKill % 16 == 0) {
      if (swapKill == 1) {
        animal.setImg(Sprite.player_dead1.getFxImage());
        swapKill = 2;
      } else if (swapKill == 2) {
        animal.setImg(Sprite.player_dead2.getFxImage());
        swapKill = 3;
      } else if (swapKill == 3) {
        animal.setImg(Sprite.player_dead3.getFxImage());
        swapKill = 4;
      } else {
        animal.setImg(Sprite.transparent.getFxImage());
        running = false;
        Image gameOver = new Image(String.valueOf(Menu.class.getResource("/images/gameOver.png")));
        authorView.setImage(gameOver);
      }
    }
  }

  private void checkBombs() {
    if (listKill[player.getX() / 32][player.getY() / 32] == 4)
      player.setLife(false);
  }

  private void checkEnemy() {
    int ax = player.getX();
    int ay = player.getY();
    for (Animal animal : enemy) {
      int bx = animal.getX();
      int by = animal.getY();
      if (ax == bx && by - 32 <= ay && by + 32 >= ay
          || ay == by && bx - 32 <= ax && bx + 32 >= ax) {
        player.life = false;
        break;
      }
    }
  }

  @Override
  public void update() {
    checkBombs();
    checkEnemy();
    countKill++;
    if (!player.life)
      killBomber(player);
  }
}
