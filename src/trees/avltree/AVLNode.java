package src.trees.avltree;

import src.trees.binary.BinaryNode;

public class AVLNode<T> extends BinaryNode<T, AVLNode<T>> {
    private int balanceFactor;

    public AVLNode(T element, AVLNode<T> parent) {
        super(element, parent);
        balanceFactor = 0;
    }

    @Override
    protected AVLNode<T> self() { return this; }

    // Balance Factor Methods
    public int getBalanceFactor() { return balanceFactor; }
    public void setBalanceFactor(int newBalanceFactor) { balanceFactor = newBalanceFactor; }
}