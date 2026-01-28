package number_theory_algorithms.lab.algoritms.modular;


import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.util.Scanner;

// Modular Exponentiation: calcula a^b mod n em O(log b) usando quadrados repetidos
public class ModularExponentiation implements Algorithm {

    @Override
    public String name() {
        return "Exponenciação Modular (repeated squaring)";
    }

    @Override
    public String description() {
        return "Calcula a^b mod n eficientemente usando bits do expoente.";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        BigInteger a = InputUtils.readBigInt(sc, "a (base) = ");
        BigInteger b = InputUtils.readBigInt(sc, "b (expoente) = ");
        BigInteger n = InputUtils.readBigInt(sc, "n (módulo) = ");

        AlgorithmResult res = new AlgorithmResult(name());

        // Validações básicas
        if (n.compareTo(BigInteger.ONE) <= 0) {
            return res.addLine("❌ n deve ser > 1");
        }
        if (b.signum() < 0) {
            return res.addLine("❌ Expoente negativo não suportado (use inverso)");
        }

        res.addLine("📌 Ideia: processar bits do expoente da direita p/ esquerda");
        res.addLine("   • bit=1 ⟹ multiplica resultado pela base");
        res.addLine("   • sempre: base = base² mod n");

        BigInteger ans = modPowFast(a, b, n, res);
        res.addLine("✅ Resultado: " + ans);
        return res;
    }

    // Versão pública sem log (para usar em outros algoritmos)
    public static BigInteger modPowFast(BigInteger a, BigInteger b, BigInteger n) {
        return modPowFast(a, b, n, null);
    }

    // Implementação: itera bits do expoente (LSB → MSB)
    private static BigInteger modPowFast(BigInteger a, BigInteger b, BigInteger n,
                                         AlgorithmResult log) {
        BigInteger base = a.mod(n);     // base atual (vai quadrando)
        BigInteger exp = b;              // expoente (vai shiftando)
        BigInteger result = BigInteger.ONE;

        int step = 0;
        while (exp.compareTo(BigInteger.ZERO) > 0) {
            if (exp.testBit(0)) { // LSB = 1?
                result = result.multiply(base).mod(n);
                if (log != null) {
                    log.addLine(String.format("  Passo %d: bit=1 ⟹ result ← result×base mod n = %s",
                            ++step, result));
                }
            } else if (log != null) {
                log.addLine(String.format("  Passo %d: bit=0 ⟹ skip", ++step));
            }

            base = base.multiply(base).mod(n); // sempre quadra
            exp = exp.shiftRight(1);           // próximo bit
        }
        return result;
    }
}
