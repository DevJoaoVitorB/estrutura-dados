package src.trees.btree;

public class BTree<T extends Comparable<T>>  {

    private BNode<T> root;
    private int order;

    public BTree(int order) { this.order = order; root = new BNode<>(null, order); }

    // Generic Methods
    public int size() { return size(root); }
    public int size(BNode<T> node) {
        int count = node.getNKeys();
        if (node.isExternal()) return count;

        BNode<T>[] children = node.getChildren();
        for (int i = 0; i <= node.getNKeys(); i++) { count += size(children[i]); }

        return count;
    }

    public boolean isEmpty() { return root.getNKeys() == 0; }

    // Access Methods
    public BNode<T> getRoot() { return root; }

    // Query Methods
    public boolean isRoot(BNode<T> node) { return node == root; }

    public int height(BNode<T> node) {
        if (node.isExternal()) return 0;
        return 1 + height(node.getChild(0));
    }

    public int depth(BNode<T> node) { 
        if (isRoot(node)) return 0;
        return 1 + depth(node.getParent());
    }

    public BNode<T> find(T key) { return find(root, key); }
    private BNode<T> find(BNode<T> node, T key) {
        int i = 0;
        while (i < node.getNKeys() && key.compareTo(node.getKey(i)) > 0) { i++; }

        if (i < node.getNKeys() && key.compareTo(node.getKey(i)) == 0) { return node; }
        if (node.isExternal()) { return null; }

        return find(node.getChild(i), key);
    }

    // Update Methods
    public void insert(T element) { }
    public void remove(T key) { }
}   
 