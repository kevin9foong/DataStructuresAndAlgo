package binarysearchtree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

// great practice for recursion and wishful thinking
public class BinarySearchTree<T extends Comparable<T>> {
    // number of nodes
    private int size = 0;

    // root node of the BST
    private Node rootNode = null;

    private class Node {
        T data;
        // pointers to children elements
        Node left, right;

        public Node(T elem, Node left, Node right) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    // getter method
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(T elem) {
        if (contains(elem)) {
            return false;
        } else {
            rootNode = add(rootNode, elem);
            size++;
            return true;
        }
    }

    // we are only adding new elements to the leaf nodes.
    private Node add(Node node, T elem) {
        // if the current node is a leaf node
        // create a brand new node with no children nodes and value of elem
        if (node == null) {
            node = new Node(elem, null, null);
        } else if (elem.compareTo(node.data) < 0) {
            node.left = add(node.left, elem);
        } else {
            node.right = add(node.right, elem);
        }

        return node;
    }

    public boolean remove(T elem) {
        if (contains(elem)) {
            rootNode = remove(rootNode, elem);
            size--;
            return true;
        } else {
            return false;
        }
    }

    public Node remove(Node node, T elem) {
        if (node == null) return null;
        int cmp = elem.compareTo(node.data);

        if (cmp < 0) {
            node.left = remove(node.left, elem);
        } else if (cmp > 0) {
            node.right = remove(node.right, elem);
        } else {
            // currently the node we want to remove
            // case 1 & 2: no children nodes or only 1 subtree
            // if only 1 subtree - then we the other subtree is null
            // which we assign to the current node.
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // case 3: has 2 subtrees
                // 2 possibilities - either swap with the min of the right
                // subtree or the max of the left subtree (we do the right for this)
                // the max node is the rightmost node
                Node tmp = digRightmost(node);

                // need to swap data and then remove the tmp data from
                // right subtree
                node.data = tmp.data;
                node.right = remove(node.right, tmp.data);
            }
        }
        return node;
    }

    private Node digRightmost(Node node) {
        // base case
        // check for this current node only
        if (node.right == null) {
            return node;
        } else {
            // wishful thinking - we assume that dig rightmost will
            // work for the rest of the right subtree
            return digRightmost(node.right);
        }
    }

    public boolean contains(T elem) {
        return contains(rootNode, elem);
    }

    private boolean contains(Node node, T elem) {
        if (node == null) return false;
        int cmp = elem.compareTo(node.data);
        if (cmp == 0) return true;
        else if (cmp < 0) {
            return contains(node.left, elem);
        } else {
            return contains(node.right, elem);
        }
    }

    public int height() {
        return height(rootNode);
    }

    // recursive process helper
    private int height(Node node) {
        if (node == null) {
            return 0;
        } else {
            // accumulated deferred operations
            return Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    private Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER: return preOrderTraversal();
            case IN_ORDER: return inOrderTraversal();
            case POST_ORDER: return postOrderTraversal();
            case LEVEL_ORDER: return levelOrderTraversal();
            default: return null;
        }
    }

    private Iterator<T> preOrderTraversal() {
        return null;
    }

    private Iterator<T> inOrderTraversal() {
        return null;
    }

    private Iterator<T> postOrderTraversal() {
        return null;
    }

    // use BFS - with queue
    private Iterator<T> levelOrderTraversal() {
        final int expectedNodeCount = size;
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(rootNode);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != size) throw new ConcurrentModificationException();
                return rootNode != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != size) throw new ConcurrentModificationException();
                Node node = queue.poll();
                // add the element to the queue for next
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
