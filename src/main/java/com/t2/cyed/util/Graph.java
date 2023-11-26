package com.t2.cyed.util;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Graph<K extends Comparable<K>, V> implements Graphable<K, V> {
  protected int time, numberVertexCurrent;
  protected boolean directed, loops, multiple;
  protected final Integer INFINITE;
  protected final HashMap<K, Integer> verticesPosition;
  protected LinkedList<Edge<K, V>> edges;


  protected Graph(GraphType type) {
    verticesPosition = new HashMap<>();
    INFINITE = Integer.MAX_VALUE - 100;
    edges = new LinkedList<>();
    time = 0;
    numberVertexCurrent = 0;
    selectType(type);
  }

  public void selectType(GraphType type) {
    directed = (type == GraphType.DIRECTED);
  }

  public abstract boolean adjacent(K keyVertex1, K keyVertex2);
}