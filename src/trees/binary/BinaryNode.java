package src.trees.binary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.trees.AbstractNode;

public abstract class BinaryNode<
    T, 
    N extends BinaryNode<T, N>
> extends AbstractNode<T, N> {

    private N leftChild;
    private N rightChild;

    public BinaryNode(T element, N parent) { super(element, parent); }

    // Self Method - return this node
    protected abstract N self();

    // Children Methods
    public N getLeftChild() { return leftChild; }
    public void setLeftChild(N newLeftChild) {
        leftChild = newLeftChild;
        if (newLeftChild != null) newLeftChild.setParent(self());
    }

    public N getRightChild() { return rightChild; }
    public void setRightChild(N newRightChild) {
        rightChild = newRightChild;
        if (newRightChild != null) newRightChild.setParent(self());
    }

    public Iterator<N> getChildren() {
        List<N> children = new ArrayList<>();
        if (getLeftChild() != null) children.add(leftChild);
        if (getRightChild() != null) children.add(rightChild);
        return children.iterator();
    }

    // Validation Methods
    public boolean isExternal() { return leftChild == null && rightChild == null; }
    public boolean isInternal() { return !isExternal(); }
}