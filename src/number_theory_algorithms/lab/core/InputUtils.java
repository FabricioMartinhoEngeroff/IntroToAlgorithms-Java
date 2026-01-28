package number_theory_algorithms.lab.core;

import java.math.BigInteger;
import java.util.Scanner;

// Helpers para ler entrada com validação
public final class InputUtils {
    private InputUtils() {} // utility class - não instanciar

    // Lê int com retry automático se inválido
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Inválido. Digite um número inteiro.");
            }
        }
    }

    // Lê BigInteger com retry automático
    public static BigInteger readBigInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return new BigInteger(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("❌ Inválido. Digite um número (ex: 123456789).");
            }
        }
    }
}