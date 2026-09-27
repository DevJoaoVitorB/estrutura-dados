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
        if (isBlack(parent)) { return; }

        // Grandfather and Uncle
        RedBlackNode<T> grandfather = parent.getParent();
        RedBlackNode<T> uncle = grandfather.getLeftChild() == parent ? grandfather.getRightChild() : grandfather.getLeftChild();

        // Case 2 - Parent and Uncle are Red - Paint Parent and Uncle Black, and Grandfather Red
        if (isRed(uncle)) {
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
    protected void updateAfterRemove(RedBlackNode<T> removedNode, RedBlackNode<T> parent, boolean wasLeftChild, RedBlackNode<T> realNode) {
        /*
            * removedNode - Node physically removed from the tree (Can be the Successor). (X)
            * realNode    - Actual node whose key is replaced. (V)
        */

        // Situation 1 - Removed and Real Nodes are Red - OK!
        if (isRed(removedNode, realNode)) { return; }

        // Situation 2 - Removed Node is Red and Real Node is Black - Paint the Real Node Black
        if (isRed(removedNode) && isBlack(realNode)) { realNode.setColor(Color.BLACK); return; }

        // Situation 4 - Removed Node is Black and Real Node is Red - Paint the Real Node Red
        if (isBlack(removedNode) && isRed(realNode)) { realNode.setColor(Color.RED); }

        // Situation 3 - Removed and Real Node are Black
        RedBlackNode<T> current = parent;
        boolean isLeftChild = wasLeftChild;

        while(current != null) {
            RedBlackNode<T> brother = isLeftChild ? current.getRightChild() : current.getLeftChild();

            if (brother == null) { current = current.getParent(); continue; }

            // Removed Node's Nephews Near and Far
            RedBlackNode<T> nearNephew = isLeftChild 
                ? brother.getLeftChild() 
                : brother.getRightChild();
            RedBlackNode<T> farNephew = isLeftChild
                ? brother.getRightChild()
                : brother.getLeftChild();

             // Case 2b - Brother and Nephews are Black, Parent is Red
            if (isRed(current) && isBlack(removedNode, brother, nearNephew, farNephew)) {
                brother.setColor(Color.RED);
                current.setColor(Color.BLACK);
                return;
            }

            // Case 4 - Brother is Black and Right Nephew Red
            if (isBlack(removedNode, brother) && isRed(farNephew)) {
                Color parentColor = current.getColor();
                
                if (brother == current.getLeftChild()) rotateRight(current);
                else rotateLeft(current);

                brother.setColor(parentColor);
                current.setColor(Color.BLACK);
                farNephew.setColor(Color.BLACK);
                return;
            }
            
            // Case 1 - Brother is Red and Parent is Black
            if (isBlack(removedNode, current) && isRed(brother)) {
                if (brother == current.getLeftChild()) rotateRight(current);
                else rotateLeft(current);

                brother.setColor(Color.BLACK);
                current.setColor(Color.RED);
                continue;
            }

            // Case 2a - Family are Black
            if (isBlack(current, removedNode, brother, nearNephew, farNephew)) {
                brother.setColor(Color.RED);

                RedBlackNode<T> oldCurrent = current;
                current = current.getParent();
                if (current != null) isLeftChild = current.getLeftChild() == oldCurrent;

                continue;
            }

            // Case 3 - Brothers Black, Left Nephew Red and Right Nephew Black
            if (isBlack(removedNode, brother, farNephew) && isRed(nearNephew)) {
                if (nearNephew == brother.getLeftChild()) rotateRight(brother);
                else rotateLeft(brother);

                brother.setColor(Color.RED);
                nearNephew.setColor(Color.BLACK);

                continue;
            }
        }
    }

    // Helpers
    @SafeVarargs
    private boolean isBlack(RedBlackNode<T>... nodes) {
        for (RedBlackNode<T> node : nodes) { if (node != null && node.getColor() == Color.RED) return false; }
        return true;
    }

    @SafeVarargs
    private boolean isRed(RedBlackNode<T>... nodes) {
        for (RedBlackNode<T> node : nodes) { if (node == null || node.getColor() == Color.BLACK) return false; }
        return true;
    }

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
