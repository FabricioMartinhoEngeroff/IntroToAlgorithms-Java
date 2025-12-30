package matrix_algorithms.inversion;

import matrix_algorithms.core.Matrix;
import matrix_algorithms.core.Vector;
import matrix_algorithms.decomposition.LUPDecomposition;
import matrix_algorithms.solving.LinearSystemSolver;

public class MatrixInverter {

    public static Matrix invert(Matrix A) {

        int n = A.getRows();
        Matrix inverse = new Matrix(n, n);

        LUPDecomposition lup = new LUPDecomposition(A);

        for (int i = 0; i < n; i++) {
            Vector e = new Vector(n);
            e.set(i, 1.0);

            Vector col = LinearSystemSolver.solve(lup, e);

            for (int j = 0; j < n; j++) {
                inverse.set(j, i, col.get(j));
            }
        }
        return inverse;
    }
}

