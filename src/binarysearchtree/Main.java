package binarysearchtree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // Inserting values into the tree
        bst.insert(15);
        bst.insert(10);
        bst.insert(20);
        bst.insert(8);
        bst.insert(12);
        bst.insert(17);
        bst.insert(25);

        // Traversals
        System.out.println("In-Order (ascending):");
        bst.traverseInOrder();   // Expected: 8 10 12 15 17 20 25

        System.out.println("Pre-Order:");
        bst.traversePreOrder();  // Expected: 15 10 8 12 20 17 25

        System.out.println("Post-Order:");
        bst.traversePostOrder(); // Expected: 8 12 10 17 25 20 15

        // Searching
        System.out.println("\nSearching for 12: " + (bst.search(12) ? "Found" : "Not Found"));
        System.out.println("Searching for 30: " + (bst.search(30) ? "Found" : "Not Found"));

        // Minimum and Maximum
        System.out.println("\nMinimum value: " + bst.getMin());
        System.out.println("Maximum value: " + bst.getMax());

        // Deletion
        System.out.println("\nDeleting 20...");
        bst.delete(20);

        System.out.println("Tree In-Order after deletion:");
        bst.traverseInOrder(); // Expected: 8 10 12 15 17 25
    }
}
