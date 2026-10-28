package src.graphs.graphadt;

public class Edge<T> {
    private Vertex<T> origin;
    private Vertex<T> destination;
    private T value;
    private final boolean isDirected;

    public Edge(Vertex<T> origin, Vertex<T> destination, T value, boolean isDirected) {
        this.origin = origin;
        this.destination = destination;
        this.value = value;
        this.isDirected = isDirected;
    }

    // Methods
    public Vertex<T> getOrigin() { return origin; }
    public Vertex<T> getDestination() { return destination; }

    public T getValue() { return value; }
    public void setValue(T newValue) { value = newValue; }

    public boolean isDirected() { return isDirected; }

    public Vertex<T> contrary(Vertex<T> vertex) {
        if (vertex == origin) return destination;
        if (vertex == destination) return origin;

        return null;
    }

    @Override
    public String toString() {
        String connect =  isDirected ? "->" : "--";
        return origin + connect + destination + " [valor =" + value + "]";
    }
}
