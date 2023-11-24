package util;

public class Edge <V>{
    private GraphVertex<V> end;
    private double weight;

    public Edge(GraphVertex<V> end, double weight){
        this.end = end;
        this.weight = weight;
    }

    public GraphVertex<V> getEnd() {
        return end;
    }

    public void setEnd(GraphVertex<V> end) {
        this.end = end;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
