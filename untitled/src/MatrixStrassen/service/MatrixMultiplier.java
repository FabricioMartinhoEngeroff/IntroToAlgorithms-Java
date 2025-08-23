package MatrixStrassen.service;

import MatrixStrassen.model.Matrix;

public interface MatrixMultiplier {
    Matrix multiply(Matrix A, Matrix B);
}