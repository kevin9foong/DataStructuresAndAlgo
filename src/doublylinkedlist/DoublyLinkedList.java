package doublylinkedlist;

import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> {
    // reference to first and last node of Linked list
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size;

    // represent data as nodes
    private class Node<T> {
        T data;
        // reference to next and prev node
        Node<T> prev;
        Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

            @Override public String toString() {
                return data.toString();
            }
        }

        public void clear() {
            head = null;
            tail = null;
        }

        // getter method
        public int size() {
            return size;
//            int len = 0;
//            Node<T> current = head;
//            while (current != null) {
//                current = head.next;
//                len++;
//            }
//            return len;
        }

        public boolean isEmpty() {
//            if no head is empty
//            return head == null;
            return size == 0;
        }

        // O(1) since we keep a ref to the head
        public void addFirst(T elem) {
            if (isEmpty()) {
                head = tail = new Node(elem, null, this.head);
            } else {
                head.prev = new Node(elem, null, head);
                head = head.prev;
            }
            size++;
        }

        public void addLast(T elem) {
            if (isEmpty()) {
                head = tail = new Node(elem, null, null);
            } else {
                tail.next = new Node(elem, tail, null);
                tail = tail.next;
            }
            size++;
        }

        // basic O(1) operations
        // unreferenced object in heap will be cleaned by Java GC
        public T removeFirst() {
            if (isEmpty()) {
                throw new RuntimeException("List is empty");
            } else {
                T data = head.data;
                head = head.next;
                size--;
                // have to check if removal of element will cause list to be empty
                if (isEmpty()) {
                    tail = null;
                } else {
                    head.prev = null;
                }
                return data;
            }
        }

    public T removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        } else {
            T data = tail.data;
            tail = tail.prev;
            size--;
            if (isEmpty()) {
                head = null;
            } else {
                tail.next = null;
            }
            return data;
        }
    }

    // remove an arbitrary node - inner encapsulated method (info hiding)
    private T remove(Node<T> node) {
        if (node.prev == null) return removeFirst();
        if (node.next == null) return removeLast();

        // set the prev and next node to skip over current node
        node.prev.next = node.next;
        node.next.prev = node.prev;

        T data = node.data;

        // remove references (memory cleanup)
        node.data = null;
        node = node.next = node.prev = null;

        return data;
    }

    private T removeAt(int index) {
        // find the node at index
        // apply remove
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            int i;
            Node<T> trav;
            // iterate from front or back for better performance
            if (index < size/2) {
                for (i = 0, trav = head; i != index; i++) {
                    trav = trav.next;
                }
            } else {
                for (i = size - 1, trav = tail; i != index; i--) {
                    trav = trav.prev;
                }
            }
            return remove(trav);
        }
    }

    public boolean remove(Object obj) {
        Node<T> trav;
        for (trav = head; trav != null; trav = trav.next) {
            if (obj == trav.data || obj.equals(trav.data)) {
                remove(trav);
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object obj) {
        Node<T> trav;
        int index = 0;
        for (trav = head; trav != null; trav = trav.next, index++) {
            if (obj == trav.data || obj.equals(trav.data)) {
                return index;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> trav = head;
            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    // toString method

}
