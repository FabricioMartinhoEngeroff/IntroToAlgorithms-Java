package strassen.service;

import strassen.model.Matrix;

public interface MatrixMultiplier {
    Matrix multiply(Matrix A, Matrix B);
}