package matrix_algorithms;

import matrix_algorithms.core.Matrix;
import matrix_algorithms.core.Vector;
import matrix_algorithms.decomposition.LUDecomposition;
import matrix_algorithms.decomposition.LUPDecomposition;
import matrix_algorithms.inversion.MatrixInverter;
import matrix_algorithms.leastsquares.LeastSquaresSolver;
import matrix_algorithms.solving.LinearSystemSolver;
import matrix_algorithms.util.MatrixUtils;

import java.util.Scanner;

public class Main {

    /* ================= MENU ================= */

    private static void showMenu() {
        System.out.println("====================================");
        System.out.println(" MATRIX ALGORITHMS (CLRS Inspired)");
        System.out.println("====================================");
        System.out.println("Choose an option:");
        System.out.println();
        System.out.println(" 1 - LU Decomposition");
        System.out.println(" 2 - LUP Decomposition");
        System.out.println(" 3 - Solve Linear System (Ax = b)");
        System.out.println(" 4 - Matrix Inversion");
        System.out.println(" 5 - Least Squares Approximation");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    /* ================= PRINT HELPERS ================= */

    private static void printMatrix(Matrix A) {
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getCols(); j++) {
                System.out.printf("%8.4f ", A.get(i, j));
            }
            System.out.println();
        }
    }

    private static void printVector(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            System.out.printf("%8.4f%n", v.get(i));
        }
    }

    private static void printPermutation(int[] p) {
        System.out.print("P = [ ");
        for (int value : p) {
            System.out.print(value + " ");
        }
        System.out.println("]");
    }

    /* ================= MAIN ================= */

    public static void main(String[] args) {

        showMenu();
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        System.out.println();
        System.out.println("------------------------------------");

        /* ========= DADOS DE EXEMPLO ========= */

        // Matriz quadrada (LU, LUP, solve, inverse)
        double[][] data = {
                {2, 3},
                {4, 7}
        };
        Matrix A = new Matrix(data);
        Vector b = new Vector(new double[]{1, 0});

        // Dados para mínimos quadrados (retangular)
        double[][] lsData = {
                {1, 1},
                {1, 2},
                {1, 3}
        };
        Matrix A_ls = new Matrix(lsData);
        Vector y_ls = new Vector(new double[]{1, 2, 2});

        /* ========= EXECUÇÃO ========= */

        switch (choice) {

            /* ===== 1. LU ===== */
            case 1 -> {
                System.out.println("LU Decomposition (A = L · U)");
                System.out.println();

                System.out.println("Matrix A:");
                printMatrix(A);

                System.out.println();
                LUDecomposition lu = new LUDecomposition(A);

                System.out.println("Matrix L:");
                printMatrix(lu.getL());

                System.out.println();
                System.out.println("Matrix U:");
                printMatrix(lu.getU());

                System.out.println();
                System.out.println("Check L · U:");
                printMatrix(MatrixUtils.multiply(lu.getL(), lu.getU()));
            }

            /* ===== 2. LUP ===== */
            case 2 -> {
                System.out.println("LUP Decomposition (P · A = L · U)");
                System.out.println();

                System.out.println("Matrix A:");
                printMatrix(A);

                LUPDecomposition lup = new LUPDecomposition(A);

                System.out.println();
                System.out.println("Permutation vector:");
                printPermutation(lup.getPermutation());

                System.out.println();
                System.out.println("Matrix L:");
                printMatrix(lup.getL());

                System.out.println();
                System.out.println("Matrix U:");
                printMatrix(lup.getU());

                System.out.println();
                System.out.println("Check L · U:");
                printMatrix(MatrixUtils.multiply(lup.getL(), lup.getU()));
            }

            /* ===== 3. SOLVE ===== */
            case 3 -> {
                System.out.println("Solving linear system Ax = b");
                System.out.println();

                System.out.println("Matrix A:");
                printMatrix(A);

                System.out.println();
                System.out.println("Vector b:");
                printVector(b);

                LUPDecomposition lup = new LUPDecomposition(A);
                Vector x = LinearSystemSolver.solve(lup, b);

                System.out.println();
                System.out.println("Solution x:");
                printVector(x);

                System.out.println();
                System.out.println("Check A · x:");
                printVector(MatrixUtils.multiply(A, x));
            }

            /* ===== 4. INVERSE ===== */
            case 4 -> {
                System.out.println("Matrix Inversion (A⁻¹)");
                System.out.println();

                System.out.println("Matrix A:");
                printMatrix(A);

                Matrix inv = MatrixInverter.invert(A);

                System.out.println();
                System.out.println("Inverse A⁻¹:");
                printMatrix(inv);

                System.out.println();
                System.out.println("Check A · A⁻¹:");
                printMatrix(MatrixUtils.multiply(A, inv));
            }

            /* ===== 5. LEAST SQUARES ===== */
            case 5 -> {
                System.out.println("Least Squares Approximation");
                System.out.println();

                System.out.println("Matrix A:");
                printMatrix(A_ls);

                System.out.println();
                System.out.println("Vector y:");
                printVector(y_ls);

                Vector x = LeastSquaresSolver.solve(A_ls, y_ls);

                System.out.println();
                System.out.println("Approximate solution x:");
                printVector(x);

                System.out.println();
                System.out.println("Predicted A · x:");
                printVector(MatrixUtils.multiply(A_ls, x));
            }

            default -> System.out.println("Invalid option.");
        }

        System.out.println("------------------------------------");
        System.out.println("Finished.");
    }
}
