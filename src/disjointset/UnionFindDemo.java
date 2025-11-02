package disjointset;

public class UnionFindDemo {
    public static void main(String[] args){

        DisjointSet ds = new DisjointSet();

        Node a = ds.makeSet(1);
        Node b = ds.makeSet(2);
        Node c = ds.makeSet(3);
        Node d = ds.makeSet(4);


        ds.union(a, b);
        ds.union(c, d);
        ds.union(a, c);

        System.out.println("Are a and d in the same set? " +
                (ds.findSet(a) == ds.findSet(d)));

        System.out.println("Are b and c in the same set? " +
                (ds.findSet(b) == ds.findSet(c)));

        Node e = ds.makeSet(5);
        System.out.println("Are a and e in the same set? " +
                (ds.findSet(a) == ds.findSet(e)));

    }
}
