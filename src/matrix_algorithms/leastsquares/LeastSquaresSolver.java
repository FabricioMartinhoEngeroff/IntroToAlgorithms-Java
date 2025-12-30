package matrix_algorithms.leastsquares;

import matrix_algorithms.core.Matrix;
import matrix_algorithms.core.Vector;
import matrix_algorithms.decomposition.LUPDecomposition;
import matrix_algorithms.solving.LinearSystemSolver;
import matrix_algorithms.util.MatrixUtils;

public class LeastSquaresSolver {


    public static Vector solve(Matrix A, Vector y) {

        Matrix At = A.transpose();
        Matrix normal = MatrixUtils.multiply(At, A);
        Vector rhs = MatrixUtils.multiply(At, y);

        LUPDecomposition lup = new LUPDecomposition(normal);
        return LinearSystemSolver.solve(lup, rhs);
    }
}