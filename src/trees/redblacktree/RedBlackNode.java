package src.trees.redblacktree;
import src.trees.binary.BinaryNode;

public class RedBlackNode<T> extends BinaryNode<T, RedBlackNode<T>>{
    private Color color;

    public RedBlackNode(T element, RedBlackNode<T> parent) { 
        super(element, parent);

        // Default: Create a red node or a black root
        if (parent == null) setColor(Color.BLACK);
        else setColor(Color.RED);
    }

    @Override
    protected RedBlackNode<T> self() { return this; }

    // Colors Methods
    public Color getColor() { return color; }
    public void setColor(Color newColor) { color = newColor; }
}