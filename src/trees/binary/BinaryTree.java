package src.trees.binary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.trees.Tree;

public abstract class BinaryTree<
    T extends Comparable<T>, 
    N extends BinaryNode<T, N>
> implements Tree<T, N> {

    private N root;

    public BinaryTree() { root = null; }

    // Generic Methods
    public int size() { return size(root); }
    public int size(N node) {
        if (node == null) return 0;
        return 1 + size(node.getLeftChild()) + size(node.getRightChild());
    }

    public boolean isEmpty() { return root == null; }

    public Iterator<T> elements() {
        List<T> elements = new ArrayList<>();
        getElements(root, elements);
        return elements.iterator();
    }

    public Iterator<N> nodes() {
        List<N> nodes = new ArrayList<>();
        getNodes(root, nodes);
        return nodes.iterator();
    }

    // Access Methods
    public N getRoot() { return root; }
    private void setRoot(N newRoot) { root = newRoot; }

    // Query Methods
    public boolean isRoot(N node) { return node == root; }

    public int height(N node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    public int depth(N node) {
        if (isRoot(node)) return 0;
        return 1 + depth(node.getParent());
    }

    public N find(T key) { return find(getRoot(), key); }
    public N find(N node, T key) {
        if (node == null || key.compareTo(node.getElement()) == 0) return node;
        if (key.compareTo(node.getElement()) < 0) return find(node.getLeftChild(), key);
        else return find(node.getRightChild(), key);
    }

    // Update Methods
    public void insert(T element) {
        if (getRoot() == null) {
            setRoot(createNode(element, null));
            return;
        }

        System.out.println("\n=== BEFORE INSERTING %s ===\n".formatted(element.toString()));
        printTree();

        insertOperation(getRoot(), element);

        System.out.println("\n=== AFTER INSERTING %s ===\n".formatted(element.toString()));
        printTree();
    }

    public void remove(T key) {
        N node = find(key);
        if (node == null) { 
            System.out.println("Element not found.");
            return;
        }

        System.out.println("\n=== BEFORE REMOVING %s ===\n".formatted(key.toString()));
        printTree();

        removeOperation(node);
        
        System.out.println("\n=== AFTER REMOVING %s ===\n".formatted(key.toString()));
        printTree();
    }

    // Traversal Methods
    public void preOrder() { preOrder(root); }
    private void preOrder(N node) {
        if (node == null) return;
        System.out.print(node.getElement() + " ");
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    public void inOrder() { inOrder(root); }
    private void inOrder(N node) {
        if (node == null) return;
        inOrder(node.getLeftChild());
        System.out.print(node.getElement() + " ");
        inOrder(node.getRightChild());
    }

    public void postOrder() { postOrder(root); }
    private void postOrder(N node) {
        if (node == null) return;
        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        System.out.print(node.getElement() + " ");
    }

    // Hooks
    protected abstract N createNode(T element, N parent);

    protected void rebalanceInsert(N parent, boolean wasLeftChild) { return; }
    
    protected void rebalanceRemove(N parent, boolean wasLeftChild) { return; }

    // Print Tree
    public void printTree() {
        N root = getRoot();
        int height = height(root);
        int width = (int) Math.pow(2, height + 2) * 2;
        List<StringBuilder> lines = new ArrayList<>();
        for (int i = 0; i <= height; i++) lines.add(new StringBuilder(" ".repeat(width)));
        printTree(root, lines, 0, width / 2, width / 4);
        for (StringBuilder line : lines) System.out.println(line.toString().stripTrailing());
    }

    private void printTree(N node, List<StringBuilder> lines, int level, int position, int shift) {
        if (node == null || level >= lines.size()) return;

        String label = nodeLabel(node);
        int start = position - label.length() / 2;
        lines.get(level).replace(start, start + label.length(), label);

        printTree(node.getLeftChild(), lines, level + 1, position - shift, shift / 2);
        printTree(node.getRightChild(), lines, level + 1, position + shift, shift / 2);
    }

    // Helpers
    private void getElements(N node, List<T> elements) {
        if (node == null) return;
        getElements(node.getLeftChild(), elements);
        elements.add(node.getElement());
        getElements(node.getRightChild(), elements);
    }

    private void getNodes(N node, List<N> nodes) {
        if (node == null) return;
        getNodes(node.getLeftChild(), nodes);
        nodes.add(node);
        getNodes(node.getRightChild(), nodes);
    }

    private void insertOperation(N node, T element) {
        boolean goLeft = element.compareTo(node.getElement()) < 0;
        N child = goLeft ? node.getLeftChild() : node.getRightChild();
        
        if (child != null) {
            insertOperation(child, element);
            return;
        }
        
        N newNode = createNode(element, node);
        if (goLeft) node.setLeftChild(newNode);
        else node.setRightChild(newNode);

        rebalanceInsert(node, goLeft);
    }

    private void removeOperation(N node) {
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            N successor = node.getRightChild();
            while (successor.getLeftChild() != null) successor = successor.getLeftChild();

            node.setElement(successor.getElement());
            removeOperation(successor);
            return;
        }
        
        N child = node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild();
        N parent = node.getParent();
        boolean wasLeftChild = parent != null && parent.getLeftChild() == node;

        replaceInParent(node, child);
        rebalanceRemove(parent, wasLeftChild);
    }

    protected void replaceInParent(N node, N replacement) {
        N parent = node.getParent();

        if (parent == null) setRoot(replacement);
        else if (parent.getLeftChild() == node) parent.setLeftChild(replacement);
        else parent.setRightChild(replacement);

        if (parent == null && replacement != null) replacement.setParent(null);
    }

    protected String nodeLabel(N node) { return node.getElement().toString(); }
}