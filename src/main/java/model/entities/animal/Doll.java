package model.entities.animal;

import javafx.scene.image.Image;
import model.entities.animal.ai.searchMinimumHeuristics;
import model.entities.animal.ai.Node;
import ui.Sprites.Sprite;
import ui.control.Move;

import java.util.List;

import static ui.BombermanGame.enemy;
import static ui.BombermanGame.height;
import static ui.BombermanGame.idObjects;
import static ui.BombermanGame.player;
import static ui.BombermanGame.width;

public class Doll extends Animal {
  private static int swapKill = 1;
  private static int countKill = 0;

  public Doll(int x, int y, Image img) {
    super(x, y, img);
  }

  public Doll(int isMove, int swap, String direction, int count, int countToRun) {
    super(4, 1, "up", 0, 0);
  }

  public Doll(boolean life) {
    super(life);
  }

  public Doll() {
  }

  private void killDoll(Animal animal) {
    if (countKill % 16 == 0) {
      if (swapKill == 1) {
        animal.setImg(Sprite.doll_dead.getFxImage());
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

  private void moveDoll() {
    if (this.getX() % 32 == 0 && this.getY() % 32 == 0) {
      Node initialNode = new Node(this.getY() / 32, this.getX() / 32);
      Node finalNode = new Node(player.getY() / 32, player.getX() / 32);

      int rows = height;
      int cols = width;

      searchMinimumHeuristics searchMinimumHeuristics = new searchMinimumHeuristics(rows, cols, initialNode, finalNode);

      int[][] blocksArray = new int[width * height][2];
      int countBlock = 0;

      for (int i = 0; i < height; i++)
        for (int j = 0; j < width; j++)
          if (idObjects[j][i] != 0) {
            blocksArray[countBlock][0] = i;
            blocksArray[countBlock][1] = j;
            countBlock++;
          }

      searchMinimumHeuristics.setBlocks(blocksArray, countBlock);
      List<Node> path = searchMinimumHeuristics.findPath();
      if (!path.isEmpty()) {
        int nextY = path.get(1).getRow();
        int nextX = path.get(1).getCol();

        if (this.getY() / 32 > nextY)
          Move.up(this);
        if (this.getY() / 32 < nextY)
          Move.down(this);
        if (this.getX() / 32 > nextX)
          Move.left(this);
        if (this.getX() / 32 < nextX)
          Move.right(this);
      }
    }
  }

  @Override
  public void update() {
    countKill++;
    for (Animal animal : enemy) {
      if (animal instanceof Doll && !animal.life)
        killDoll(animal);
    }
    moveDoll();
  }
}
