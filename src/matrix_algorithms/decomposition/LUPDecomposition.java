package matrix_algorithms.decomposition;

import matrix_algorithms.core.Matrix;

public class LUPDecomposition {

    private final Matrix L;
    private final Matrix U;
    private final int[] permutation;

    public LUPDecomposition(Matrix A) {

        int n = A.getRows();
        permutation = new int[n];

        Matrix M = new Matrix(A);

        for (int i = 0; i < n; i++) {
            permutation[i] = i;
        }

        for (int k = 0; k < n; k++) {

            int pivot = k;
            double max = Math.abs(M.get(k, k));

            for (int i = k + 1; i < n; i++) {
                double value = Math.abs(M.get(i, k));
                if (value > max) {
                    max = value;
                    pivot = i;
                }
            }

            if (max == 0.0) {
                throw new RuntimeException("Singular matrix");
            }

            int temp = permutation[k];
            permutation[k] = permutation[pivot];
            permutation[pivot] = temp;

            for (int j = 0; j < n; j++) {
                double t = M.get(k, j);
                M.set(k, j, M.get(pivot, j));
                M.set(pivot, j, t);
            }

            for (int i = k + 1; i < n; i++) {
                M.set(i, k, M.get(i, k) / M.get(k, k));
                for (int j = k + 1; j < n; j++) {
                    M.set(i, j,
                            M.get(i, j) - M.get(i, k) * M.get(k, j)
                    );
                }
            }
        }

        L = new Matrix(n, n);
        U = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            L.set(i, i, 1.0);
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    L.set(i, j, M.get(i, j));
                } else {
                    U.set(i, j, M.get(i, j));
                }
            }
        }
    }

    public Matrix getL() {
        return L;
    }

    public Matrix getU() {
        return U;
    }

    public int[] getPermutation() {
        return permutation;
    }
}