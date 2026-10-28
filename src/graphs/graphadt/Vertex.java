package src.graphs.graphadt;

public class Vertex<T> {
    private T value;

    public Vertex(T value) { this.value = value; }

    // Methods
    public T getValue() { return value; }
    public void setValue(T newValue) { value = newValue; }

    @Override
    public String toString() { return String.valueOf(value); }
}
