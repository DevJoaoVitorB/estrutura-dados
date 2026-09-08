package src.trees.avltree;

import src.trees.binary.BinaryTree;

public class AVLTree<T extends Comparable<T>> extends BinaryTree<T, AVLNode<T>> {

    @Override
    protected AVLNode<T> createNode(T element, AVLNode<T> parent) { 
        return new AVLNode<>(element, parent);
    }

    @Override
    protected void rebalanceInsert(AVLNode<T> parent, boolean wasLeftChild) { }

    @Override
    protected void rebalanceRemove(AVLNode<T> parent, boolean wasLeftChild) { }

    // Helpers
    private AVLNode<T> rotateRight(AVLNode<T> node) { return null; }

    private AVLNode<T> rotateLeft(AVLNode<T> node) { return null; }

    @Override
    protected String nodeLabel(AVLNode<T> node) {
        return "%s[%d]".formatted(node.getElement(), node.getBalanceFactor());
    }
}