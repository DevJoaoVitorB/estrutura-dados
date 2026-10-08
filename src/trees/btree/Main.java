package src.trees.btree;

public class Main {
    private static BTree<Integer> tree = new BTree<>(2);

    public static void main(String[] args) {
        tree.insert(10);
        tree.insert(15);
        tree.insert(5);
        tree.insert(22);
        tree.insert(2);
        tree.insert(8);
        tree.insert(25);
        tree.insert(30);
        tree.insert(1);
        tree.remove(5);
    }
}