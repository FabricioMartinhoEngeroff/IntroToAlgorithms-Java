package sortedAlgorithm;

public class QuickSort implements Sorter {

    @Override
    public void sort(int[] a) {
        qs(a, 0, a.length -1);
    }

    private void qs(int[] a, int l, int r){
        if(l >=r) return;
        int p = part(a, l, r);
        qs(a, l, p - 1);
        qs(a , p + 1, r);
    }

    private int part(int[] a, int l, int r){
        int pivot = a[r];
        int i = l;
        for(int j = l; j < r; j++){
            if(a[j] <= pivot) swap(a, i++, j);
        }
        swap(a, i, r);
        return i;
    }

    private static void swap(int[] a, int i, int j){
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
