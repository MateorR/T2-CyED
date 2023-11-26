package com.t2.cyed.util;

public class Vertex <K extends Comparable<K>,V>{
  private Vertex<K,V> predecessor;
  private final V value;
  private final K key;
  private int distance;
  private Color color;

  public Vertex(K key, V value) {
    this.key = key;
    this.value = value;
    distance = 0;
    this.color = Color.WHITE;
  }

  public V getValue() {
    return value;
  }

  public int getDistance() {
    return distance;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public K getKey() {
    return key;
  }

  public void setPredecessor(Vertex<K, V> predecessor) {
    this.predecessor = predecessor;

  }

  public Vertex<K, V> getPredecessor() {
    return predecessor;

  }

  public void setFinishTime() {
  }

}