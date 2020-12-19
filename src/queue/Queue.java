package queue;

import java.util.Iterator;
import java.util.LinkedList;

public class Queue<T> implements Iterable<T> {
    private LinkedList<T> list = new LinkedList<>();

    public Queue() {

    }

    public Queue(T firstElem) {
        enqueue(firstElem);
    }

    // aka offer
    public void enqueue(T elem) {
        // add to the back of the list
        list.addLast(elem);
    }

    // aka poll
    public void dequeue() {
        // remove from start of list
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        list.removeFirst();
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        return list.peekFirst();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
