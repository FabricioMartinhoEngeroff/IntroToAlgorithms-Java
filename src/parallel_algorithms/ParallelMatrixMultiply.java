package parallel_algorithms;

import java.util.stream.IntStream;

public class ParallelMatrixMultiply implements Algorithm {

    private int[][] A;
    private int[][] B;
    private int[][] C;

    public ParallelMatrixMultiply(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;
        this.C = new int[A.length][B[0].length];
    }

    @Override
    public String getName() {
        return "Parallel Matrix Multiplication";
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.MATRIX;
    }

    @Override
    public String getDescription() {
        return "Parallelizes matrix multiplication using parallel streams.";
    }

    @Override
    public void execute() {
        int n = A.length;

        IntStream.range(0, n).parallel().forEach(i -> {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        });

        System.out.println("Matrix multiplication completed.");
    }
}
