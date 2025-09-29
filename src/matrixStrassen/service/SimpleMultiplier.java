package matrixStrassen.service;

import matrixStrassen.model.Matrix;

public class SimpleMultiplier  implements MatrixMultiplier {

    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        if (A.size() != B.size()) throw new IllegalArgumentException("Different sizes");
        int n = A.size();
        Matrix C = new Matrix(n);
        long[][] ad = A.raw(), bd = B.raw(), cd = C.raw();

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                long aik = ad[i][k];
                if (aik == 0) continue;
                for (int j = 0; j < n; j++) {
                    cd[i][j] += aik * bd[k][j];
                }
            }
        }
        return C;
    }
}

