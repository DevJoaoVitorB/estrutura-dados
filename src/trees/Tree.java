package src.trees;

import java.util.Iterator;

public interface Tree<T, N> {
    // Generic Methods
    int size();
    boolean isEmpty();
    Iterator<T> elements();
    Iterator<N> nodes();

    // Access Methods
    N getRoot();

    // Query Methods
    boolean isRoot(N node);
    int height(N node);
    int depth(N node);

    // Traversal Methods
    void preOrder();
    void postOrder();
}

