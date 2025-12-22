package parallel_algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort implements Algorithm {

    private int[] array;

    public ParallelMergeSort(int[] array){
        this.array = array;
    }


    @Override
    public String getName() {
        return "Parallel Merge Sort";
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.SORTING;
    }

    @Override
    public String getDescription() {
        return "Divide-and-coquer sorting using Fork/Join parallelism";
    }

    @Override
    public void execute() {
        ForkJoinPool.commonPool().invoke(new MergeSortTask(array));
        System.out.println("Sorted array: " + Arrays.toString(array));
    }

    private static class MergeSortTask extends RecursiveAction {

        private final int[] arr;

        MergeSortTask(int[] arr) {
            this.arr = arr;
        }

        @Override
        protected void compute() {
            if (arr.length <= 1) return;

            int mid = arr.length / 2;
            int[] left = Arrays.copyOfRange(arr, 0, mid);
            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

            invokeAll(new MergeSortTask(left), new MergeSortTask(right));
            merge(left, right, arr);
        }

        private void merge(int[] left, int[] right, int[] result) {
            int i = 0, j = 0, k = 0;

            while (i < left.length && j < right.length) {
                result[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
            }
            while (i < left.length) result[k++] = left[i++];
            while (j < right.length) result[k++] = right[j++];
        }
    }
}
