package src.graphs.graphadt;

import java.util.ArrayList;

public class Graph<T> {
    private final boolean isMultigraph;
    private final ArrayList<Vertex<T>> vertex = new ArrayList<>();

    // Matriz[Linha][Coluna] - Armazena uma lista de arestas
    private final ArrayList<ArrayList<ArrayList<Edge<T>>>> matrix = new ArrayList<>();

    public Graph(boolean isMultigraph) { this.isMultigraph = isMultigraph; }

    // Methods
}
