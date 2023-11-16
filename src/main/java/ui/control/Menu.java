package ui.control;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ui.BombermanGame;
import ui.levels.Level1;

import static ui.BombermanGame.player;
import static ui.BombermanGame.running;

public class Menu {
  private static ImageView statusGame;
  public static Text level, bomb, time;
  public static int bombNumber = 20, timeNumber = 120;

  public static void createMenu(Group root) {
    level = new Text("Level: 1");
    level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    level.setFill(Color.valueOf("#182c42"));
    level.setX(416);
    level.setY(20);
    bomb = new Text("Bombs: 20");
    bomb.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    bomb.setFill(Color.valueOf("#182c42"));
    bomb.setX(512);
    bomb.setY(20);
    time = new Text("Times: 0");
    time.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    time.setFill(Color.valueOf("#182c42"));
    time.setX(608);
    time.setY(20);

    Image newGame = new Image(String.valueOf(Menu.class.getResource("/images/newGame.jpg")));
    statusGame = new ImageView(newGame);
    statusGame.setFitHeight(90);
    statusGame.setFitWidth(180);
    statusGame.setX(25);
    statusGame.setY(-28.5);
    statusGame.setScaleX(0.5);
    statusGame.setScaleY(0.5);

    Pane pane = new Pane();
    pane.getChildren().addAll(level, bomb, time, statusGame);
    pane.setMinSize(800, 64);
    pane.setMaxSize(800, 480);
    pane.setStyle("-fx-background-color: #9cd8db");

    root.getChildren().add(pane);

    statusGame.setOnMouseClicked(event -> {
      if (player.isLife()) {
        running = !running;
      } else {
        new Level1();
        running = true;
      }
      updateMenu();
    });

  }

  public static void updateMenu() {
    level.setText("Level: " + BombermanGame.level);
    bomb.setText("Bombs: " + bombNumber);

    if (player.isLife())
      if (running) {
        Image pauseGame = new Image(String.valueOf(Menu.class.getResource("/images/pauseGame.png")));
        statusGame.setImage(pauseGame);
        statusGame.setFitHeight(75);
        statusGame.setY(-22.5);
      } else {
        Image playGame = new Image(String.valueOf(Menu.class.getResource("/images/playGame.png")));
        statusGame.setImage(playGame);
        statusGame.setFitHeight(75);
        statusGame.setY(-22.5);
      }
    else {
      Image newGame = new Image(String.valueOf(Menu.class.getResource("/images/newGame.jpg")));
      statusGame.setImage(newGame);
      statusGame.setFitHeight(90);
      statusGame.setY(-28.5);
    }
  }
}
