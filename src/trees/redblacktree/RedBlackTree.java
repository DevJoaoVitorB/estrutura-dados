package src.trees.redblacktree;

import src.trees.binary.BinaryTree;

public class RedBlackTree<T extends Comparable<T>> extends BinaryTree<T, RedBlackNode<T>> {

    // Hooks
    @Override 
    protected RedBlackNode<T> createNode(T element, RedBlackNode<T> parent) {
        return new RedBlackNode<>(element, parent);
    }

    @Override
    protected void updateAfterInsert(RedBlackNode<T> parent, boolean wasLeftChild) {
        if (parent == null) { return; }

        // Case 1 - Parent is BLACK - OK!
        if (parent.getColor() == Color.BLACK) { return; }

        // Grandfather and Uncle
        RedBlackNode<T> grandfather = parent.getParent();
        RedBlackNode<T> uncle = grandfather.getLeftChild() == parent ? grandfather.getRightChild() : grandfather.getLeftChild();

        // Case 2 - Parent and Uncle are Red - Paint Parent and Uncle Black, and Grandfather Red
        if (uncle != null && uncle.getColor() == Color.RED) {
            parent.setColor(Color.BLACK);
            uncle.setColor(Color.BLACK);
            grandfather.setColor(Color.RED);

            if (isRoot(grandfather)) { grandfather.setColor(Color.BLACK); return; }

            updateAfterInsert(grandfather.getParent(), grandfather.getParent().getLeftChild() == grandfather ? true : false);
            return;
        }

        // Case 3 - Parent is Red, Uncle and Grandfather are Black - Rotate
        // Inserted Node
        RedBlackNode<T> insertedNode = wasLeftChild ? parent.getLeftChild() : parent.getRightChild();

        System.out.println("\n=== Tree Unbalanced At %s ===\n".formatted(insertedNode.getElement().toString()));
        printTree();

        // Case 3a - Left Simple Rotate
        if (!wasLeftChild && grandfather.getRightChild() == parent) {
            grandfather.setColor(Color.RED);
            parent.setColor(Color.BLACK);
            rotateLeft(grandfather);
            return;
        }

        // Case 3b - Right Simple Rotate
        if (wasLeftChild && grandfather.getLeftChild() == parent) {
            grandfather.setColor(Color.RED);
            parent.setColor(Color.BLACK);
            rotateRight(grandfather);
            return;
        }

        // Case 3c - Left Double Rotate
        if (!wasLeftChild && grandfather.getLeftChild() == parent) {
            rotateLeft(parent);
            insertedNode.setColor(Color.BLACK);
            grandfather.setColor(Color.RED);
            rotateRight(grandfather);
            return;
        }
        
        // Case 3d - Right Double Rotate
        if (wasLeftChild && grandfather.getRightChild() == parent) {
            rotateRight(parent);
            insertedNode.setColor(Color.BLACK);
            grandfather.setColor(Color.RED);
            rotateLeft(grandfather);
        }
    }

    @Override 
    protected void updateAfterRemove(RedBlackNode<T> parent, boolean wasLeftChild) {}

    // Helpers
    private void rotateLeft(RedBlackNode<T> node) {
        RedBlackNode<T> rightChild = node.getRightChild();
        super.rotateLeft(node, rightChild);

        System.out.println("\n=== Tree After Left Rotation ===\n");
        printTree();
    }

    private void rotateRight(RedBlackNode<T> node) {
        RedBlackNode<T> leftChild = node.getLeftChild();
        super.rotateRight(node, leftChild);

        System.out.println("\n=== Tree After Right Rotation ===\n");
        printTree();
    }

    @Override
    protected String nodeLabel(RedBlackNode<T> node) {
        String color = node.getColor() == Color.BLACK ? "B" : "R";
        return "%s[%s]".formatted(node.getElement(), color);
    }
}
