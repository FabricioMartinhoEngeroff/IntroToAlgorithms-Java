package MatrixLabCLRS;

/**
 * Represents a 2D matrix as an immutable structure.
 * Provides accessors, factory methods (identity, zero) and pretty printing.
 *
 * Concept (CLRS Appendix D):
 *   A matrix is a rectangular array of numbers organized in rows and columns.
 *   Internally, it behaves like a 2D array indexed as data[row][col].
 */
public class Matrix {

    private final double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Matrix data cannot be null or empty.");
        }

        int expectedCols = data[0].length;
        for (double[] row : data) {
            if (row.length != expectedCols) {
                throw new IllegalArgumentException("All rows must have the same number of columns.");
            }
        }

        // defensive copy → keeps the matrix immutable
        this.rows = data.length;
        this.cols = expectedCols;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public boolean isSquare() {
        return rows == cols;
    }

    /** Identity matrix I_n: diagonal filled with 1, everything else 0. */
    public static Matrix identity(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive.");
        }
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1.0;
        }
        return new Matrix(result);
    }

    /** Zero matrix of the given dimensions. */
    public static Matrix zero(int rows, int cols) {
        return new Matrix(new double[rows][cols]);
    }

    /** Transpose: A^T where (A^T)[i][j] = A[j][i]. */
    public Matrix transpose() {
        double[][] result = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = data[i][j];
            }
        }
        return new Matrix(result);
    }

    public void print() {
        print("Matrix");
    }

    public void print(String label) {
        System.out.println(label + " (" + rows + "x" + cols + "):");
        for (int i = 0; i < rows; i++) {
            System.out.print("  [ ");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%8.2f ", data[i][j]);
            }
            System.out.println("]");
        }
    }
}
