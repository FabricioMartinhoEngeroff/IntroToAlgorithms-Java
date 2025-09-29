package matrixStrassen.model;

import java.util.Arrays;

public class Matrix {

    private final int n;
    private final long[][] data;

    public Matrix(int n) {
        this.n = n;
        this.data = new long[n][n];
    }

    public Matrix(long[][] data) {
        this.n = data.length;
        this.data = new long[n][n];
        for (int i = 0; i < n; i++) this.data[i] = Arrays.copyOf(data[i], n);
    }

    public int size() { return n; }
    public long[][] raw() { return data; }
    public long get(int i, int j) { return data[i][j]; }
    public void set(int i, int j, long v) { data[i][j] = v; }

    public Matrix copy() {
        Matrix c = new Matrix(n);
        for (int i = 0; i < n; i++) System.arraycopy(this.data[i], 0, c.data[i], 0, n);
        return c;
    }

    public static Matrix identity(int n) {
        Matrix I = new Matrix(n);
        for (int i = 0; i < n; i++) I.set(i, i, 1);
        return I;
    }

    public Matrix add(Matrix B) {
        checkSameSize(B);
        Matrix C = new Matrix(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C.data[i][j] = this.data[i][j] + B.data[i][j];
        return C;
    }

    public Matrix sub(Matrix B) {
        checkSameSize(B);
        Matrix C = new Matrix(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C.data[i][j] = this.data[i][j] - B.data[i][j];
        return C;
    }

    private void checkSameSize(Matrix B) {
        if (this.n != B.n) throw new IllegalArgumentException("Different sizes");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (long[] row : data) sb.append(Arrays.toString(row)).append('\n');
        return sb.toString();
    }

}
