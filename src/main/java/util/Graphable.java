package util;

import java.util.ArrayList;
import java.util.LinkedList;

public interface Graphable<K extends Comparable<K>,V> {
    public void addVertex(K key, V value);
    public void addEdge(K origin, K end, int weight);

    public void bfs(K origin);

    public int size();

    LinkedList<Edge<K, V>> getEdge();

    ArrayList<Integer> dijkstra(K node);

    ArrayList<Integer> shortestPath(K start, K end);

    ArrayList<Edge<K,V>> kruskal();
    Vertex<K,V> getVertex(K key);
}
