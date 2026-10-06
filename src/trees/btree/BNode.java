package src.trees.btree;

import src.trees.AbstractNode;

public class BNode<T> extends AbstractNode<T, BNode<T>> {
    private T[] keys;
    private int order;
    private BNode<T>[] children;
    private int nKeys;

    @SuppressWarnings("unchecked")
    public BNode(BNode<T> parent, int order) {
        super(null, parent);
        this.order = order;
        keys = (T[]) new Object[2 * order - 1];
        children = new BNode[2 * order];
        nKeys = 0;
    }

    // Order Methods
    public int getOrder() { return order; }

    // Keys Methods
    public T[] getKeys() { return keys; }
    public T getKey(int index) { return keys[index]; }
    public void setKey(int index, T key) { keys[index] = key; }

    // Keys Number Methods
    public int getNKeys() { return nKeys; }
    public void setNKeys(int n) { nKeys = n; }
    public void incrementNKeys() { nKeys++; }
    public void decrementNKeys() { nKeys--; }

    // Children Methods
    public BNode<T>[] getChildren() { return children; }
    public BNode<T> getChild(int index) { return children[index]; }
    public void setChild(int index, BNode<T> child) { children[index] = child; }

    // Validation Methods
    public boolean isExternal() { return children[0] == null; }
    public boolean isInternal() { return !isExternal(); }
    public boolean isFull() { return nKeys == 2 * order - 1; }
}
