package com.t2.cyed.util;

import java.util.stream.Collectors;
import java.util.*;

public class GraphAdjacentList<K extends Comparable<K>, V> extends Graph<K, V> {

  private final HashMap<K, VertexAdjacentList<K, V>> vertices;

  public GraphAdjacentList(GraphType type) {
    super(type);
    vertices = new HashMap<>();
  }

  @Override
  public boolean addVertex(K key, V value) {
    if (!vertices.containsKey(key)) {
      vertices.put(key, new VertexAdjacentList<>(key, value));
      verticesPosition.put(key, numberVertexCurrent);
      numberVertexCurrent++;
      return true;
    }
    return false;
  }


  @Override
  public boolean addEdge(K key1, K key2, int weight) {
    VertexAdjacentList<K, V> v1 = vertices.get(key1);
    VertexAdjacentList<K, V> v2 = vertices.get(key2);
    if (v1 == null) {
      return false;
    }
    if (v2 == null) {
      return false;
    }
    if (!loops && key1.compareTo(key2) == 0) {
      return false;
    }
    Edge<K, V> edge = new Edge<>(v1, v2, weight);
    if (!multiple && v1.getEdges().contains(edge)) {
      return false;
    }
    v1.getEdges().add(edge);
    edges.add(edge);
    if (!directed) {

      Edge<K, V> edge2 = new Edge<>(v2, v1, weight);
      v2.getEdges().add(edge2);
      edges.add(edge2);
    }
    return true;
  }

  @Override
  public void bfs(K origin) {
    for (K key : vertices.keySet()) {
      Vertex<K, V> vertex = vertices.get(key);
      vertex.setColor(Color.WHITE);
      vertex.setDistance(INFINITE);
      vertex.setPredecessor(null);
    }

    VertexAdjacentList<K, V> vertexL = vertices.get(origin);
    if (vertexL == null) {
      return;
    }

    vertexL.setColor(Color.GRAY);
    vertexL.setDistance(0);
    Queue<VertexAdjacentList<K, V>> queue = new LinkedList<>();
    queue.offer(vertexL);
    while (!queue.isEmpty()) {
      VertexAdjacentList<K, V> vertex = queue.poll();
      LinkedList<Edge<K, V>> edges = vertex.getEdges();
      for (Edge<K, V> edge : edges) {

        VertexAdjacentList<K, V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
        if (vertex2.getColor() == Color.WHITE) {
          vertex2.setColor(Color.GRAY);
          vertex2.setDistance(vertex.getDistance() + 1);
          vertex2.setPredecessor(vertex);
          queue.offer(vertex2);
        }
      }
      vertex.setColor(Color.BLACK);
    }
  }

  @Override
  public int size() {
    return vertices.size();
  }

  public boolean removeEdge(K key1, K key2) {
    boolean removed = false;
    VertexAdjacentList<K, V> v1 = vertices.get(key1);
    VertexAdjacentList<K, V> v2 = vertices.get(key2);
    if (v1 == null || v2 == null) {
      return false;
    }

    List<Edge<K, V>> edgesV1 = v1.getEdges();
    for (Iterator<Edge<K, V>> iterator = edgesV1.iterator(); iterator.hasNext(); ) {
      Edge<K, V> edge = iterator.next();
      if (edge.getDestination().getKey().compareTo(key2) == 0) {
        iterator.remove();
        removed = true;
      }
    }

    if (!directed) {
      List<Edge<K, V>> edgesV2 = v2.getEdges();
      edgesV2.removeIf(edge -> edge.getDestination().getKey().compareTo(key1) == 0);
    }
    return removed;
  }


  public boolean adjacent(K keyVertex1, K keyVertex2) {
    boolean adjacent = false;
    VertexAdjacentList<K, V> v1 = vertices.get(keyVertex1);
    VertexAdjacentList<K, V> v2 = vertices.get(keyVertex2);
    if (v1 != null && v2 != null) {
      LinkedList<Edge<K, V>> edges1 = v1.getEdges();
      for (Edge<K, V> edge : edges1) {
        if (edge.getDestination().getKey().compareTo(keyVertex2) == 0) {
          adjacent = true;
          break;
        }
      }
    }

    return adjacent;
  }

  private int verticesIndex(K key) {
    Integer index = verticesPosition.get(key);


    return index == null ? -1 : index;
  }

  public void DFS() {
    if (!vertices.isEmpty()) {
      for (VertexAdjacentList<K, V> vertex : vertices.values()) {
        vertex.setColor(Color.WHITE);
        vertex.setPredecessor(null);
      }
      time = 0;
      for (VertexAdjacentList<K, V> vertex : vertices.values()) {
        if (vertex.getColor() == Color.WHITE) {
          DFSVisit(vertex, time);
        }
      }
    }
  }

  private void DFSVisit(VertexAdjacentList<K, V> vertex, int t) {
    time += 1;
    vertex.setDistance(time);

    vertex.setColor(Color.GRAY);
    LinkedList<Edge<K, V>> edges = vertex.getEdges();
    for (Edge<K, V> edge : edges) {
      VertexAdjacentList<K, V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
      if (vertex2.getColor() == Color.WHITE) {
        vertex2.setPredecessor(vertex);
        DFSVisit(vertex2, time);
      }
    }
    vertex.setColor(Color.BLACK);
    time += 1;
    vertex.setFinishTime();
  }


  @Override
  public ArrayList<Integer> dijkstra(K keyVertexSource) {
    if (vertices.get(keyVertexSource) == null) {
      return null;
    }
    calculateNodes(keyVertexSource);
    return vertices.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
  }


  @Override
  public ArrayList<Integer> shortestPath(K startNode, K endNode) {
    if (vertices.get(startNode) == null || vertices.get(endNode) == null) {
      return null;
    }

    calculateNodes(startNode);

    ArrayList<Integer> shortestPath = new ArrayList<>();
    VertexAdjacentList<K, V> currentNode = vertices.get(endNode);
    while (currentNode != null) {
      shortestPath.add((Integer) currentNode.getKey());
      if (currentNode.getKey().equals(startNode)) {
        break;
      }
      currentNode = (VertexAdjacentList<K, V>) currentNode.getPredecessor();
    }
    Collections.reverse(shortestPath);

    return shortestPath;
  }

  private void calculateNodes(K startNode) {
    for (VertexAdjacentList<K, V> vertex : vertices.values()) {
      if (vertex.getKey().compareTo(startNode) != 0)
        vertex.setDistance(INFINITE);
      vertex.setPredecessor(null);
    }

    PriorityQueue<VertexAdjacentList<K, V>> priority = new PriorityQueue<>(
        Comparator.comparingInt(Vertex::getDistance));
    for (VertexAdjacentList<K, V> vertex : vertices.values()) {
      priority.offer(vertex);
    }

    while (!priority.isEmpty()) {
      VertexAdjacentList<K, V> vertex = priority.poll();
      LinkedList<Edge<K, V>> edges = vertex.getEdges();
      for (Edge<K, V> edge : edges) {
        VertexAdjacentList<K, V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
        int weight = edge.getWeight() + vertex.getDistance();
        if (weight < vertex2.getDistance()) {
          priority.remove(vertex2);
          vertex2.setDistance(weight);
          vertex2.setPredecessor(vertex);
          priority.offer(vertex2);
        }
      }
    }
  }

  @Override
  public ArrayList<Edge<K, V>> kruskal() {
    ArrayList<Edge<K, V>> mst = new ArrayList<>();
    UnionFind unionFind = new UnionFind(vertices.size());
    edges.sort(Comparator.comparingInt(Edge::getWeight));
    for (Edge<K, V> edge : edges) {
      VertexAdjacentList<K, V> vertex1 = (VertexAdjacentList<K, V>) edge.getStart();
      VertexAdjacentList<K, V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
      if (unionFind.find(verticesIndex(vertex1.getKey())) != unionFind.find(verticesIndex(vertex2.getKey()))) {
        mst.add(edge);
        unionFind.union(verticesIndex(vertex1.getKey()), verticesIndex(vertex2.getKey()));
      }
    }
    return mst;
  }

  public Vertex<K, V> getVertex(K key) {
    return vertices.get(key);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vertices:\n");
    for (Vertex<K, V> vertex : vertices.values()) {
      sb.append(vertex.getKey()).append(" ");
    }
    sb.append("\n\nEdges:\n");
    for (Edge<K, V> edge : edges) {
      sb.append(edge.getStart().getKey()).append(" -> ").append(edge.getDestination().getKey()).append(" (").append(edge.getWeight()).append(")\n");
    }
    return sb.toString();
  }

  public LinkedList<Edge<K, V>> getEdge() {
    return edges;
  }

  public boolean removeVertex(K i) {
    boolean removed = false;
    VertexAdjacentList<K, V> vertex = vertices.remove(i);
    if (vertex != null) {
      removed = true;
      for (K KeyVertex : vertices.keySet()) {
        VertexAdjacentList<K, V> vertexList = vertices.get(KeyVertex);
        LinkedList<Edge<K, V>> edges = vertexList.getEdges();
        edges.removeIf(edge -> edge.getDestination().getKey().compareTo(i) == 0);
      }
    }
    return removed;
  }
}
