package src.trees.searchbinarytree;

import src.trees.binary.BinaryTree;

public class SearchBinaryTree<T extends Comparable<T>> extends BinaryTree<T, SearchBinaryNode<T>> {

    // Hooks
    @Override
    protected SearchBinaryNode<T> createNode(T element, SearchBinaryNode<T> parent) { 
        return new SearchBinaryNode<>(element, parent); 
    }
}