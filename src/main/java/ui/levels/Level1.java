package ui.levels;

import javafx.scene.image.Image;
import model.entities.animal.Animal;
import model.entities.animal.Ballom;
import model.entities.animal.Oneal;
import ui.Sprites.Sprite;

import static model.entities.animal.Player.swapKill;
import static model.entities.blocks.Bomb.powerBomb;
import static model.entities.item.SpeedItem.speed;
import static model.entities.blocks.Bomb.isBomb;
import static ui.BombermanGame.*;
import static ui.control.Menu.*;

public class Level1 {
  public Level1() {
    enemy.clear();
    block.clear();
    swapKill = 1;
    powerBomb = 0;
    new CreateMap("src/main/resources/levels/Level1.txt");
    player.setLife(true);
    player.setX(32);
    player.setY(32);
    timeNumber = 360;
    bombNumber = 20;
    isBomb = 0;
    speed = 1;

    player.setImg(Sprite.player_right_2.getFxImage());
    Image transparent = new Image(String.valueOf(getClass().getResource("/images/transparent.png")));
    authorView.setImage(transparent);

    Animal enemy1 = new Ballom(4, 4, Sprite.ballom_left1.getFxImage());
    Animal enemy2 = new Ballom(9, 9, Sprite.ballom_left1.getFxImage());
    Animal enemy3 = new Ballom(22, 6, Sprite.ballom_left1.getFxImage());
    enemy.add(enemy1);
    enemy.add(enemy2);
    enemy.add(enemy3);

    Animal enemy4 = new Oneal(7, 6, Sprite.oneal_right1.getFxImage());
    Animal enemy5 = new Oneal(13, 8, Sprite.oneal_right1.getFxImage());
    enemy.add(enemy4);
    enemy.add(enemy5);

    for (Animal animal : enemy) {
      animal.setLife(true);
    }
  }
}
