package strassen;

import strassen.model.Matrix;
import strassen.service.MatrixMultiplier;
import strassen.service.MatrixPower;
import strassen.service.SimpleMultiplier;
import strassen.service.StrassenMultiplier;

public class Main {
    public static void main(String[] args) {
        // Example: adjacency matrix (4x4)
        long[][] adj = {
                {0,1,1,0},
                {0,0,1,1},
                {0,0,0,1},
                {1,0,0,0}
        };
        Matrix A = new Matrix(adj);

        MatrixMultiplier naive = new SimpleMultiplier();
        MatrixMultiplier strassen = new StrassenMultiplier(64);

        MatrixPower powerNaive = new MatrixPower(naive);
        MatrixPower powerStrassen = new MatrixPower(strassen);

        int k = 7;
        Matrix A7_naive = powerNaive.pow(A, k);
        Matrix A7_stras = powerStrassen.pow(A, k);

        System.out.println("A^" + k + " (naive):\n" + A7_naive);
        System.out.println("A^" + k + " (strassen):\n" + A7_stras);
    }
}