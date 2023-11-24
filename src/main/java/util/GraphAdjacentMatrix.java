package util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class GraphAdjacentMatrix<K, V> implements Graphable<K,V> {

    private GraphType type;
    private Hashtable<K,GraphVertex<V>> vertices;
    private ArrayList<K> keys;
    private double[][] edges;

    public GraphAdjacentMatrix(int type, int maxVertices){
        this.type = GraphType.values()[type];
        this.vertices = new Hashtable<>();
        this.keys = new ArrayList<>();
        this.edges = new double[maxVertices][maxVertices];
    }
    @Override
    public void addVertex(K key, V value) {
        if (!this.keys.contains(key)){
            vertices.put(key,new GraphVertex<>(value));
            keys.add(key);
        }
    }

    @Override
    public void addEdge(K origin, K end, double weight) {
        if (this.vertices.containsKey(origin) && this.vertices.containsKey(end)){
            switch (this.type){
                case SIMPLE:
                    for (int i = 0; i < this.edges.length; i++) {
                        for (int j = 0; j < this.edges.length; j++) {
                            if (i == this.keys.indexOf(origin) && j == this.keys.indexOf(end)) {
                                edges[i][j] = weight;
                                edges[j][i] = weight;
                            }
                        }
                    }
                    break;
                case DIRECTED:
                    for (int i = 0; i < this.edges.length; i++) {
                        for (int j = 0; j < this.edges.length; j++) {
                            if (i == this.keys.indexOf(origin) && j == this.keys.indexOf(end)){
                                edges[i][j] = weight;
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public GraphVertex<V> removeVertex(K key) {
        GraphVertex<V> found = null;
        if (this.vertices.containsKey(key)){
            for (int i = 0; i < this.edges.length; i++) {
                for (int j = 0; j < this.edges.length; j++) {
                    if (i == this.keys.indexOf(key) || j == this.keys.indexOf(key)){
                        edges[i][j] = 0;
                    }
                }
            }
            found = vertices.remove(key);
            keys.remove(key);
        }
        return found;
    }

    @Override
    public void removeEdge(K origin, K end) {
        if (this.vertices.containsKey(origin) && this.vertices.containsKey(end)){
            switch (this.type){
                case SIMPLE:
                    for (int i = 0; i < this.edges.length; i++) {
                        for (int j = 0; j < this.edges.length; j++) {
                            if (i == this.keys.indexOf(origin) && j == this.keys.indexOf(end)){
                                edges[i][j] = 0;
                                edges[j][i] = 0;
                            }
                        }
                    }
                    break;
                case DIRECTED:
                    for (int i = 0; i < this.edges.length; i++) {
                        for (int j = 0; j < this.edges.length; j++) {
                            if (i == this.keys.indexOf(origin) && j == this.keys.indexOf(end)){
                                edges[i][j] = 0;
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void bfs(K origin) {
        for (GraphVertex<V> vertex : vertices.values()) {
            vertex.setState(State.WHITE);
            vertex.setDistance(Double.POSITIVE_INFINITY);
            vertex.setParent(null);
        }
        this.vertices.get(origin).setState(State.GRAY);
        this.vertices.get(origin).setDistance(0);
        this.vertices.get(origin).setParent(null);
        Queue<GraphVertex<V>> queue = new LinkedList<>();
        queue.add(this.vertices.get(origin));
        while (!queue.isEmpty()){
            GraphVertex<V> current = queue.poll();
            for (GraphVertex<V> adjacent : getAdjacentsV(current)) {
                if (adjacent.getState() == State.WHITE){
                    adjacent.setState(State.GRAY);
                    adjacent.setDistance(current.getDistance()+1);
                    adjacent.setParent(current);
                    queue.add(adjacent);
                }
            }
            current.setState(State.BLACK);
        }
    }

    private ArrayList<GraphVertex<V>> getAdjacentsV(GraphVertex<V> current) {
        ArrayList<GraphVertex<V>> adjacents = new ArrayList<>();
        for (int i = 0; i < this.edges.length; i++) {
            if (this.edges[this.keys.indexOf(getKey(current))][i] != 0){
                adjacents.add(this.vertices.get(this.keys.get(i)));
            }
        }
        return adjacents;
    }

    @Override
    public void dfs() {
        for (GraphVertex<V> vertex : vertices.values()) {
            vertex.setState(State.WHITE);
            vertex.setParent(null);
        }
        int time = 0;
        for (GraphVertex<V> vertex : vertices.values()) {
            if (vertex.getState() == State.WHITE){
                dfsVisit(vertex,time);
            }
        }
    }

    private void dfsVisit(GraphVertex<V> vertex, int time){
        time++;
        vertex.setTime(time);
        vertex.setState(State.GRAY);
        for (GraphVertex<V> adjacent : getAdjacentsV(vertex)) {
            if (adjacent.getState() == State.WHITE){
                adjacent.setParent(vertex);
                dfsVisit(adjacent,time);
            }
        }
        vertex.setState(State.BLACK);
        time++;
        vertex.setFinalTime(time);
    }

    @Override
    public ArrayList<GraphVertex<V>> dijsktra(K origin, K end) {
        this.vertices.get(origin).setDistance(0);
        MinPriorityQueue<GraphVertex<V>> queue = new MinPriorityQueue<>();
        for (GraphVertex<V> vertex : vertices.values()) {
            vertex.setDistance(Double.POSITIVE_INFINITY);
            vertex.setParent(null);
            queue.insert(vertex);
        }
        while (!queue.isEmpty()){
            GraphVertex<V> current = queue.extractMin();
            for (GraphVertex<V> vertex : getAdjacentsV(current)) {
                double weight = current.getDistance() + current.lenghtTo(vertex);
                if (weight < vertex.getDistance()){
                    vertex.setDistance(weight);
                    vertex.setParent(current);
                    queue.decreaseKey(queue.getElements().indexOf(vertex),vertex);
                }
            }
        }
        ArrayList<GraphVertex<V>> path = new ArrayList<>();
        GraphVertex<V> current = vertices.get(end);
        while (current != null){
            path.add(0,current);
            current = current.getParent();
        }
        return path;
    }

    @Override
    public double[][] floydWarshall() {
        double[][] matrix = new double[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                matrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < vertices.size(); i++) {
            matrix[i][i] = 0;
        }
        for (GraphVertex<V> vertex : vertices.values()) {
            for (GraphVertex<V> adjacent : getAdjacentsV(vertex)) {
                matrix[this.keys.indexOf(getKey(vertex))][this.keys.indexOf(getKey(adjacent))] = vertex.lenghtTo(adjacent);
            }
        }
        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]){
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
        return matrix;
    }

    @Override
    public void prim(GraphVertex<V> r) {
        for (GraphVertex<V> vertex : vertices.values()) {
            vertex.setDistance(Double.POSITIVE_INFINITY);
            vertex.setParent(null);
            vertex.setState(State.WHITE);
        }
        r.setDistance(0);
        MinPriorityQueue<GraphVertex<V>> queue = new MinPriorityQueue<>();
        for (GraphVertex<V> vertex : vertices.values()) {
            queue.insert(vertex);
        }
        while (!queue.isEmpty()){
            GraphVertex<V> current = queue.extractMin();
            for (GraphVertex<V> vertex : getAdjacentsV(current)) {
                if (vertex.getState()==State.WHITE && current.lenghtTo(vertex) < vertex.getDistance()){
                    vertex.setDistance(current.lenghtTo(vertex));
                    queue.decreaseKey(queue.getElements().indexOf(vertex),vertex);
                    vertex.setParent(current);
                }
            }
        }
    }

    @Override
    public int size() {
        return 0;
    }

    public K getKey(GraphVertex<V> vertex){
        for (K key : vertices.keySet()) {
            if (vertices.get(key).equals(vertex)){
                return key;
            }
        }
        return null;
    }
    public GraphVertex<V> getVertex(K key){
        return vertices.get(key);
    }

    public GraphType getType() {
        return type;
    }

    public void setType(GraphType type) {
        this.type = type;
    }

    public Hashtable<K, GraphVertex<V>> getVertices() {
        return vertices;
    }

    public void setVertices(Hashtable<K, GraphVertex<V>> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<K> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<K> keys) {
        this.keys = keys;
    }

    public double[][] getEdges() {
        return edges;
    }

    public void setEdges(double[][] edges) {
        this.edges = edges;
    }


}
