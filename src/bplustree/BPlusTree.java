package bplustree;

import java.util.ArrayList;
import java.util.Collections;

public class BPlusTree<T extends Comparable<T>> {
    private int order;
    private BPlusNode<T> root;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusNode<>(true);
    }
    
    public void insert(T key){
        BPlusNode<T> newRoot = insertInternal(root, key);
        if (newRoot != null){
            root = newRoot;
        }
}
    // Recursive insertion
    private BPlusNode<T> insertInternal(BPlusNode<T> node, T key) {
        // If it's a leaf, insert directly
        if (node.isLeaf) {
            node.keys.add(key);
            Collections.sort(node.keys);

            // Split if overflow
            if (node.keys.size() > order - 1) {
                return splitLeaf(node);
            }
            return null;
        }

        // Internal node → find child
        int pos = findChildPosition(node, key);
        BPlusNode<T> child = node.children.get(pos);
        BPlusNode<T> newChild = insertInternal(child, key);

        if(newChild != null){
            // Promote middle key
            T midKey = newChild.keys.get(0);
            node.keys.add(pos, midKey);
            node.children.add(pos +1, newChild);

            // Split if overflow
            if (node.keys.size() > order - 1 ){
                return splitInternal(node);
            }
        }
        return null;
    }

    // Split a leaf node and return the new parent
    private BPlusNode<T> splitLeaf(BPlusNode<T> node){
        int mid = node.keys.size() / 2;

        BPlusNode<T> newLeaf = new BPlusNode<>(true);
        newLeaf.keys.addAll(node.keys.subList(mid, node.keys.size()));
        node.keys = new ArrayList<>(node.keys.subList(0, mid));

        // Link leaves
        newLeaf.next = node.next;
        node.next = newLeaf;

        // Create parent
        BPlusNode<T> parent = new BPlusNode<>(false);
        parent.keys.add(newLeaf.keys.get(0));
        parent.children.add(node);
        parent.children.add(newLeaf);

        return parent;

    }

    // Split an internal node and return the new parent
    private BPlusNode<T> splitInternal(BPlusNode<T> node){
        int mid = node.keys.size() / 2;

        BPlusNode<T> newInternal = new BPlusNode<>(false);
        newInternal.keys.addAll(node.keys.subList(mid + 1, node.keys.size()));
        newInternal.children.addAll(node.children.subList(mid + 1, node.children.size()));

        T promote = node.keys.get(mid);

        node.keys = new ArrayList<>(node.keys.subList(0, mid));
        node.children = new ArrayList<>(node.children.subList(0, mid + 1));

        BPlusNode<T> parent = new BPlusNode<>(false);
        parent.keys.add(promote);
        parent.children.add(node);
        parent.children.add(newInternal);

        return parent;
    }

    // Find correct child index for key
    private int findChildPosition(BPlusNode<T> node, T key){
        int i = 0;
        while (i < node.keys.size() && key.compareTo(node.keys.get(i)) >= 0){
            i++;
        }

        return i;
    }

    // Search for a key
    public boolean search(T key) {
        return searchInternal(root, key);
    }

    private boolean searchInternal(BPlusNode<T> node, T key) {
        if (node.isLeaf) {
            return node.keys.contains(key);
        }

        int pos = findChildPosition(node, key);
        return searchInternal(node.children.get(pos), key);
    }

    public void traverse(){
        BPlusNode<T> current = root;
        while (!current.isLeaf){
            current = current.children.get(0);
        }

        while (current != null) {
            System.out.print(current.keys + " ");
            current = current.next;
        }
        System.out.println();
    }
}


