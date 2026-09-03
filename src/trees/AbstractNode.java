package src.trees;

public abstract class AbstractNode<T, N extends AbstractNode<T, N>> {
    private T element;
    private N parent;

    public AbstractNode(T element) { this.element = element; }

    // Get and Set Methods
    public T getElement() { return element; }
    public void setElement(T newElement) { element = newElement; }

    public N getParent() { return parent; }
    public void setParent(N newParent) { parent = newParent; }

    // Validation Methods
    public abstract boolean isExternal();
    public abstract boolean isInternal();
}
