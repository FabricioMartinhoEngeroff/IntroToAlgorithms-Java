package MatrixStrassen.service;

import MatrixStrassen.model.Matrix;

public class StrassenMultiplier implements MatrixMultiplier {
    private final int threshold;

    public StrassenMultiplier() { this(64); }
    public StrassenMultiplier(int threshold) { this.threshold = threshold; }

    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        if (A.size() != B.size()) throw new IllegalArgumentException("Different sizes");
        int n = A.size();
        if (n <= threshold) return new SimpleMultiplier().multiply(A, B);

        int m = nextPow2(n);
        Matrix Ap = padTo(A, m);
        Matrix Bp = padTo(B, m);
        Matrix Cp = strassenRec(Ap, Bp);
        return unpad(Cp, n);
    }

    private Matrix strassenRec(Matrix A, Matrix B) {
        int n = A.size();
        if (n <= threshold) return new SimpleMultiplier().multiply(A, B);

        int mid = n / 2;

        Matrix A11 = subMatrix(A, 0,    0,    mid);
        Matrix A12 = subMatrix(A, 0,    mid,  mid);
        Matrix A21 = subMatrix(A, mid,  0,    mid);
        Matrix A22 = subMatrix(A, mid,  mid,  mid);

        Matrix B11 = subMatrix(B, 0,    0,    mid);
        Matrix B12 = subMatrix(B, 0,    mid,  mid);
        Matrix B21 = subMatrix(B, mid,  0,    mid);
        Matrix B22 = subMatrix(B, mid,  mid,  mid);

        Matrix M1 = strassenRec(A11.add(A22), B11.add(B22));
        Matrix M2 = strassenRec(A21.add(A22), B11);
        Matrix M3 = strassenRec(A11,          B12.sub(B22));
        Matrix M4 = strassenRec(A22,          B21.sub(B11));
        Matrix M5 = strassenRec(A11.add(A12), B22);
        Matrix M6 = strassenRec(A21.sub(A11), B11.add(B12));
        Matrix M7 = strassenRec(A12.sub(A22), B21.add(B22));

        Matrix C11 = M1.add(M4).sub(M5).add(M7);
        Matrix C12 = M3.add(M5);
        Matrix C21 = M2.add(M4);
        Matrix C22 = M1.sub(M2).add(M3).add(M6);

        return combine(C11, C12, C21, C22);
    }

    // helpers
    private static int nextPow2(int n) { int p = 1; while (p < n) p <<= 1; return p; }

    private static Matrix padTo(Matrix A, int m) {
        if (A.size() == m) return A;
        Matrix P = new Matrix(m);
        long[][] Pd = P.raw(), Ad = A.raw();
        int n = A.size();
        for (int i = 0; i < n; i++) System.arraycopy(Ad[i], 0, Pd[i], 0, n);
        return P;
    }

    private static Matrix unpad(Matrix A, int nTarget) {
        if (A.size() == nTarget) return A;
        Matrix U = new Matrix(nTarget);
        long[][] Ud = U.raw(), Ad = A.raw();
        for (int i = 0; i < nTarget; i++) System.arraycopy(Ad[i], 0, Ud[i], 0, nTarget);
        return U;
    }

    private static Matrix subMatrix(Matrix A, int r0, int c0, int size) {
        Matrix S = new Matrix(size);
        long[][] Sd = S.raw(), Ad = A.raw();
        for (int i = 0; i < size; i++) System.arraycopy(Ad[r0 + i], c0, Sd[i], 0, size);
        return S;
    }

    private static Matrix combine(Matrix C11, Matrix C12, Matrix C21, Matrix C22) {
        int mid = C11.size();
        Matrix C = new Matrix(mid * 2);
        long[][] Cd = C.raw();
        long[][] d11 = C11.raw(), d12 = C12.raw(), d21 = C21.raw(), d22 = C22.raw();

        for (int i = 0; i < mid; i++) {
            System.arraycopy(d11[i], 0, Cd[i], 0, mid);
            System.arraycopy(d12[i], 0, Cd[i], mid, mid);
            System.arraycopy(d21[i], 0, Cd[i + mid], 0, mid);
            System.arraycopy(d22[i], 0, Cd[i + mid], mid, mid);
        }
        return C;
    }
}
