package ui.levels;

import model.entities.item.FlameItem;
import model.entities.item.SpeedItem;
import model.entities.blocks.Portal;
import model.entities.blocks.Grass;
import model.entities.blocks.Brick;
import model.entities.blocks.Wall;

import java.util.StringTokenizer;

import model.entities.Entity;

import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

import ui.Sprites.Sprite;

import java.io.File;

import static ui.BombermanGame.*;

public class CreateMap {
  public CreateMap(String lvl) {
    System.out.println(System.getProperty("user.dir"));
    final File fileName = new File(lvl);
    try (FileReader inputFile = new FileReader(fileName)) {
      Scanner sc = new Scanner(inputFile);
      String line = sc.nextLine();

      StringTokenizer tokens = new StringTokenizer(line);
      level = Integer.parseInt(tokens.nextToken());
      height = Integer.parseInt(tokens.nextToken());
      width = Integer.parseInt(tokens.nextToken());

      while (sc.hasNextLine()) {
        idObjects = new int[width][height];
        listKill = new int[width][height];
        for (int i = 0; i < height; ++i) {
          String lineTile = sc.nextLine();
          StringTokenizer tokenTile = new StringTokenizer(lineTile);

          for (int j = 0; j < width; j++) {
            int element = Integer.parseInt(tokenTile.nextToken());
            Entity entity;
            switch (element) {
              case 1:
                entity = new Portal(j, i, Sprite.grass.getFxImage());
                element = 0;
                break;
              case 2:
                entity = new Wall(j, i, Sprite.wall.getFxImage());
                break;
              case 3:
                entity = new Brick(j, i, Sprite.brick.getFxImage());
                break;
              case 6:
                entity = new SpeedItem(j, i, Sprite.brick.getFxImage());
                break;
              case 7:
                entity = new FlameItem(j, i, Sprite.brick.getFxImage());
                break;
              default:
                entity = new Grass(j, i, Sprite.grass.getFxImage());
            }
            idObjects[j][i] = element;
            block.add(entity);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
