package bplustree;

import dijkstra.Node;
import dijkstra.Service;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        BPlusTree<Integer> tree = new BPlusTree<>(3); // order 3

        int[] values = {5, 15, 25, 35, 45, 55, 65};
        for (int v : values) {
            tree.insert(v);
        }

        System.out.println("Traverse B+ Tree:");
        tree.traverse();

        System.out.println("Search 25: " + tree.search(25));
        System.out.println("Search 40: " + tree.search(40));
    }
}