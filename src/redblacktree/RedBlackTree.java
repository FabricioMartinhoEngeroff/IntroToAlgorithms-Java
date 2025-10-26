package redblacktree;


public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // INSERTION
    public void insert(T data) {
        root = insert(root, data);
        root.color = BLACK;  // root is always black
    }

    // INSERTION
    private Node<T> insert(Node<T> node, T data) {
        if (node == null) {
            return new Node<>(data, RED);// new nodes are red by default
        }

        int cmp = data.compareTo(node.data);

        if (cmp < 0) {
            node.left = insert(node.left, data);
        } else if (cmp > 0) {
            node.right = insert(node.right, data);
        }

        // Fixing violations (the Red-Black Tree rules)
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        return node;
    }

    public boolean search(T data) {
        return search(root, data);
    }

    private boolean search(Node<T> node, T data) {
        if (node == null) return false;

        int cmp = data.compareTo(node.data);
        if (cmp < 0) return search(node.left, data);
        else if (cmp > 0) return search(node.right, data);
        else return true;
    }


    private boolean isRed(Node<T> node) {
        if (node == null) return false;
        return node.color == RED;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }


    private void flipColors(Node<T> node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
}

    public void traverseInOrder() {
        traverseInOrder(root);
        System.out.println();
    }

     private void traverseInOrder(Node<T> node){
        if(node != null){
            traverseInOrder(node.left);
            System.out.print(node.data +  " ");
            traverseInOrder(node.right);
        }
     }



}
