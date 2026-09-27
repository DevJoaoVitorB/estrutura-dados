package src.trees.redblacktree;
public class Main {

    private static RedBlackTree<Integer> tree = new RedBlackTree<>();

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
