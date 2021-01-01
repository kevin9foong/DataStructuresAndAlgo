package unionfind;

public class UnionFind {
    private int size;

    // maintain an array with sizes of each component
    private int[] sz;

    // point to the parent node of i, if i points to itself it is a root node
    private int[] rootId;

    // number of grouped components = root nodes
    private int numComponents;

    // constructors
    public UnionFind(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be more than 0");
        }
        // initially all nodes are root nodes
        this.size = size = numComponents;
        sz = new int[size];
        rootId = new int[size];

        // init starting data
        for (int i = 0; i < size; i++) {
            rootId[i] = i; //ref to itself as all are root nodes initially
            sz[i] = 1; // initially each group/component has only 1 node
        }
    }

    // find the group the node belongs to
    public int find(int node) {
        int root = node;
        // while not a self loop and hence not root
        while (root != rootId[root]) {
            root = rootId[root];
        }

        // assign the root node as the parent for every
        // child of root node
        // path compression allows us to achieve ammortized
        // constant time complexity
        while (node != root) {
            int next = rootId[node];
            rootId[node] = root;
            node = next;
        }

        return root;
    }

    // check if two nodes are connected
    public boolean connected(int node1, int node2) {
        return find(node1) == find(node2);
    }

    public int componentSize(int node) {
        return sz[node];
    }

    public int size() {
        return size;
    }

    public int components() {
        return numComponents;
    }



    public void union(int node1, int node2) {
        // find the grouping of the nodes
        int root1 = find(node1);
        int root2 = find(node2);

        // base case if they match
        if (root1 == root2) return;

        // map the parent of the root of the smaller component
        // to the root elem of the bigger component
        // NOTE: path compression is done by the find method
        if (sz[root1] < sz[root2]) {
            // add up the size of the components
            sz[root2] += sz[root1];
            rootId[root1] = rootId[root2];
        } else {
            sz[root1] += sz[root2];
            rootId[root2] = rootId[root1];
        }
        numComponents--;
        // since two components are merged into one, hence numComponents
        // decreases by one
    }


}
