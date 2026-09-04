package src.trees.generictree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.trees.AbstractNode;

public class GenericNode<T> extends AbstractNode<T, GenericNode<T>> {

    private List<GenericNode<T>> children;

    public GenericNode(T element, GenericNode<T> parent) {
        super(element, parent);
        children = new ArrayList<>();
    }

    // Children Methods
    public Iterator<GenericNode<T>> getChildren() { return children.iterator(); }
    public void addChild(GenericNode<T> newChild) { children.add(newChild); }
    public void removeChild(GenericNode<T> child) { children.remove(child); }
    public int countChildren() { return children.size(); }

    // Validation Methods
    public boolean isExternal() { return countChildren() == 0; }
    public boolean isInternal() { return countChildren() > 0; }
}
