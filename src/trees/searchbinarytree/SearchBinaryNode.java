package src.trees.searchbinarytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.trees.AbstractNode;

public class SearchBinaryNode<T> extends AbstractNode<T, SearchBinaryNode<T>> {
    
    private SearchBinaryNode<T> leftChild;
    private SearchBinaryNode<T> rightChild;

    public SearchBinaryNode(T element, SearchBinaryNode<T> parent) { super(element, parent); }

    // Children Methods 
    public SearchBinaryNode<T> getLeftChild() { return leftChild; }
    public void setLeftChild(SearchBinaryNode<T> newLeftChild) { 
        leftChild = newLeftChild; 
        if (newLeftChild != null) newLeftChild.setParent(this);
    }

    public SearchBinaryNode<T> getRightChild() { return rightChild; }
    public void setRightChild(SearchBinaryNode<T> newRightChild) { 
        rightChild = newRightChild; 
        if (newRightChild != null) newRightChild.setParent(this);
    }

    public Iterator<SearchBinaryNode<T>> getChildren() {
        List<SearchBinaryNode<T>> children = new ArrayList<>();

        if (getLeftChild() != null) children.add(leftChild);
        if (getRightChild() != null) children.add(rightChild);

        return children.iterator();
    }

    // Validation Methods
    public boolean isExternal() { return leftChild == null && rightChild == null; }
    public boolean isInternal() { return !isExternal(); }
}
