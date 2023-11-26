package com.t2.cyed.model;

public class Votation {

  private int x;
  private int y;
  private int id;

  public Votation(int x, int y, int id) {
    this.x = x;
    this.y = y;
    this.id = id;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
