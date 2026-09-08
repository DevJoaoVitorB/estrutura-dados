package src.trees.searchbinarytree;

import src.trees.binary.BinaryNode;

public class SearchBinaryNode<T> extends BinaryNode<T, SearchBinaryNode<T>> {

    public SearchBinaryNode(T element, SearchBinaryNode<T> parent) { super(element, parent); }

    @Override
    protected SearchBinaryNode<T> self() { return this; }
}