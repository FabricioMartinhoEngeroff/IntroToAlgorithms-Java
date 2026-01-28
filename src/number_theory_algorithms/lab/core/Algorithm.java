package number_theory_algorithms.lab.core;

import java.util.Scanner;

// Contrato: todo algoritmo tem nome, descrição e execução
public interface Algorithm {
    String name();
    String description();
    AlgorithmResult run(Scanner sc); // retorna resultado formatado
}