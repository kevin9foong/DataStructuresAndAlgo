package queue;

// implemented with an Array
// pros: random access memory - faster than having to maintain ref to next
// elem

// cons: size is fixed for static arrays
import java.util.Arrays;

public class QueueArr<T> {
    private T[] arr;
    private int length = 0;
    private int head = 0;
    private int tail = -1;

    public QueueArr(int size) {
        arr = (T[]) new Object[size];
    }

    public void enqueue(T elem) {
        if (length > arr.length) throw new IndexOutOfBoundsException();
        tail = tail + 1 >= arr.length ? 0 : tail + 1;
        arr[tail] = elem;
        length++;
    }

    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        T data = arr[head];
        head = head >= arr.length ? 0 : head;
        arr[head++] = null;
        length--;
        return data;
    }

    public T peek() {
        return arr[head];
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String toString() {
        return Arrays.toString(arr);
    }
}
