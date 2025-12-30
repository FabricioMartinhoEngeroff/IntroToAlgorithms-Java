package matrix_algorithms.util;

import matrix_algorithms.core.Matrix;
import matrix_algorithms.core.Vector;

public class MatrixUtils {

    public static Matrix multiply(Matrix A, Matrix B) {

        Matrix C = new Matrix(A.getRows(), B.getCols());

        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < B.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < A.getCols(); k++) {
                    sum += A.get(i, k) * B.get(k, j);
                }
                C.set(i, j, sum);
            }
        }
        return C;
    }

    public static Vector multiply(Matrix A, Vector x) {

        Vector y = new Vector(A.getRows());

        for (int i = 0; i < A.getRows(); i++) {
            double sum = 0;
            for (int j = 0; j < A.getCols(); j++) {
                sum += A.get(i, j) * x.get(j);
            }
            y.set(i, sum);
        }
        return y;
    }

}