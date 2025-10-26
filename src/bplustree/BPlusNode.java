package bplustree;

import java.util.ArrayList;
import java.util.List;

public class BPlusNode <T extends Comparable<T>>{
    boolean isLeaf;
    List<T> keys;
    List<BPlusNode<T>> children;
    BPlusNode<T> next;

    public BPlusNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.next = null;
    }

}
