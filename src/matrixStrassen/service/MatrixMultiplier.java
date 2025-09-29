package matrixStrassen.service;

import matrixStrassen.model.Matrix;

public interface MatrixMultiplier {
    Matrix multiply(Matrix A, Matrix B);
}