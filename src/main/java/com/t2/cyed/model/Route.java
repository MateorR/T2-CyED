package com.t2.cyed.model;

public class Route {
  private final int cityA;
  private final int cityB;
  private final int difficulty;

  public Route(int cityA, int cityB, int difficulty) {
    this.cityA = cityA;
    this.cityB = cityB;
    this.difficulty = difficulty;
  }

  public int getCityA() {
    return cityA;
  }

  public int getCityB() {
    return cityB;
  }

  public int getDifficulty() {
    return difficulty;
  }

}
