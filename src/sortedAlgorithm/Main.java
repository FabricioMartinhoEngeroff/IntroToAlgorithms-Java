package sortedAlgorithm;

import matrixStrassen.model.Matrix;
import matrixStrassen.service.MatrixMultiplier;
import matrixStrassen.service.MatrixPower;
import matrixStrassen.service.SimpleMultiplier;
import matrixStrassen.service.StrassenMultiplier;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] original = {12, 11, 13, 5, 6, 7};

        // QuickSort
        int[] arr1 = Arrays.copyOf(original, original.length);
        Sorter quickSort = new QuickSort();
        quickSort.sort(arr1);
        System.out.println("QuickSort: " + Arrays.toString(arr1));

        // MergeSort
        int[] arr2 = Arrays.copyOf(original, original.length);
        Sorter mergeSort = new MergeSort();
        mergeSort.sort(arr2);
        System.out.println("MergeSort: " + Arrays.toString(arr2));

        // HeapSort
        int[] arr3 = Arrays.copyOf(original, original.length);
        Sorter heapSort = new HeapSort();
        heapSort.sort(arr3);
        System.out.println("HeapSort:  " + Arrays.toString(arr3));
    }

}