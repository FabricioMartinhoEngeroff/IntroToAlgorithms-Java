package matrixLabCLRS;

/**
 * Rank, determinant and singularity tests from CLRS Appendix D.3.
 *
 * Determinant is computed via cofactor (Laplace) expansion — works for any size,
 * but cost grows fast (O(n!)). Good enough for the small examples in this lab.
 *
 * Rank is computed via Gaussian elimination on a copy of the matrix.
 */
public class RankAndDeterminant {

    private static final double EPS = 1e-9;

    /** Determinant of a square matrix, computed by cofactor expansion. */
    public static double determinant(Matrix a) {
        if (!a.isSquare()) {
            throw new IllegalArgumentException("Determinant requires a square matrix.");
        }
        int n = a.rows();
        double[][] m = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = a.get(i, j);
            }
        }
        return determinantRecursive(m, n);
    }

    private static double determinantRecursive(double[][] m, int n) {
        if (n == 1) return m[0][0];
        if (n == 2) return m[0][0] * m[1][1] - m[0][1] * m[1][0];

        double det = 0.0;
        for (int col = 0; col < n; col++) {
            double[][] minor = buildMinor(m, n, 0, col);
            double sign = (col % 2 == 0) ? 1.0 : -1.0;
            det += sign * m[0][col] * determinantRecursive(minor, n - 1);
        }
        return det;
    }

    private static double[][] buildMinor(double[][] m, int n, int skipRow, int skipCol) {
        double[][] minor = new double[n - 1][n - 1];
        int ri = 0;
        for (int i = 0; i < n; i++) {
            if (i == skipRow) continue;
            int rj = 0;
            for (int j = 0; j < n; j++) {
                if (j == skipCol) continue;
                minor[ri][rj++] = m[i][j];
            }
            ri++;
        }
        return minor;
    }

    /** A square matrix is singular iff its determinant is (effectively) zero. */
    public static boolean isSingular(Matrix a) {
        return Math.abs(determinant(a)) < EPS;
    }

    /**
     * Rank via Gaussian elimination: number of non-zero rows after row reduction.
     * Works for any rectangular matrix.
     */
    public static int rank(Matrix a) {
        int rows = a.rows();
        int cols = a.cols();
        double[][] m = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = a.get(i, j);
            }
        }

        int rank = 0;
        boolean[] rowUsed = new boolean[rows];

        for (int col = 0; col < cols; col++) {
            int pivot = -1;
            for (int row = 0; row < rows; row++) {
                if (!rowUsed[row] && Math.abs(m[row][col]) > EPS) {
                    pivot = row;
                    break;
                }
            }
            if (pivot == -1) continue;

            rowUsed[pivot] = true;
            rank++;

            for (int row = 0; row < rows; row++) {
                if (row != pivot && Math.abs(m[row][col]) > EPS) {
                    double factor = m[row][col] / m[pivot][col];
                    for (int j = col; j < cols; j++) {
                        m[row][j] -= factor * m[pivot][j];
                    }
                }
            }
        }
        return rank;
    }

    /** A matrix has full rank when rank == min(rows, cols). */
    public static boolean isFullRank(Matrix a) {
        return rank(a) == Math.min(a.rows(), a.cols());
    }
}

