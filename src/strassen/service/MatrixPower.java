package strassen.service;

import strassen.model.Matrix;

public class MatrixPower {
    private final MatrixMultiplier multiplier;

    public MatrixPower(MatrixMultiplier multiplier) { this.multiplier = multiplier; }

    public Matrix pow(Matrix A, long k) {
        if (k < 0) throw new IllegalArgumentException("Negative exponent");
        Matrix base = A.copy();
        Matrix result = Matrix.identity(A.size());
        long e = k;
        while (e > 0) {
            if ((e & 1L) == 1L) result = multiplier.multiply(result, base);
            e >>= 1L;
            if (e > 0) base = multiplier.multiply(base, base);
        }
        return result;
    }
}

