package functional;

public class Node<E> {
    private E element;
    private Node nextNode;
    private Node prevNode;

    public Node(E element) {
        this(element, null, null);
    }

    public Node(E element, Node<E> prevNode, Node<E> nextNode) {
        this.element = element;
        this.nextNode = nextNode;
        this.prevNode = prevNode;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<E> nextNode) {
        this.nextNode = nextNode;
    }

    public Node<E> getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node<E> prevNode) {
        this.prevNode = prevNode;
    }
}
