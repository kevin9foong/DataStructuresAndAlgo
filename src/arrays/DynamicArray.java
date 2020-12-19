package arrays;

// also known as a ArrayList in Java
// Size of dynamic array is grown exponentially to keep time complexity of insertion at ammortized O(1)

import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T> {
    T[] staticArray;
    int len = 0; // displayed to user
    int capacity; // actual capacity of array

    // default starting size if no arg for constructor
    public DynamicArray() {
        this(2);
        capacity = 2;
    }

    public DynamicArray(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Invalid Capacity - cannot be less than 0");
        this.capacity = capacity;
        staticArray = (T[]) new Object[capacity];
    }

    // operations
    public T get(int index) {
        if ( index < 0 || index >= len ) throw new IndexOutOfBoundsException();
        return staticArray[index];
    }

    public void set(int index, T val) {
        if ( index < 0 || index >= len ) throw new IndexOutOfBoundsException();
        staticArray[index] = val;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            staticArray[i] = null;
        }
        this.len = 0;
    }

    public void add(T element) {
        // time to resize array!

        if (len + 1 > capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2; // resize exponentially by factor of 2 to maintain O(1) append time complexity
            T[] resizedArr = (T[]) new Object[capacity];

            for (int i = 0; i < len; i++) {
                resizedArr[i] = staticArray[i];
            }
            resizedArr[len] = element;
            staticArray = resizedArr;
        } else {
            staticArray[len] = element;
        }
        len = len + 1;
    }

    public void removeAt(int index) {
        if ( index >= len || index < 0 ) {
            throw new IndexOutOfBoundsException();
        } else {
            T[] newArr = (T[]) new Object[len - 1];
            for (int i = 0, j = 0; i < len; i++, j++) {
                if (i == index) { // tracks the original
                    j--; //tracks the new array
                } else {
                    newArr[j] = staticArray[j];
                }
            }
            capacity = --len;
        }
    }

    public void remove(T object) {
        for (int i = 0; i < len; i++) {
            if (staticArray[i].equals(object)) {
                removeAt(i);
            }
        }
    }

    public int indexOf(T object) {
        for (int i = 0; i < len; i ++) {
            if (staticArray[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public T next() {
                return staticArray[index++];
            }
        };
    }

    @Override
    public String toString() {
        // use Stringbuilder to avoid instantiation of String for each concat
        if (len == 0) return "[]";
        else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len; i++) {
                sb.append(staticArray[i]).append(", ");
            }
            return sb.append("]").toString();
        }
    }
}
