package com.t2.cyed.util;

import java.util.*;
import java.util.stream.Collectors;

public class GraphAdjacentMatrix<K extends Comparable<K>, V> extends Graph<K, V> {
  private final HashMap<K, Vertex<K, V>> vertices;
  private final ArrayList<Integer>[][] matriz;

  public GraphAdjacentMatrix(int vertexNumber, GraphType type) {
    super(type);
    vertices = new HashMap<>();
    matriz = new ArrayList[vertexNumber][vertexNumber];
    int i = 0;
    do {
      int j = 0;
      do {
        matriz[i][j] = new ArrayList<>();
        j++;
      } while (j < vertexNumber);
      i++;
    } while (i < vertexNumber);
  }

  @Override
  public void addVertex(K key, V value) {
    if (!vertices.containsKey(key)) {
      vertices.put(key, new Vertex<>(key, value));
      verticesPosition.put(key, numberVertexCurrent++);
    }
  }

  @Override
  public void addEdge(K origin, K end, int weight) {
    vertexExist(origin, end);
    int vertex1 = indexVertex(origin);
    int vertex2 = indexVertex(end);
    if (!loops && vertex1 == vertex2) {
      return;
    }
    if (!multiple && !matriz[vertex1][vertex2].isEmpty()) {
      return;
    }
    matriz[vertex1][vertex2].add(weight);
    Collections.sort(matriz[vertex1][vertex2]);

    edges.add(new Edge<>(vertices.get(origin), vertices.get(end), (int) weight));
    if (!directed) {
      matriz[vertex2][vertex1].add(weight);
      Collections.sort(matriz[vertex2][vertex1]);
      edges.add(new Edge<>(vertices.get(end), vertices.get(origin), (int) weight));
    }
  }

  public boolean vertexExist(K key1, K key2) {
    if (!vertices.containsKey(key1)) {
      return false;
    }
    return vertices.containsKey(key2);
  }

  private int indexVertex(K key) {
    Integer index = verticesPosition.get(key);

    return index == null ? -1 : index;
  }


  @Override
  public boolean adjacent(K keyVertex1, K keyVertex2) {
    if (!vertexExist(keyVertex1, keyVertex2)) {
      return false;
    }
    ;
    return !matriz[indexVertex(keyVertex1)][indexVertex(keyVertex2)].isEmpty();
  }

  @Override
  public void bfs(K keyVertex) {
    for (Vertex<K, V> vertex : vertices.values()) {
      vertex.setColor(Color.WHITE);
      vertex.setDistance(INFINITE);
      vertex.setPredecessor(null);
    }
    Vertex<K, V> vertex = vertices.get(keyVertex);
    vertex.setColor(Color.GRAY);
    vertex.setDistance(0);
    Queue<Vertex<K, V>> queue = new LinkedList<>();
    queue.offer(vertex);
    while (!queue.isEmpty()) {
      Vertex<K, V> u = queue.poll();
      for (Vertex<K, V> v : vertices.values()) {
        if (adjacent(u.getKey(), v.getKey()) && v.getColor() == Color.WHITE) {
          v.setColor(Color.GRAY);
          v.setDistance(u.getDistance() + 1);
          v.setPredecessor(u);
          queue.offer(v);

        }
      }
      u.setColor(Color.BLACK);
    }
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public LinkedList<Edge<K, V>> getEdge() {
    return edges;
  }

  @Override
  public ArrayList<Integer> dijkstra(K keyVertexSource) {
    if (!vertices.containsKey(keyVertexSource)) {
      return null;
    }
    for (Vertex<K, V> vertex : vertices.values()) {
      if (vertex.getKey().compareTo(keyVertexSource) != 0)
        vertex.setDistance(INFINITE);
      vertex.setPredecessor(null);
    }
    vertices.get(keyVertexSource).setDistance(0);
    PriorityQueue<Vertex<K, V>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
    for (Vertex<K, V> vertex : vertices.values()) {
      queue.offer(vertex);
    }
    while (!queue.isEmpty()) {
      Vertex<K, V> u = queue.poll();

      for (Vertex<K, V> v : vertices.values()) {
        if (adjacent(u.getKey(), v.getKey())) {
          int weight = u.getDistance() + matriz[indexVertex(u.getKey())][indexVertex(v.getKey())].get(0);
          if (weight < v.getDistance() || v.getDistance() < -100) {
            v.setDistance(weight);
            v.setPredecessor(u);
            queue.offer(v);
          }
        }
      }
    }
    return vertices.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public ArrayList<Integer> shortestPath(K start, K end) {
    if (!vertices.containsKey(start) || !vertices.containsKey(end)) {
      return null;
    }
    for (Vertex<K, V> vertex : vertices.values()) {
      if (vertex.getKey().compareTo(start) != 0) {
        vertex.setDistance(INFINITE);
      }
      vertex.setPredecessor(null);
    }
    vertices.get(start).setDistance(0);
    PriorityQueue<Vertex<K, V>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
    for (Vertex<K, V> vertex : vertices.values()) {
      queue.offer(vertex);
    }
    while (!queue.isEmpty()) {
      Vertex<K, V> u = queue.poll();
      for (Vertex<K, V> v : vertices.values()) {
        if (adjacent(u.getKey(), v.getKey())) {
          int weight = matriz[indexVertex(u.getKey())][indexVertex(v.getKey())].get(0) + u.getDistance();
          if (weight < v.getDistance()) {
            v.setDistance(weight);
            v.setPredecessor(u);
            queue.offer(v);
          }
        }
      }
    }

    ArrayList<Integer> shortestPath = new ArrayList<>();
    Vertex<K, V> currentNode = vertices.get(end);
    while (currentNode != null && !currentNode.getKey().equals(start)) {
      shortestPath.add((Integer) currentNode.getKey());
      currentNode = currentNode.getPredecessor();
    }
    shortestPath.add((Integer) start);
    Collections.reverse(shortestPath);

    return shortestPath;
  }

  @Override
  public ArrayList<Edge<K, V>> kruskal() {
    ArrayList<Edge<K, V>> edgesG = new ArrayList();
    UnionFind findUnion = new UnionFind(matriz.length);
    edges.sort(Comparator.comparingInt(Edge::getWeight));

    for (Edge<K, V> edge : edges) {
      int keyIndex1 = indexVertex(edge.getStart().getKey());
      int keyIndex2 = indexVertex(edge.getDestination().getKey());

      if (findUnion.find(keyIndex1) != findUnion.find(keyIndex2)) {
        edgesG.add(edge);
        findUnion.union(keyIndex1, keyIndex2);
      }
    }
    return edgesG;
  }

  @Override
  public Vertex<K, V> getVertex(K key) {
    return vertices.get(key);
  }

  private void addEdgesToMinHeap(K key, PriorityQueue<Edge<K, V>> minHeap) {
    int index = indexVertex(key);
    for (int i = 0; i < matriz.length; i++) {
      if (!matriz[index][i].isEmpty()) {
        K neighborKey = null;
        for (Map.Entry<K, Integer> entry : verticesPosition.entrySet()) {
          if (entry.getValue() == i) {
            neighborKey = entry.getKey();
            break;
          }
        }
        int weight = (int) matriz[index][i].get(0);
        if (!minHeap.contains(new Edge<>(vertices.get(key), vertices.get(neighborKey), weight))) {
          minHeap.add(new Edge<>(vertices.get(key), vertices.get(neighborKey), weight));
        }
      }
    }
  }

  private ArrayList<Edge<K, V>> dfsVisit() {
    if (directed) {
      return null;
    }

    HashSet<K> visited = new HashSet<>();
    PriorityQueue<Edge<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
    ArrayList<Edge<K, V>> minimumSpanningTree = new ArrayList<>();

    K startVertex = vertices.keySet().iterator().next();

    visited.add(startVertex);
    addEdgesToMinHeap(startVertex, minHeap);
    while (visited.size() < vertices.size()) {
      Edge<K, V> minEdge = minHeap.poll();
      assert minEdge != null;
      K fromKey = minEdge.getStart().getKey();
      K toKey = minEdge.getDestination().getKey();

      if (!visited.contains(toKey)) {
        visited.add(toKey);
        minimumSpanningTree.add(minEdge);
        addEdgesToMinHeap(toKey, minHeap);
      }
    }

    return minimumSpanningTree;
  }

}
