package matrix_algorithms.solving;


import matrix_algorithms.core.Matrix;
import matrix_algorithms.core.Vector;

public class BackSubstitution {

    public static Vector solve(Matrix U, Vector y) {

        int n = y.size();
        Vector x = new Vector(n);

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U.get(i, j) * x.get(j);
            }
            x.set(i, (y.get(i) - sum) / U.get(i, i));
        }
        return x;
    }
}