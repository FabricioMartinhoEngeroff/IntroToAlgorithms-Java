package matrixLabCLRS;

/**
 * Special matrix types from CLRS Appendix D.1.
 *
 * Each method classifies a matrix according to its structural properties:
 *   - identity, diagonal, tridiagonal
 *   - upper / lower triangular
 *   - permutation, symmetric
 */
public class MatrixTypes {

    private static final double EPS = 1e-9;

    /** I_n: 1 on the diagonal, 0 elsewhere. */
    public static boolean isIdentity(Matrix a) {
        if (!a.isSquare()) return false;
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                double expected = (i == j) ? 1.0 : 0.0;
                if (Math.abs(a.get(i, j) - expected) > EPS) return false;
            }
        }
        return true;
    }

    /** Diagonal: only the main diagonal can be non-zero. */
    public static boolean isDiagonal(Matrix a) {
        if (!a.isSquare()) return false;
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                if (i != j && Math.abs(a.get(i, j)) > EPS) return false;
            }
        }
        return true;
    }

    /** Tridiagonal: only main diagonal and the diagonals immediately above/below. */
    public static boolean isTridiagonal(Matrix a) {
        if (!a.isSquare()) return false;
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.cols(); j++) {
                if (Math.abs(i - j) > 1 && Math.abs(a.get(i, j)) > EPS) return false;
            }
        }
        return true;
    }

    /** Upper triangular: zeros below the main diagonal. */
    public static boolean isUpperTriangular(Matrix a) {
        if (!a.isSquare()) return false;
        for (int i = 1; i < a.rows(); i++) {
            for (int j = 0; j < i; j++) {
                if (Math.abs(a.get(i, j)) > EPS) return false;
            }
        }
        return true;
    }

    /** Lower triangular: zeros above the main diagonal. */
    public static boolean isLowerTriangular(Matrix a) {
        if (!a.isSquare()) return false;
        for (int i = 0; i < a.rows(); i++) {
            for (int j = i + 1; j < a.cols(); j++) {
                if (Math.abs(a.get(i, j)) > EPS) return false;
            }
        }
        return true;
    }

    /** Permutation matrix: square, exactly one 1 per row and per column, 0 elsewhere. */
    public static boolean isPermutation(Matrix a) {
        if (!a.isSquare()) return false;
        int n = a.rows();
        int[] rowOnes = new int[n];
        int[] colOnes = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double v = a.get(i, j);
                if (Math.abs(v) < EPS) continue;
                if (Math.abs(v - 1.0) > EPS) return false; // only 0s and 1s allowed
                rowOnes[i]++;
                colOnes[j]++;
            }
        }
        for (int i = 0; i < n; i++) {
            if (rowOnes[i] != 1 || colOnes[i] != 1) return false;
        }
        return true;
    }

    /** Symmetric: A == A^T (only valid for square matrices). */
    public static boolean isSymmetric(Matrix a) {
        if (!a.isSquare()) return false;
        for (int i = 0; i < a.rows(); i++) {
            for (int j = i + 1; j < a.cols(); j++) {
                if (Math.abs(a.get(i, j) - a.get(j, i)) > EPS) return false;
            }
        }
        return true;
    }
}

