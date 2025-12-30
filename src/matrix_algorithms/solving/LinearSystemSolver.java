package matrix_algorithms.solving;

import matrix_algorithms.core.Vector;
import matrix_algorithms.decomposition.LUPDecomposition;

public class LinearSystemSolver {

    public static Vector solve(LUPDecomposition lup, Vector b) {

        int n = b.size();
        Vector pb = new Vector(n);

        int[] p = lup.getPermutation();
        for (int i = 0; i < n; i++) {
            pb.set(i, b.get(p[i]));
        }

        Vector y = ForwardSubstitution.solve(lup.getL(), pb);
        return BackSubstitution.solve(lup.getU(), y);
    }
}
