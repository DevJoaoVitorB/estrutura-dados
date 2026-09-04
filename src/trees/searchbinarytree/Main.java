package src.trees.searchbinarytree;

public class Main {
    private static SearchBinaryTree<Integer> tree = new SearchBinaryTree<>();

    public static void main(String[] args) {
        tree.insert(8);
        tree.insert(3);
        tree.insert(10);
        tree.insert(1);
        tree.insert(6);
        tree.insert(4);
        tree.insert(7);
        tree.insert(14);
        tree.insert(13);

        System.out.println("Tree size: " + tree.size());

        System.out.println("\nTree structure:");
        tree.printTree();

        System.out.print("\nElements in order (InOrder): ");
        tree.inOrder();
        System.out.println();

        System.out.print("\nElements in pre-order (PreOrder): ");
        tree.preOrder();
        System.out.println();

        System.out.print("\nElements in post-order (PostOrder): ");
        tree.postOrder();
        System.out.println();

        int[] searches = {6, 15};

        for (int key : searches) {
            SearchBinaryNode<Integer> node = tree.find(key);

            System.out.println(node != null
                    ? "Element " + key + " found."
                    : "Element " + key + " not found.");
        }

        SearchBinaryNode<Integer> root = tree.getRoot();

        if (root != null) {
            System.out.println(
                    "\nRoot height (" + root.getElement() + "): "
                    + tree.height(root)
            );

            SearchBinaryNode<Integer> node14 = tree.find(14);

            if (node14 != null) {
                System.out.println(
                        "Depth of node 14: " + tree.depth(node14)
                );
            }
        }

        System.out.println("\nCASE 1 - Leaf node (4)");
        tree.remove(4);
        tree.printTree();

        System.out.println("\nCASE 2 - Node with one child (6)");
        tree.remove(6);
        tree.printTree();

        System.out.println("\nCASE 3 - Node with two children (8)");
        tree.remove(8);
        tree.printTree();
    }
}
