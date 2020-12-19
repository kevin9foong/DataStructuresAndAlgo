package stack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

// implements for abstraction - contract to fulfil certain
// functionality required by the interface
// interface based programming approach
public class Stack<T> implements Iterable<T>{
    // implementation with linkedlist
    // Java linked list is a doubly linked list by default impl
    private LinkedList<T> list = new LinkedList<>();

    public Stack(T firstElem) {
        push(firstElem);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void push(T elem) {
        list.addFirst(elem);
    }

    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        return list.removeFirst();
    }

    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return list.peekFirst();
    }

    @Override
    public Iterator<T> iterator() {
        // use the inbuilt implementation
        return list.iterator();
    }
}
