package src.trees.avltree;

import src.trees.binary.BinaryTree;

public class AVLTree<T extends Comparable<T>> extends BinaryTree<T, AVLNode<T>> {

    // Hooks
    @Override
    protected AVLNode<T> createNode(T element, AVLNode<T> parent) { 
        return new AVLNode<>(element, parent);
    }

    @Override
    protected void updateAfterInsert(AVLNode<T> parent, boolean wasLeftChild) {
        AVLNode<T> current = parent;
        boolean isLeftChild = wasLeftChild;

        while(current != null) {
            current.setBalanceFactor(current.getBalanceFactor() + (isLeftChild ? 1 : -1));
            int factor = current.getBalanceFactor();

            if (factor == 0) return;
            
            if (factor > 1 || factor < -1) {
                rebalance(current, factor);
                return;
            }

            AVLNode<T> next = current.getParent();

            if (next == null) return;
            
            isLeftChild = next.getLeftChild() == current;
            current = next;
        }
    }

    @Override
    protected void updateAfterRemove(AVLNode<T> parent, boolean wasLeftChild) {
        AVLNode<T> current = parent;
        boolean isLeftChild = wasLeftChild;

        while (current != null) {
            current.setBalanceFactor(current.getBalanceFactor() + (isLeftChild ? -1 : 1));
            int factor = current.getBalanceFactor();
            
            if (factor != 0) return;

            if (factor > 1 || factor < -1) {
                rebalance(current, factor);
                return;
            }

            AVLNode<T> next = current.getParent();
            if (next == null) return;
            isLeftChild = next.getLeftChild() == current;
            current = next;
        }
    }

    private void rebalance(AVLNode<T> node, int factor) {
        if (factor > 1) {
            System.out.println("\n=== Tree Unbalanced At %s ===\n".formatted(node.getElement().toString()));
            printTree();

            AVLNode<T> left = node.getLeftChild();
            if (left.getBalanceFactor() < 0) rotateLeft(left);
            rotateRight(node);
            return;
        }

        if (factor < -1) {
            System.out.println("\n=== Tree Unbalanced At %s ===\n".formatted(node.getElement().toString()));
            printTree();

            AVLNode<T> right = node.getRightChild();
            if (right.getBalanceFactor() > 0) rotateRight(right);
            rotateLeft(node);
            return;
        }
    }

    // Helpers
    private void rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightChild = node.getRightChild();

        node.setRightChild(rightChild.getLeftChild());
        replaceInParent(node, rightChild);
        rightChild.setLeftChild(node);

        node.setBalanceFactor(node.getBalanceFactor() + 1 - Math.min(rightChild.getBalanceFactor(), 0));
        rightChild.setBalanceFactor(rightChild.getBalanceFactor() + 1 + Math.max(node.getBalanceFactor(), 0));

        System.out.println("\n=== Tree After Left Rotation ===\n");
        printTree();
    }

    private void rotateRight(AVLNode<T> node) {
        AVLNode<T> leftChild = node.getLeftChild();

        node.setLeftChild(leftChild.getRightChild());
        replaceInParent(node, leftChild);
        leftChild.setRightChild(node);

        node.setBalanceFactor(node.getBalanceFactor() - 1 - Math.max(leftChild.getBalanceFactor(), 0));
        leftChild.setBalanceFactor(leftChild.getBalanceFactor() - 1 + Math.min(node.getBalanceFactor(), 0));

        System.out.println("\n=== Tree After Right Rotation ===\n");
        printTree();
    }
    
    @Override
    protected String nodeLabel(AVLNode<T> node) {
        return "%s[%d]".formatted(node.getElement(), node.getBalanceFactor());
    }
}