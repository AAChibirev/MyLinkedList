package functional;
import interfaces.ILinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements ILinkedList<E> {
    private int size;
    private Node<E> begin;
    private Node<E> end;

    public MyLinkedList() {
        this.size = 0;
        this.begin = null;
        this.end = null;
    }

    private void addFirst(E element) {
        Node<E> newNode = new Node<>(element);

        if (begin != null) {
            newNode.setNextNode(begin);
            begin.setPrevNode(newNode);
            begin = newNode;
        } else {
            begin = newNode;
            end = newNode;
        }

        size++;
    }

    private void addLast(E element) {
        Node<E> newNode = new Node<>(element, end, null);

        end.setNextNode(newNode);
        end = newNode;
        size++;
    }

    public void add(E element) {

        if (size != 0) {
            addLast(element);
        } else {
            addFirst(element);
        }

    }

    @Override
    public void add(int index, E element) {

        checkIndexOnBoundsForAdding(index);

        if (index == size) {
            add(element);
        } else if (index == 0) {
            addFirst(element);
        } else {
            Node<E> tmpNode = jumpToNode(index);
            Node<E> newNode = new Node<>(element, tmpNode, tmpNode.getNextNode());
            tmpNode.getNextNode().setPrevNode(newNode);
            tmpNode.setNextNode(newNode);
            size++;
        }

    }

    private void clearElement(Node<E> node) {
        node.setElement(null);
        node.setNextNode(null);
        node.setPrevNode(null);
    }

    @Override
    public void clear() {
        Node<E> tmpNode = begin;
        while (tmpNode != null) {
            Node<E> nextTmp = tmpNode.getNextNode();
            clearElement(tmpNode);
            tmpNode = nextTmp;
        }
        begin = null;
        end = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        //check first
        checkIndexOnBounds(index);

        if (index == 0) {
            return begin.getElement();
        }

        if (index == size - 1) {
            return end.getElement();
        }
        //jumpToNode
        /*Node<E> tmpNode = begin;
        for (int i = 0; i < index-1; i++) {
            tmpNode = tmpNode.getNextNode();
        }*/
        return jumpToNode(index).getElement();

    }

    @Override
    public int indexOf(E element) {
        Node<E> tmpNode = begin;
        for (int i = 0; i < size; i++) {
            if (tmpNode.getElement().hashCode() == element.hashCode()) {
                return i;
            }
            if (tmpNode.getElement().equals(element)) {
                return i;
            }
            tmpNode = tmpNode.getNextNode();
        }
        return -1;
    }

    private E removeFirst() {
        E element = begin.getElement();
        if (begin.getNextNode() != null) {
            Node<E> tmpNode = begin.getNextNode();
            clearElement(begin);
            begin = tmpNode;
            begin.setPrevNode(null);
        } else {
            clearElement(begin);
            begin = null;
            end = null;
        }
        return element;
    }

    private E removeLast() {
        E element = end.getElement();
        Node<E> tmpNode = end.getPrevNode();
        clearElement(end);
        end = tmpNode;
        end.setNextNode(null);
        return element;
    }

    @Override
    public E remove(int index) {
        checkIndexOnBounds(index);

        E element;
        if (index == 0) {
            element = removeFirst();
        } else {
            //last element
            if (index == size - 1) {
                element = removeLast();

            } else {
                Node<E> tmpNode = jumpToNode(index+1);

                element = tmpNode.getElement();
                Node<E> willRemove = tmpNode;
                tmpNode.getNextNode().setPrevNode(tmpNode.getPrevNode());
                tmpNode.getPrevNode().setNextNode(tmpNode.getNextNode());
                clearElement(willRemove);

            }

        }

        size--;
        return element;

    }

    @Override
    public E set(int index, E element) {
        checkIndexOnBounds(index);

        E prevElement;
        if (index == 0) {
            prevElement = begin.getElement();
            begin.setElement(element);
        } else {
            if (index == size - 1) {
                prevElement = end.getElement();
                end.setElement(element);
            } else {
                Node<E> tmpNode = jumpToNode(index+1);
                prevElement = tmpNode.getElement();
                tmpNode.setElement(element);
            }

        }
        return prevElement;
    }

    private void checkIndexOnBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "+index+" and size "+ size);
        }
    }

    private void checkIndexOnBoundsForAdding(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index "+index+" and size "+ size);
        }
    }

    private Node<E> jumpToNode(int index) {

        //before
        /*
        Node<E> tmpNode = begin;
        for (int i = 0; i < index-1; i++) {
            tmpNode = tmpNode.getNextNode();
        }
        return tmpNode;
        */

        //after watching standard LinkedList
        //size >> 1 take value of half of list size and then compare where index will be, in first half or second one
        Node<E> tmpNode;
        if (index < (size >> 1)) {
            tmpNode = begin;
            for (int i = 0; i < index; i++) {
                tmpNode = tmpNode.getNextNode();
            }
        } else {
            tmpNode = end;
            for (int i = size - 1; i > index; i--) {
                tmpNode = tmpNode.getPrevNode();
            }
        }
        return tmpNode;

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = begin; x != null; x = x.getNextNode())
            result[i++] = x.getElement();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] newArray) {
        if (newArray.length < size)
            newArray = (T[])java.lang.reflect.Array.newInstance(
                    newArray.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = newArray;
        for (Node<E> x = begin; x != null; x = x.getNextNode())
            result[i++] = x.getElement();

        if (newArray.length > size)
            newArray[size] = null;

        return newArray;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("MyLinkedList[");
        if (size != 0) {
            Node<E> tmpNode = begin;
            for (int i = 0; i < size-1; i++) {
                result.append(tmpNode.getElement());
                result.append(", ");
                tmpNode = tmpNode.getNextNode();
            }
            result.append(tmpNode.getElement());
        }

        result.append("]");
        return result.toString();
    }

    @Override
    public Iterator iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements Iterator {
        private Node<E> currentNode;
        private Node<E> nextNode;
        private int iteratorIndex;

        public MyLinkedListIterator() {
            if (size != 0) {
                nextNode = begin;
            } else {
                nextNode = null;
            }
            iteratorIndex = 0;
        }

        @Override
        public boolean hasNext() {
            if (iteratorIndex < size) {
                return true;
            }
                return false;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            currentNode = nextNode;
            nextNode = nextNode.getNextNode();
            iteratorIndex++;
            return currentNode.getElement();
        }
    }
}

