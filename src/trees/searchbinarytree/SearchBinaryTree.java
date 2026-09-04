package src.trees.searchbinarytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.trees.Tree;

public class SearchBinaryTree<T extends Comparable<T>> implements Tree<T, SearchBinaryNode<T>> {
    private SearchBinaryNode<T> root;

    public SearchBinaryTree() { root = null; }

    // Generic Methods
    public int size() { return size(root); }
    public int size(SearchBinaryNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.getLeftChild()) + size(node.getRightChild());
    }

    public boolean isEmpty() { return root == null; }

    public Iterator<T> elements() {
        List<T> elements = new ArrayList<>();
        getElements(root, elements);
        return elements.iterator();
    }

    public Iterator<SearchBinaryNode<T>> nodes() {
        List<SearchBinaryNode<T>> nodes = new ArrayList<>();
        getNodes(root, nodes);
        return nodes.iterator();
    }

    // Access Methods
    public SearchBinaryNode<T> getRoot() { return root; }

    // Query Methods
    public boolean isRoot(SearchBinaryNode<T> node) { return node == root; }

    public int height(SearchBinaryNode<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    public int depth(SearchBinaryNode<T> node) {
        if (isRoot(node)) return 0;
        return 1 + depth(node.getParent());
    }

    public SearchBinaryNode<T> find(T key) { return find(root, key); }
    public SearchBinaryNode<T> find(SearchBinaryNode<T> node, T key) {
        if (node == null || key.compareTo(node.getElement()) == 0) return node;

        if (key.compareTo(node.getElement()) < 0) return find(node.getLeftChild(), key);
        else return find(node.getRightChild(), key);
    }

    // Update Methods
    public void insert(T element) { root = insertOperation(root, element, null); }

    public void remove(T key) { root = removeOperation(root, key); }

    // Traversal Methods
    public void preOrder() { preOrder(root); }
    private void preOrder(SearchBinaryNode<T> node) {
        if (node == null) return;

        System.out.print(node.getElement() + " ");
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    public void inOrder() { inOrder(root); }
    private void inOrder(SearchBinaryNode<T> node) {
        if (node == null) return;

        inOrder(node.getLeftChild());
        System.out.print(node.getElement() + " ");
        inOrder(node.getRightChild());
    }

    public void postOrder() { postOrder(root); }
    private void postOrder(SearchBinaryNode<T> node) {
        if (node == null) return;

        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        System.out.print(node.getElement() + " ");
    }

    // Print Tree
    public void printTree() {
        int height = height(root);
        int width = (int) Math.pow(2, height + 2);

        List<StringBuilder> lines = new ArrayList<>();

        for (int i = 0; i <= height; i++)
            lines.add(new StringBuilder(" ".repeat(width)));

        printTree(root, lines, 0, width / 2, width / 4);

        for (StringBuilder line : lines)
            System.out.println(line.toString().stripTrailing());
    }
    private void printTree(
            SearchBinaryNode<T> node,
            List<StringBuilder> lines,
            int level,
            int position,
            int shift) {

        if (node == null || level >= lines.size())
            return;

        String element = node.getElement().toString();
        int start = position - element.length() / 2;

        lines.get(level).replace(start, start + element.length(), element);

        printTree(
            node.getLeftChild(),
            lines,
            level + 1,
            position - shift,
            shift / 2
        );

        printTree(
            node.getRightChild(),
            lines,
            level + 1,
            position + shift,
            shift / 2
        );
    }

    // Helpers
    private void getElements(SearchBinaryNode<T> node, List<T> elements) {
        if (node == null) return;

        getElements(node.getLeftChild(), elements);
        elements.add(node.getElement());
        getElements(node.getRightChild(), elements);
    }

    private void getNodes(SearchBinaryNode<T> node, List<SearchBinaryNode<T>> nodes) {
        if (node == null) return;

        getNodes(node.getLeftChild(), nodes);
        nodes.add(node);
        getNodes(node.getRightChild(), nodes);
    }

    private SearchBinaryNode<T> insertOperation(SearchBinaryNode<T> node, T element, SearchBinaryNode<T> parent) {
        if (node == null) return new SearchBinaryNode<T>(element, parent);

        if (element.compareTo(node.getElement()) < 0) node.setLeftChild(insertOperation(node.getLeftChild(), element, node));
        else node.setRightChild(insertOperation(node.getRightChild(), element, node));

        return node;
    }

    private SearchBinaryNode<T> removeOperation(SearchBinaryNode<T> node, T key) {
        if(node == null) return null;

        if (key.compareTo(node.getElement()) < 0) node.setLeftChild(removeOperation(node.getLeftChild(), key));
        else if (key.compareTo(node.getElement()) > 0) node.setRightChild(removeOperation(node.getRightChild(), key));
        else {
            if (node.getLeftChild() == null) return node.getRightChild();
            if (node.getRightChild() == null) return node.getLeftChild();

            SearchBinaryNode<T> successor = successor(node.getRightChild());
            node.setElement(successor.getElement());
            node.setRightChild(removeOperation(node.getRightChild(), successor.getElement()));
        }

        return node;
    }

    private SearchBinaryNode<T> successor(SearchBinaryNode<T> node) {
        while (node.getLeftChild() != null) node = node.getLeftChild();
        return node;
    }
}
