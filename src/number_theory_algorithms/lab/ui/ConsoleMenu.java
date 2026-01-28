package number_theory_algorithms.lab.ui;


import number_theory_algorithms.lab.algoritms.gcd.EuclidGCD;
import number_theory_algorithms.lab.algoritms.gcd.ExtendedEuclid;
import number_theory_algorithms.lab.algoritms.modular.ModularExponentiation;
import number_theory_algorithms.lab.algoritms.primality.MillerRabinTest;
import number_theory_algorithms.lab.algoritms.primality.PseudoprimeTest;
import number_theory_algorithms.lab.algoritms.rsa.RSAEncryptor;
import number_theory_algorithms.lab.algoritms.rsa.RSAKeyGenerator;
import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Menu principal: registra algoritmos e loop de interação
public class ConsoleMenu {
    private final List<Algorithm> algorithms = new ArrayList<>();

    public ConsoleMenu() {
        // Registra todos os algoritmos do cap 31
        algorithms.add(new EuclidGCD());
        algorithms.add(new ExtendedEuclid());
        algorithms.add(new ModularExponentiation());
        algorithms.add(new PseudoprimeTest());
        algorithms.add(new MillerRabinTest());
        algorithms.add(new RSAKeyGenerator());
        algorithms.add(new RSAEncryptor());
    }

    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                printMenu();
                String opt = sc.nextLine().trim();

                if (opt.equals("0")) {
                    System.out.println("👋 Até logo!");
                    return;
                }

                // Valida índice
                int idx;
                try {
                    idx = Integer.parseInt(opt) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("❌ Opção inválida.\n");
                    continue;
                }

                if (idx < 0 || idx >= algorithms.size()) {
                    System.out.println("❌ Opção fora do range.\n");
                    continue;
                }

                // Executa algoritmo escolhido
                Algorithm algo = algorithms.get(idx);
                AlgorithmResult result = algo.run(sc);
                System.out.println(result.render());

                System.out.print("⏎ Enter para continuar...");
                sc.nextLine();
            }
        }
    }

    private void printMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  Number Theory Lab (CLRS Cap 31)  ║");
        System.out.println("╚════════════════════════════════════╝");
        for (int i = 0; i < algorithms.size(); i++) {
            System.out.printf(" %d) %s%n", i + 1, algorithms.get(i).name());
        }
        System.out.println(" 0) Sair");
        System.out.print("\n▶ Escolha: ");
    }
}