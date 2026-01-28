package number_theory_algorithms.lab.algoritms.primality;

import number_theory_algorithms.lab.algoritms.modular.ModularExponentiation;
import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

// Miller-Rabin: teste probabilístico forte de primalidade
// Erro: ≤ 2^(-s) onde s = número de tentativas
public class MillerRabinTest implements Algorithm {

    private final SecureRandom rnd = new SecureRandom();

    @Override
    public String name() {
        return "Miller–Rabin (probabilístico forte)";
    }

    @Override
    public String description() {
        return "Teste robusto: roda s tentativas com bases aleatórias.";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        BigInteger n = InputUtils.readBigInt(sc, "n (número a testar) = ");
        int s = InputUtils.readInt(sc, "s (tentativas, ex: 5-20) = ");

        AlgorithmResult res = new AlgorithmResult(name());

        // Casos triviais
        if (n.compareTo(BigInteger.TWO) < 0) {
            return res.addLine("❌ n < 2 ⟹ não primo");
        }
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) {
            return res.addLine("✅ n ∈ {2,3} ⟹ PRIMO");
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return res.addLine("✅ n é par ⟹ COMPOSTO");
        }

        // Decompõe n-1 = 2^t × u (u ímpar)
        BigInteger nm1 = n.subtract(BigInteger.ONE);
        int t = nm1.getLowestSetBit(); // quantos fatores de 2
        BigInteger u = nm1.shiftRight(t);

        res.addLine("📌 Decomposição: n-1 = 2^t × u");
        res.addLine(String.format("   t = %d, u = %s (u ímpar)", t, u));

        // Testa s bases aleatórias
        for (int i = 1; i <= s; i++) {
            BigInteger a = randomBetween(BigInteger.TWO, n.subtract(BigInteger.TWO));
            boolean witness = isWitness(a, n, t, u);

            if (witness) {
                res.addLine(String.format("Teste %d: base=%s ⟹ WITNESS", i, a));
                res.addLine("✅ COMPOSTO (certeza absoluta)");
                return res;
            } else {
                res.addLine(String.format("Teste %d: base=%s ⟹ passou", i, a));
            }
        }

        res.addLine("✅ PROVAVELMENTE PRIMO");
        res.addLine(String.format("   (prob. erro ≤ 2^(-%d) ≈ %.6f%%)", s, 100.0 / Math.pow(2, s)));
        return res;
    }

    // Testa se 'a' é testemunha de que n é composto
    // Retorna true ⟹ n é composto
    private boolean isWitness(BigInteger a, BigInteger n, int t, BigInteger u) {
        BigInteger x = ModularExponentiation.modPowFast(a, u, n); // x₀ = a^u mod n

        // Se x₀ = 1 ou x₀ = n-1 ⟹ pode ser primo
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
            return false;
        }

        // Eleva ao quadrado t vezes
        for (int i = 1; i < t; i++) {
            x = x.multiply(x).mod(n);

            if (x.equals(n.subtract(BigInteger.ONE))) return false; // ok
            if (x.equals(BigInteger.ONE)) return true; // raiz não-trivial de 1!
        }

        return true; // não chegou em n-1 ⟹ composto
    }

    // Gera BigInteger aleatório no intervalo [min, max]
    private BigInteger randomBetween(BigInteger min, BigInteger max) {
        BigInteger range = max.subtract(min).add(BigInteger.ONE);
        int bits = range.bitLength();
        BigInteger r;
        do {
            r = new BigInteger(bits, rnd);
        } while (r.compareTo(range) >= 0);
        return r.add(min);
    }
}
