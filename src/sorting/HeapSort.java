package sorting;

public class HeapSort implements Sorter {

    @Override
    public void sort(int[] a) {
        int n = a.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(a, n, i);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            swap(a, 0, i);        // Move current root to end
            heapify(a, i, 0);     // Heapify reduced heap
        }
    }

    private void heapify(int[] a, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && a[left] > a[largest]) {
            largest = left;
        }
        if (right < n && a[right] > a[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(a, i, largest);
            heapify(a, n, largest);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

