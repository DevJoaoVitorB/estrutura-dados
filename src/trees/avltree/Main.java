package src.trees.avltree;

public class Main {
    private static AVLTree<Integer> tree = new AVLTree<>();
    public static void main(String[] args) {
        tree.insert(1);
        tree.printTree();
        System.err.println();
        tree.insert(2);
        tree.printTree();
        System.err.println();
        tree.insert(3);
        tree.printTree();
        System.err.println();
        tree.insert(4);
        tree.printTree();
        System.err.println();
        tree.insert(5);
        tree.printTree();
        System.err.println();
        tree.insert(6);
        tree.printTree();
        System.err.println();
        tree.insert(7);
        tree.printTree();
        System.err.println();
        tree.insert(8);
        tree.printTree();
        System.err.println();
        tree.insert(9);
        tree.printTree();
        System.err.println();
        tree.insert(10);
        tree.printTree();
        System.err.println();
    }
}