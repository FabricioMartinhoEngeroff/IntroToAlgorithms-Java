package matrix_algorithms.decomposition;

import matrix_algorithms.core.Matrix;

public class LUDecomposition {

    private final Matrix L;
    private final Matrix U;

    public LUDecomposition(Matrix A) {

        int n = A.getRows();
        L = new Matrix(n, n);
        U = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            L.set(i, i, 1.0);
        }

        for (int j = 0; j < n; j++) {

            for (int i = 0; i <= j; i++) {
                double sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += L.get(i, k) * U.get(k, j);
                }
                U.set(i, j, A.get(i, j) - sum);
            }

            for (int i = j + 1; i < n; i++) {
                double sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += L.get(i, k) * U.get(k, j);
                }
                L.set(i, j, (A.get(i, j) - sum) / U.get(j, j));
            }
        }
    }

    public Matrix getL() {
        return L;
    }

    public Matrix getU() {
        return U;
    }
}
