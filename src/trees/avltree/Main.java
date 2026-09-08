package src.trees.avltree;

public class Main {
    private static AVLTree<Integer> tree = new AVLTree<>();
    public static void main(String[] args) {
        tree.insert(10);
        tree.insert(15);
        tree.insert(5);
        tree.insert(22);
        tree.insert(2);
        tree.insert(8);
        tree.insert(25);
        tree.remove(5);
    }
}