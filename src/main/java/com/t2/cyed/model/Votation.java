package com.t2.cyed.model;

public class Votation {

  private final int x;
  private final int y;
  private final int id;

  public Votation(int x, int y, int id) {
    this.x = x;
    this.y = y;
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getId() {
    return id;
  }

}
