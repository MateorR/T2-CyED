package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import model.entities.animal.Animal;
import javafx.scene.image.ImageView;
import model.entities.animal.Player;
import model.entities.blocks.Portal;
import javafx.scene.canvas.Canvas;
import model.entities.blocks.Bomb;
import javafx.scene.image.Image;
import model.entities.Entity;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Sprites.Sprite;
import javafx.fxml.FXML;
import ui.control.Menu;
import ui.control.Move;

import java.util.ArrayList;
import java.util.List;

import static model.entities.blocks.Portal.isPortal;
import static ui.control.Menu.*;

public class BombermanGame extends Application {
  public static final int WIDTH = 25;
  public static final int HEIGHT = 15;
  public static int width = 0;
  public static int height = 0;
  public static int level = 1;

  public static final List<Entity> block = new ArrayList<>();
  public static final List<Animal> enemy = new ArrayList<>();
  public static int[][] idObjects;
  public static int[][] listKill;
  public static Animal player;
  public static boolean running;
  public static ImageView authorView;

  private GraphicsContext context;
  @FXML
  private Canvas canvas;

  private int frame = 1;
  private long lastTime;

  public static Stage mainStage = null;


  public static void main(String[] args) {
    Application.launch(BombermanGame.class);
  }

  @Override
  public void start(Stage stage) {
    canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    canvas.setTranslateY(32);
    context = canvas.getGraphicsContext2D();
    Image author = new Image(String.valueOf(getClass().getResource("/images/front.jpg")));
    authorView = new ImageView(author);
    authorView.setX(-400);
    authorView.setY(-208);
    authorView.setScaleX(0.5);
    authorView.setScaleY(0.5);
    Group root = new Group();
    Menu.createMenu(root);
    root.getChildren().add(canvas);
    root.getChildren().add(authorView);

    Scene scene = getScene(root);

    stage.setScene(scene);
    stage.setTitle("Bomberman Game");
    mainStage = stage;
    mainStage.show();

    lastTime = System.currentTimeMillis();
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        if (running) {
          render();
          update();
          time();
          updateMenu();
        }
      }
    };
    timer.start();

    player = new Player(1, 1, Sprite.player_right_2.getFxImage());
    player.setLife(false);
  }

  private static Scene getScene(Group root) {
    Scene scene = new Scene(root);

    scene.setOnKeyPressed(event -> {
      if (player.isLife())
        switch (event.getCode()) {
          case UP:
            Move.up(player);
            break;
          case DOWN:
            Move.down(player);
            break;
          case LEFT:
            Move.left(player);
            break;
          case RIGHT:
            Move.right(player);
            break;
          case SPACE:
            Bomb.putBomb();
            break;
        }
    });
    return scene;
  }

  public void update() {
    block.forEach(Entity::update);
    enemy.forEach(Entity::update);
    player.update();

    player.setCountToRun(player.getCountToRun() + 1);
    if (player.getCountToRun() == 4) {
      Move.checkRun(player);
      player.setCountToRun(0);
    }

    for (Animal animal : enemy) {
      animal.setCountToRun(animal.getCountToRun() + 1);
      if (animal.getCountToRun() == 8) {
        Move.checkRun(animal);
        animal.setCountToRun(0);
      }
    }

    if (enemy.isEmpty() && !isPortal) {
      Entity portal = new Portal(width - 2, height - 2, Sprite.portal.getFxImage());
      block.add(portal);
    }
  }

  public void render() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    block.forEach(ent -> ent.render(context));
    enemy.forEach(ent -> ent.render(context));
    player.render(context);
  }

  public void time() {
    frame++;

    long now = System.currentTimeMillis();
    if (now - lastTime > 1000) {
      lastTime = System.currentTimeMillis();
      mainStage.setTitle("Bomberman Game | " + frame + " frame");
      frame = 0;

      time.setText("Time: " + timeNumber);
      timeNumber--;
      if (timeNumber < 0)
        player.setLife(false);
    }
  }
}
