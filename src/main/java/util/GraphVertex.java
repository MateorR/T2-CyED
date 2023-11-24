package util;

import java.util.ArrayList;

public class GraphVertex<V> implements Comparable<GraphVertex<V>> {
    private V value;
    private double distance;
    private State state;
    private int time;
    private int finalTime;
    private GraphVertex<V> parent;
    private ArrayList<Edge<V>> adjacents;

    public GraphVertex(V value){
        this.value = value;
        this.distance = 0;
        this.state = State.WHITE;
        this.time = 0;
        this.finalTime = 0;
        this.parent = null;
        this.adjacents = new ArrayList<>();
    }

    public void addAdjacent(GraphVertex<V> adjacent, double weight){
        adjacents.add(new Edge<>(adjacent,weight));
    }
    public void removeAdjacent(GraphVertex<V> adjacent){
        boolean valid = true;
        adjacents.removeIf(edge -> edge.getEnd().equals(adjacent));
    }
    public boolean isAdjacent(GraphVertex<V> adjacent){
        return searchAdjacent(adjacent.getValue()) != null;
    }
    public GraphVertex<V> searchAdjacent(V value){
        for (Edge<V> edge : adjacents) {
            if(edge.getEnd().getValue().equals(value)){
                return edge.getEnd();
            }
        }
        return null;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public double getDistance() {
        return distance;
    }

    public double lenghtTo(GraphVertex<V> vertex){
        for (Edge<V> edge : adjacents) {
            if(edge.getEnd().equals(vertex)){
                return edge.getWeight();
            }
        }
        return 0;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(int finalTime) {
        this.finalTime = finalTime;
    }

    public GraphVertex<V> getParent() {
        return parent;
    }

    public void setParent(GraphVertex<V> parent) {
        this.parent = parent;
    }

    public ArrayList<Edge<V>> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(ArrayList<Edge<V>> adjacents) {
        this.adjacents = adjacents;
    }

    public ArrayList<GraphVertex<V>> getAdjacentsVertices() {
        ArrayList<GraphVertex<V>> vertices = new ArrayList<>();
        for (Edge<V> edge : adjacents) {
            vertices.add(edge.getEnd());
        }
        return vertices;
    }

    @Override
    public int compareTo(GraphVertex<V> o) {
        if (this.distance < o.distance)
            return -1;
        else if (this.distance > o.distance)
            return 1;
        else
            return 0;
    }
}
