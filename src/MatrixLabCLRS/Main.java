package MatrixLabCLRS;

import java.util.Scanner;

/**
 * MatrixLabCLRS — Main entry point.
 *
 * Interactive menu to demonstrate every concept from CLRS Appendix D:
 *   - Matrix operations (add, subtract, scalar, multiply, transpose)
 *   - Vector operations (dot product, norm)
 *   - Special matrix types (identity, diagonal, triangular, permutation, symmetric)
 *   - Rank, determinant and singularity
 *   - Positive-definite check
 */
public class Main {

    private static final Matrix A = new Matrix(new double[][]{
            {1, 2},
            {3, 4}
    });

    private static final Matrix B = new Matrix(new double[][]{
            {5, 6},
            {7, 8}
    });

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            printMenu();
            option = readInt(scanner);
            System.out.println();

            switch (option) {
                case 1  -> demoAdd();
                case 2  -> demoSubtract();
                case 3  -> demoScalar(scanner);
                case 4  -> demoMultiply();
                case 5  -> demoTranspose();
                case 6  -> demoDotAndNorm();
                case 7  -> demoSpecialTypes();
                case 8  -> demoDeterminant();
                case 9  -> demoRank();
                case 10 -> demoPositiveDefinite();
                case 0  -> System.out.println("Exiting MatrixLab. Bye!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=========== MatrixLab CLRS ===========");
        System.out.println(" 1  - Add matrices (A + B)");
        System.out.println(" 2  - Subtract matrices (A - B)");
        System.out.println(" 3  - Scalar multiplication (k * A)");
        System.out.println(" 4  - Multiply matrices (A * B)");
        System.out.println(" 5  - Transpose (A^T)");
        System.out.println(" 6  - Dot product and norm");
        System.out.println(" 7  - Check special matrix types");
        System.out.println(" 8  - Determinant");
        System.out.println(" 9  - Rank and full-rank check");
        System.out.println(" 10 - Positive-definite check");
        System.out.println(" 0  - Exit");
        System.out.println("======================================");
        System.out.print("Choose an option: ");
    }

    private static void demoAdd() {
        A.print("A");
        B.print("B");
        MatrixOperations.add(A, B).print("A + B");
    }

    private static void demoSubtract() {
        A.print("A");
        B.print("B");
        MatrixOperations.subtract(A, B).print("A - B");
    }

    private static void demoScalar(Scanner scanner) {
        System.out.print("Type the scalar k: ");
        double k = scanner.nextDouble();
        A.print("A");
        MatrixOperations.scalarMultiply(A, k).print(k + " * A");
    }

    private static void demoMultiply() {
        A.print("A");
        B.print("B");
        MatrixOperations.multiply(A, B).print("A * B");
    }

    private static void demoTranspose() {
        Matrix m = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}
        });
        m.print("M");
        m.transpose().print("M^T");
    }

    private static void demoDotAndNorm() {
        double[] u = {1, 2, 3};
        double[] v = {4, 5, 6};
        System.out.println("u = [1, 2, 3]");
        System.out.println("v = [4, 5, 6]");
        System.out.println("u . v = " + MatrixOperations.dot(u, v));
        System.out.printf("||u|| = %.4f%n", MatrixOperations.norm(u));
    }

    private static void demoSpecialTypes() {
        Matrix identity = Matrix.identity(3);
        Matrix diagonal = new Matrix(new double[][]{
                {2, 0, 0},
                {0, 5, 0},
                {0, 0, 9}
        });
        Matrix upper = new Matrix(new double[][]{
                {1, 2, 3},
                {0, 4, 5},
                {0, 0, 6}
        });
        Matrix permutation = new Matrix(new double[][]{
                {0, 1, 0},
                {0, 0, 1},
                {1, 0, 0}
        });
        Matrix symmetric = new Matrix(new double[][]{
                {1, 7, 3},
                {7, 4, 5},
                {3, 5, 9}
        });

        identity.print("Identity");
        System.out.println("isIdentity? " + MatrixTypes.isIdentity(identity));

        diagonal.print("Diagonal");
        System.out.println("isDiagonal? " + MatrixTypes.isDiagonal(diagonal));
        System.out.println("isTridiagonal? " + MatrixTypes.isTridiagonal(diagonal));

        upper.print("Upper");
        System.out.println("isUpperTriangular? " + MatrixTypes.isUpperTriangular(upper));
        System.out.println("isLowerTriangular? " + MatrixTypes.isLowerTriangular(upper));

        permutation.print("Permutation");
        System.out.println("isPermutation? " + MatrixTypes.isPermutation(permutation));

        symmetric.print("Symmetric");
        System.out.println("isSymmetric? " + MatrixTypes.isSymmetric(symmetric));
    }

    private static void demoDeterminant() {
        A.print("A");
        System.out.println("det(A) = " + RankAndDeterminant.determinant(A));

        Matrix singular = new Matrix(new double[][]{
                {1, 2},
                {2, 4}
        });
        singular.print("Singular example");
        System.out.println("det = " + RankAndDeterminant.determinant(singular));
        System.out.println("isSingular? " + RankAndDeterminant.isSingular(singular));
    }

    private static void demoRank() {
        Matrix fullRank = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 10}
        });
        Matrix rankDeficient = new Matrix(new double[][]{
                {1, 2, 3},
                {2, 4, 6},
                {3, 6, 9}
        });

        fullRank.print("Full-rank example");
        System.out.println("rank = " + RankAndDeterminant.rank(fullRank));
        System.out.println("isFullRank? " + RankAndDeterminant.isFullRank(fullRank));

        rankDeficient.print("Rank-deficient example");
        System.out.println("rank = " + RankAndDeterminant.rank(rankDeficient));
        System.out.println("isFullRank? " + RankAndDeterminant.isFullRank(rankDeficient));
    }

    private static void demoPositiveDefinite() {
        Matrix pd = new Matrix(new double[][]{
                {2, -1},
                {-1, 2}
        });
        Matrix notPd = new Matrix(new double[][]{
                {1, 2},
                {2, 1}
        });

        pd.print("Positive-definite candidate");
        System.out.println("isPositiveDefinite? " + PositiveDefinite.isPositiveDefinite(pd));
        System.out.println("isPositiveSemidefinite? " + PositiveDefinite.isPositiveSemidefinite(pd));

        notPd.print("Not positive-definite");
        System.out.println("isPositiveDefinite? " + PositiveDefinite.isPositiveDefinite(notPd));
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please type a valid number: ");
        }
        return scanner.nextInt();
    }
}

