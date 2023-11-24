package util;

import java.util.ArrayList;

public interface Graphable<K,V> {
    public void addVertex(K key, V value);

    public void addEdge(K origin, K end, double weight);

    public GraphVertex<V> removeVertex(K key);

    public void removeEdge(K origin, K end);

    public void bfs(K origin);

    public void dfs();

    public ArrayList<GraphVertex<V>> dijsktra(K origin, K end);

    public double[][] floydWarshall();

    public void prim(GraphVertex<V> r);

    public int size();



}
