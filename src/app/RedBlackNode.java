package app;

// class RedBlackNode
class RedBlackNode<T extends Comparable<T>> {

    public static final int BLACK = 0;
    public static final int RED = 1;
    public T key;

    RedBlackNode<T> parent;

    RedBlackNode<T> left;

    RedBlackNode<T> right;

    // the number of elements to the left of each node
    public int numLeft = 0;
    // the number of elements to the right of each node
    public int numRight = 0;

    // the color of a node
    public int color;

    RedBlackNode() {
        parent = null;
        left = null;
        right = null;
        color = BLACK;
        numLeft = 0;
        numRight = 0;
    }

    // Constructor which sets key to the argument.
    RedBlackNode(T key) {
        this();
        this.key = key;
    }
}