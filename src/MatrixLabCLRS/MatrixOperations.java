package MatrixLabCLRS;

/**
 * Core matrix and vector operations from CLRS Appendix D.2.
 *
 * Includes:
 *   - addition / subtraction
 *   - scalar multiplication
 *   - matrix multiplication
 *   - dot product (inner product)
 *   - vector norm (Euclidean)
 */
public class MatrixOperations {

    /** A + B (element-wise). Both matrices must have the same shape. */
    public static Matrix add(Matrix a, Matrix b) {
        requireSameShape(a, b);
        double[][] result = new double[a.rows()][a.cols()];
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                result[i][j] = a.get(i, j) + b.get(i, j);
            }
        }
        return new Matrix(result);
    }

    /** A - B (element-wise). */
    public static Matrix subtract(Matrix a, Matrix b) {
        requireSameShape(a, b);
        double[][] result = new double[a.rows()][a.cols()];
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                result[i][j] = a.get(i, j) - b.get(i, j);
            }
        }
        return new Matrix(result);
    }

    /** k * A (multiply every element by the scalar k). */
    public static Matrix scalarMultiply(Matrix a, double k) {
        double[][] result = new double[a.rows()][a.cols()];
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                result[i][j] = k * a.get(i, j);
            }
        }
        return new Matrix(result);
    }

    /**
     * A * B. Compatible only when A.cols == B.rows.
     * Cost: O(n^3) for square n x n matrices (naive algorithm).
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols() != b.rows()) {
            throw new IllegalArgumentException(
                    "Incompatible shapes: A is " + a.rows() + "x" + a.cols()
                            + ", B is " + b.rows() + "x" + b.cols()
                            + ". A.cols must equal B.rows.");
        }
        double[][] result = new double[a.rows()][b.cols()];
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < b.cols(); j++) {
                double sum = 0.0;
                for (int k = 0; k < a.cols(); k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                result[i][j] = sum;
            }
        }
        return new Matrix(result);
    }

    /** Dot product of two vectors of equal length: sum(u_i * v_i). */
    public static double dot(double[] u, double[] v) {
        if (u.length != v.length) {
            throw new IllegalArgumentException("Vectors must have the same length.");
        }
        double sum = 0.0;
        for (int i = 0; i < u.length; i++) {
            sum += u[i] * v[i];
        }
        return sum;
    }

    /** Euclidean norm ||v|| = sqrt(v . v). */
    public static double norm(double[] v) {
        return Math.sqrt(dot(v, v));
    }

    private static void requireSameShape(Matrix a, Matrix b) {
        if (a.rows() != b.rows() || a.cols() != b.cols()) {
            throw new IllegalArgumentException("Matrices must have the same shape.");
        }
    }
}
