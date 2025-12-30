package matrix_algorithms.solving;


import matrix_algorithms.core.Matrix;
import matrix_algorithms.core.Vector;

public class ForwardSubstitution {

    public static Vector solve(Matrix L, Vector b) {

        int n = b.size();
        Vector y = new Vector(n);

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L.get(i, j) * y.get(j);
            }
            y.set(i, b.get(i) - sum);
        }
        return y;
    }
}