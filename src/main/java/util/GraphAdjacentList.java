package util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class GraphAdjacentList<K,V> implements Graphable<K,V>{

    private GraphType type;
    private Hashtable<K,GraphVertex<V>> vertices;

    public GraphAdjacentList(int type){
        this.type = GraphType.values()[type];
        this.vertices = new Hashtable<>();
    }
    @Override
    public void addVertex(K key, V value){
        if (!this.vertices.containsKey(key)){
            vertices.put(key,new GraphVertex<>(value));
        }
    }

    @Override
    public void addEdge(K origin, K end, double weight){
        if (vertices.containsKey(origin) && vertices.containsKey(end)){
            GraphVertex<V> originVertex = vertices.get(origin);
            GraphVertex<V> endVertex = vertices.get(end);
            switch (this.type){
                case SIMPLE:
                    originVertex.addAdjacent(endVertex,weight);
                    endVertex.addAdjacent(originVertex,weight);
                    break;
                case DIRECTED:
                    originVertex.addAdjacent(endVertex,weight);
                    break;
            }
        }
    }

    @Override
    public GraphVertex<V> removeVertex(K key) {
        GraphVertex<V> found = null;
        if (vertices.containsKey(key)){
            for (GraphVertex<V> vertex : vertices.values()) {
                if (vertex.isAdjacent(vertices.get(key))){
                    vertex.removeAdjacent(vertices.get(key));
                }
            }
            found = vertices.remove(key);
        }
        return found;
    }

    @Override
    public void removeEdge(K origin, K end) {
        if (vertices.containsKey(origin) && vertices.containsKey(end)){
            if (vertices.get(origin).isAdjacent(vertices.get(end))){
                switch (this.type){
                    case SIMPLE:
                        vertices.get(origin).removeAdjacent(vertices.get(end));
                        vertices.get(end).removeAdjacent(vertices.get(origin));
                        break;
                    case DIRECTED:
                        vertices.get(origin).removeAdjacent(vertices.get(end));
                        break;
                }
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
            for (GraphVertex<V> adjacent : current.getAdjacentsVertices()) {
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
        for (GraphVertex<V> adjacent : vertex.getAdjacentsVertices()) {
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
            for (GraphVertex<V> vertex : current.getAdjacentsVertices()) {
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
            for (GraphVertex<V> adjacent : vertex.getAdjacentsVertices()) {
                matrix[getIndex(vertex)][getIndex(adjacent)] = vertex.lenghtTo(adjacent);
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
            for (GraphVertex<V> vertex : current.getAdjacentsVertices()) {
                if (vertex.getState()==State.WHITE && current.lenghtTo(vertex) < vertex.getDistance()){
                    vertex.setDistance(current.lenghtTo(vertex));
                    queue.decreaseKey(queue.getElements().indexOf(vertex),vertex);
                    vertex.setParent(current);
                }
            }
        }
    }

    public int getIndex(GraphVertex<V> vertex){
        ArrayList<K> keys = vertexNumeration();
        int index = -1;
        for (int i = 0; i < keys.size(); i++) {
            if (vertices.get(keys.get(i)).equals(vertex)){
                index = i;
            }
        }
        return index;
    }
    public ArrayList<K> vertexNumeration(){
        return new ArrayList<>(vertices.keySet());
    }

    @Override
    public int size() {
        int count = 0;
        for (GraphVertex<V> vertex : vertices.values()) {
            if (vertex != null){
                count++;
            }
        }
        return count;
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

    public boolean isStronglyConnected() {
        boolean connected = true;
        for (K key: vertices.keySet()) {
            bfs(key);
            if (bfsParents(key) < vertices.size()-1){
                connected = false;
            }
        }
        return connected;
    }

    public int bfsParents(K origin){
        int count=0;
        for (GraphVertex<V> vertex : vertices.values()) {
            if (vertex.getParent()!=null){
                count++;
            }
        }
        return count;
    }




}
