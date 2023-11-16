package model.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ui.Sprites.Sprite;

public abstract class Entity {

  private int x;
  private int y;
  private Image img;

  public Entity(int xUnit, int yUnit, Image img) {
    this.x = xUnit * Sprite.SCALED_SIZE;
    this.y = yUnit * Sprite.SCALED_SIZE;
    this.img = img;
  }

  public Entity() {

  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Image getImg() {
    return img;
  }

  public void setImg(Image img) {
    this.img = img;
  }

  public void render(GraphicsContext context) {
    context.drawImage(img, x, y);
  }

  public abstract void update();
}
