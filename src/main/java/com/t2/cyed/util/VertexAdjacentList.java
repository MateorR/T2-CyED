package com.t2.cyed.util;

import java.util.LinkedList;

public class VertexAdjacentList<K extends Comparable<K>, V> extends Vertex<K, V> {
  private final LinkedList<Edge<K, V>> edges;

  public VertexAdjacentList(K key, V value) {
    super(key, value);
    edges = new LinkedList<>();
  }

  public LinkedList<Edge<K, V>> getEdges() {
    return edges;
  }
}
