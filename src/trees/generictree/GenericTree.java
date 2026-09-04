package src.trees.generictree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.trees.Tree;

public class GenericTree<T> implements Tree<T, GenericNode<T>> {

    private GenericNode<T> root;

    public GenericTree(T element) { root = new GenericNode<>(element, null); }

    // Generic Methods
    public int size() { return size(root); }
    public int size(GenericNode<T> node) {
        if (node == null) return 0;

        int contador = 1;
        Iterator<GenericNode<T>> iterator = node.getChildren();
        while(iterator.hasNext()) contador += size(iterator.next());

        return contador;
    }

    public boolean isEmpty() { return root == null; }

    public Iterator<T> elements() {
        List<T> elements = new ArrayList<>();
        getElements(root, elements);
        return elements.iterator();
    }

    public Iterator<GenericNode<T>> nodes() { 
        List<GenericNode<T>> nodes = new ArrayList<>();
        getNodes(root, nodes);
        return nodes.iterator();
     }

    // Access Methods
    public GenericNode<T> getRoot() { return root; }

    // Query Methods
    public boolean isRoot(GenericNode<T> node) { return node == root; }

    public int height(GenericNode<T> node) {
        int height;
        Iterator<GenericNode<T>> iterator;

        if (node.isExternal()) return 0;

        height = 0;
        iterator = node.getChildren();
        while (iterator.hasNext()) {
            GenericNode<T> child = iterator.next();
            height = Math.max(height, height(child));
        }
        
        return 1 + height;
    }

    public int depth(GenericNode<T> node) {
        if (isRoot(node)) return 0;
        return 1 + depth(node.getParent());
    }

    // Update Methods
    public void addChild(GenericNode<T> parent, T newElement) {
        GenericNode<T> newChild = new GenericNode<>(newElement, parent);
        parent.addChild(newChild);
    }

    public T remove(GenericNode<T> node) {
        GenericNode<T> parent = node.getParent();

        if (!isRoot(node) && node.isExternal()) {
            parent.removeChild(node);
            return node.getElement();
        }

        return null;
    }

    public void swapElement(GenericNode<T> node1, GenericNode<T> node2) {
        T aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    public T replace(GenericNode<T> node, T newElement) {
        T oldElement = node.getElement();
        node.setElement(newElement);
        return oldElement;
    }

    // Traversal Methods
    public void preOrder() { preOrder(root); }
    private void preOrder(GenericNode<T> node) {
        if (node == null) return;

        System.out.print(node.getElement() + " ");
        Iterator<GenericNode<T>> iterator = node.getChildren();
        while (iterator.hasNext()) preOrder(iterator.next());
    }

    public void postOrder() { postOrder(root); }
    private void postOrder(GenericNode<T> node) {
        if (node == null) return;

        Iterator<GenericNode<T>> iterator = node.getChildren();
        while (iterator.hasNext()) postOrder(iterator.next());
        System.out.print(node.getElement() + " ");
    }

    // Helpers
    private void getElements(GenericNode<T> node, List<T> elements) {
        elements.add(node.getElement());
        Iterator<GenericNode<T>> iterator = node.getChildren();
        while(iterator.hasNext()) getElements(iterator.next(), elements);
    }

    private void getNodes(GenericNode<T> node, List<GenericNode<T>> nodes) {
        nodes.add(node);
        Iterator<GenericNode<T>> iterator = node.getChildren();
        while(iterator.hasNext()) getNodes(iterator.next(), nodes);
    }
}
