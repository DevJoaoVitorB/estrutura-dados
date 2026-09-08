package src.trees.avltree;

import src.trees.binary.BinaryTree;

public class AVLTree<T extends Comparable<T>> extends BinaryTree<T, AVLNode<T>> {

    // Hooks
    @Override
    protected AVLNode<T> createNode(T element, AVLNode<T> parent) { 
        return new AVLNode<>(element, parent);
    }

    @Override
    protected void rebalanceInsert(AVLNode<T> parent, boolean wasLeftChild) {
        AVLNode<T> current = parent;
        boolean isLeftChild = wasLeftChild;

        while(current != null) {
            current.setBalanceFactor(current.getBalanceFactor() + (isLeftChild ? 1 : -1));
            int factor = current.getBalanceFactor();

            if (factor == 0) return;

            if (factor > 1) {
                AVLNode<T> left = current.getLeftChild();
                if (left.getBalanceFactor() < 0) rotateLeft(left);
                rotateRight(current);
                return;
            }

            if (factor < -1) {
                AVLNode<T> right = current.getRightChild();
                if (right.getBalanceFactor() > 0) rotateRight(right);
                rotateLeft(current);
                return;
            }

            AVLNode<T> next = current.getParent();

            if (next == null) return;
            
            isLeftChild = next.getLeftChild() == current;
            current = next;
        }
    }

    @Override
    protected void rebalanceRemove(AVLNode<T> parent, boolean wasLeftChild) { }

    // Helpers
    private void rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightChild = node.getRightChild();

        node.setRightChild(rightChild.getLeftChild());
        replaceInParent(node, rightChild);
        rightChild.setLeftChild(node);

        updateBalanceFactor(node);
        updateBalanceFactor(rightChild);
    }

    private void rotateRight(AVLNode<T> node) { 
        AVLNode<T> leftChild = node.getLeftChild();

        node.setLeftChild(leftChild.getRightChild());
        replaceInParent(node, leftChild);
        leftChild.setRightChild(node);

        updateBalanceFactor(node);
        updateBalanceFactor(leftChild);
    }

    private void updateBalanceFactor(AVLNode<T> node) {
        node.setBalanceFactor(height(node.getLeftChild()) - height(node.getRightChild()));
    }
    
    @Override
    protected String nodeLabel(AVLNode<T> node) {
        return "%s[%d]".formatted(node.getElement(), node.getBalanceFactor());
    }
}