package redBlackTree;


public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

        // Inserting values
        rbt.insert(10);
        rbt.insert(20);
        rbt.insert(30);
        rbt.insert(15);
        rbt.insert(25);
        rbt.insert(5);
        rbt.insert(1);

        // Traversal
        System.out.println("In-Order Traversal (sorted):");
        rbt.traverseInOrder(); // Expected sorted order

        // Search
        System.out.println("Searching for 15: " + (rbt.search(15) ? "Found" : "Not Found"));
        System.out.println("Searching for 99: " + (rbt.search(99) ? "Found" : "Not Found"));
    }
}