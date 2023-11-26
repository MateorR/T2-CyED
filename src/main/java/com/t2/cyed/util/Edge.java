package com.t2.cyed.util;

public class Edge<K extends Comparable<K>, V> {
  private final Vertex<K, V> start;
  private final Vertex<K, V> destination;
  private final int weight;

  public Edge(Vertex<K, V> start, Vertex<K, V> destination, int weight) {
    this.start = start;
    this.destination = destination;
    this.weight = weight;
  }

  public Vertex<K, V> getStart() {
    return start;
  }

  public Vertex<K, V> getDestination() {
    return destination;
  }

  public int getWeight() {
    return weight;
  }

}
