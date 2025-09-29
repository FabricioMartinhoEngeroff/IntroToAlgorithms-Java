package sortedAlgorithm;

public class MergeSort implements Sorter{

    @Override
    public void sort(int[] a){
        ms(a, new int[a.length], 0, a.length - 1);
    }

    private  void ms(int[] a, int[] aux, int l, int r){
        if(l >= r) return;
        int m = (l + r) >>> 1;
        ms(a, aux, l, m);
        ms(a, aux, m + 1, r);
        merge(a, aux, l, m, r);



    }

    private void merge(int[] a, int[] aux, int l, int m, int r) {
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r){
            aux[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        }
        while(i <= m) aux[k++] = a[i++];
        while(j <= r) aux[k++] = a[j++];
        for(int t = l; t <= r; t++) a[t] = aux[t];
    }
}
