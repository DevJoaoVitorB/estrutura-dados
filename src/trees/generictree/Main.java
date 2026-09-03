package src.trees.generictree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static GenericTree<String> tree = new GenericTree<>("A");

    public static void main(String[] args) {
        GenericNode<String> root = tree.getRoot();

        tree.addChild(root, "B");
        tree.addChild(root, "C");

        Iterator<GenericNode<String>> rootChildren = root.getChildren();
        List<GenericNode<String>> children = new ArrayList<>();

        while (rootChildren.hasNext()) {
            children.add(rootChildren.next());
        }

        tree.addChild(children.get(0), "D");
        tree.addChild(children.get(0), "E");

        System.out.println("Tree size: " + tree.size());

        System.out.println("Root height: " + tree.height(root));

        Iterator<GenericNode<String>> iteratorE = children.get(0).getChildren();

        GenericNode<String> nodeD = iteratorE.next();
        GenericNode<String> nodeE = iteratorE.next();

        System.out.println(
            "Depth of node " + nodeE.getElement() + ": " + tree.depth(nodeE)
        );

        System.out.print("Tree elements: ");

        Iterator<String> elements = tree.elements();

        while (elements.hasNext()) {
            System.out.print(elements.next() + " ");
        }

        System.out.println();

        String removed = tree.remove(nodeE);

        System.out.println(
            "Removed element: " + (removed != null ? removed : "null")
        );

        System.out.println("Size after removal: " + tree.size());

        tree.swapElement(children.get(0), children.get(1));

        System.out.println("Elements after swapping B and C:");

        elements = tree.elements();

        while (elements.hasNext()) {
            System.out.print(elements.next() + " ");
        }

        System.out.println();

        String oldElement = tree.replace(nodeD, "X");

        System.out.println("Replaced element: " + oldElement);

        System.out.print("Pre-order: ");
        tree.preOrder();
        System.out.println();

        System.out.print("Post-order: ");
        tree.postOrder();
        System.out.println();
    }
}