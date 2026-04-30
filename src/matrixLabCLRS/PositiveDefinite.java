package matrixLabCLRS;

/**
 * Positive-definite matrices from CLRS Appendix D.4.
 *
 * A symmetric matrix A is positive-definite when x^T A x > 0 for every
 * non-zero vector x. Equivalent (Sylvester's criterion):
 *   all leading principal minors must be strictly positive.
 *
 * For positive-semidefinite, the inequality becomes >= 0.
 */
public class PositiveDefinite {

    private static final double EPS = 1e-9;

    /** Sylvester's criterion: A is positive-definite iff symmetric AND every leading minor > 0. */
    public static boolean isPositiveDefinite(Matrix a) {
        if (!a.isSquare()) return false;
        if (!MatrixTypes.isSymmetric(a)) return false;

        int n = a.rows();
        for (int k = 1; k <= n; k++) {
            Matrix leading = leadingPrincipalSubmatrix(a, k);
            double det = RankAndDeterminant.determinant(leading);
            if (det <= EPS) return false;
        }
        return true;
    }

    /** Positive-semidefinite: leading minors must be >= 0 (relaxed condition). */
    public static boolean isPositiveSemidefinite(Matrix a) {
        if (!a.isSquare()) return false;
        if (!MatrixTypes.isSymmetric(a)) return false;

        int n = a.rows();
        for (int k = 1; k <= n; k++) {
            Matrix leading = leadingPrincipalSubmatrix(a, k);
            double det = RankAndDeterminant.determinant(leading);
            if (det < -EPS) return false;
        }
        return true;
    }

    /** The k x k top-left submatrix of A. */
    private static Matrix leadingPrincipalSubmatrix(Matrix a, int k) {
        double[][] sub = new double[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                sub[i][j] = a.get(i, j);
            }
        }
        return new Matrix(sub);
    }
}

