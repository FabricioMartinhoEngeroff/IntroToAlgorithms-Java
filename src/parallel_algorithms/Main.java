package parallel_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Algorithm> algorithms = new ArrayList<>();

        algorithms.add(new ParallelMergeSort(new int[]{5, 2, 9, 1, 3}));
        algorithms.add(new ParallelMatrixMultiply(
                new int[][]{{1, 2}, {3, 4}},
                new int[][]{{5, 6}, {7, 8}}
        ));
        algorithms.add(new RaceConditionExample());

        printHeader();
        showMenu(algorithms);
    }

    private static void printHeader() {
        System.out.println("==================================");
        System.out.println(" PARALLEL ALGORITHMS - CHAPTER 26 ");
        System.out.println("==================================");
    }

    private static void showMenu(List<Algorithm> algorithms) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an algorithm to run:");
            for (int i = 0; i < algorithms.size(); i++) {
                System.out.println((i + 1) + " - " + algorithms.get(i).getName());
            }
            System.out.println("0 - Exit");

            System.out.print("Your choice: ");
            int choice = sc.nextInt();

            if (choice == 0) {
                System.out.println("Exiting program...");
                break;
            }

            if (choice < 1 || choice > algorithms.size()) {
                System.out.println("Invalid choice, try again.");
                continue;
            }

            Algorithm selected = algorithms.get(choice - 1);
            printAlgorithm(selected);
            selected.execute();
            System.out.println("----------------------------------");
        }

        sc.close();
    }

    private static void printAlgorithm(Algorithm algorithm) {
        System.out.println("\n----------------------------------");
        System.out.println("Algorithm: " + algorithm.getName());
        System.out.println("Type     : " + algorithm.getType());
        System.out.println("Info     : " + algorithm.getDescription());
        System.out.println();
    }
}
