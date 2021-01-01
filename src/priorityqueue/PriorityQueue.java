package priorityqueue;

import java.util.*;

public class PriorityQueue<T extends Comparable<T>> {
    private int heapSize = 0;
    private int heapCapacity = 0;
    private List<T> heap = null;

    // map helps reduce time complexity for remove/contains from O(n) to O(1)
    // but might have overload - use only if remove operations are
    // done frequently
    private Map<T, TreeSet<Integer>> map = new HashMap<>();

    public PriorityQueue() {
        this(1);
    }

    public PriorityQueue(int size) {
        heap = new ArrayList<>(size);
    }


    // O(n) runtime - heapify approach
    public PriorityQueue(T[] elems) {
        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<>(heapCapacity);

        // add all elems into the heap
        for (int i = 0; i < heapSize; i++) {
            mapAdd(elems[i], i);
            heap.add(elems[i]);
        }

        // heapify process
        for (int i = Math.max(0,(heapSize / 2) - 1); i >= 0; i--) {
            sink(i);
        }
    }

    // O(n log(n)) runtime
    public PriorityQueue(Collection <T> elems) {
        this(elems.size());

        // for n elems, perform logn operations each during add
        for (T elem: elems) add(elem);
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void clear() {
        // clear the heap
        for (int i = 0; i < heapCapacity; i++) {
            heap.set(i, null);
        }
        heapSize = 0;
        // clear the hashtable
        map.clear();
    }

    public int size() {
        return heapSize;
    }

    // get highest priority elem
    public T peek() {
        if (isEmpty()) return null;
        // due to heap invariant, heap will maintain highest priority
        // at the index 0 of the heap - depending on min/max heap
        return heap.get(0);
    }

    // Ologn as we need to bubble up/sink to maintain heap invariant
    public T poll() {
        return removeAt(0);
    }

    // O(1) due to hashtable lookup property
    public boolean contains(T elem) {
        if (elem == null) return false;
        return map.containsKey(elem);
    }

    public void add(T elem) {
        if (elem == null) throw new IllegalArgumentException();

        // add the new elem in the left bottom most level
        // as insertion point
        if (heapSize < heapCapacity) {
            heap.set(heapSize, elem);
        } else {
            heap.add(elem);
            heapCapacity++;
        }

        // update hashtable as well
        mapAdd(elem, heapSize);

        // bubble up to maintain heap invariant
        // the direction is upwards since the insertion point is at the bottom
        // most level
        swim(heapSize);
        heapSize++;
    }

    // compares 2 nodes from indexes i and j
    // check if node at index i less than at index j
    private boolean isLess(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);

        // returns -1 if less than, 0 if equals and 1 is more than
        return node1.compareTo(node2) <= 0;
    }

    // destructive method to the heap
    private void swim(int k) {
        int parent = (k-1) / 2;

        // compare k with its parent
        while (k > 0 && isLess(k, parent)) {
            //swap indices between k and parent
            swap(parent, k);
            k = parent;

            // update parent index to new parent based on new index of k
            parent = (k - 1) / 2;
        }
    }

    private void sink(int k) {
        while (true) {
            // get indices of children nodes
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left;

            if (right < heapSize && isLess(right, left)) {
                smallest = right;
            }
            if (left >= heapSize || isLess(k, smallest)) break;

            swap(smallest, k);
            k = smallest;
        }
    }

    private void swap(int i, int j) {
        T iElem = heap.get(i);
        T jElem = heap.get(j);

        heap.set(i, jElem);
        heap.set(j, iElem);

        // need to update the hashtable as well
        mapSwap(iElem, jElem, i, j);
    }

    public boolean remove(T elem) {
        Integer index = mapGet(elem);
        if (index != null) removeAt(index);
        return index != null; // whether removal was successful
    }

    private T removeAt(int i) {
        if (isEmpty()) return null;

        // swap value to the last index
        heapSize--;
        T removed = heap.get(i);
        swap(i, heapSize);

        // remove the value
        heap.set(heapSize, null);
        mapRemove(removed, heapSize);

        // check if the index is last, then no need to swim/sink
        if (i == heapSize) return removed;
        else {
            T elem = heap.get(i);
            sink(i);
            // if sinking dn work, try swimming
            if (heap.get(i).equals(elem)) swim(i);

            return removed;
        }
    }

    // test to check if heap is still min heap
    public boolean isMinHeap(int k) {
        // testing index - if index is out of bounds, return true
        // base case
        if (k >= heapSize) return true;

        // get child node indices
        int left = 2 * k + 1;
        int right = 2 * k + 2;

        if (left < heapSize && !isLess(k, left)) return false;
        if (right < heapSize && !isLess(k, right)) return false;

        // by wishful thinking, we just need to solve for smaller cases
        // which are left and right indices
        return isMinHeap(left) && isMinHeap(right);
    }

    private void mapAdd(T value, int index) {
        TreeSet<Integer> set = map.get(value);
        if (set == null) {
            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);
        } else {
            set.add(index);
        }
    }

    private void mapRemove(T value, int index) {
        // TreeSet is a BBST in java impl
        TreeSet<Integer> set = map.get(value);
        set.remove(index);

        // no indices with value, remove entry entirely from the hashtable
        if (set.size() == 0) map.remove(value);
    }

    private Integer mapGet(T value) {
        TreeSet<Integer> set = map.get(value);
        if (set != null) return set.last();
        return null;
    }

    private void mapSwap(T valOne, T valTwo, int valOneIndex, int valTwoIndex) {
        Set<Integer> setOne = map.get(valOne);
        Set<Integer> setTwo = map.get(valTwo);

        setOne.remove(valOneIndex);
        setTwo.remove(valTwoIndex);

        setOne.add(valTwoIndex);
        setTwo.add(valOneIndex);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
