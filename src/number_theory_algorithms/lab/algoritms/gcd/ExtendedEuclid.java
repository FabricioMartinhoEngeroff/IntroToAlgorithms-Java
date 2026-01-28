package number_theory_algorithms.lab.algoritms.gcd;


import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.util.Scanner;

// Euclides Estendido: acha gcd(a,b) e coeficientes x,y onde ax + by = gcd
public class ExtendedEuclid implements Algorithm {

    @Override
    public String name() {
        return "Euclides Estendido (inverso modular)";
    }

    @Override
    public String description() {
        return "Calcula gcd(a,b) e coeficientes x,y: ax + by = gcd(a,b)";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        BigInteger a = InputUtils.readBigInt(sc, "a = ");
        BigInteger b = InputUtils.readBigInt(sc, "b = ");

        AlgorithmResult res = new AlgorithmResult(name());

        // Executa algoritmo estendido
        Result r = egcd(a, b);

        res.addLine("gcd(a,b) = " + r.g);
        res.addLine("Coeficientes: x = " + r.x + ", y = " + r.y);

        // Verifica identidade de Bézout
        BigInteger check = a.multiply(r.x).add(b.multiply(r.y));
        res.addLine("✓ Verifica: a×x + b×y = " + check);

        // Se gcd=1, existe inverso modular
        if (r.g.equals(BigInteger.ONE) && !b.equals(BigInteger.ZERO)) {
            BigInteger inv = r.x.mod(b.abs());
            res.addLine("📌 gcd = 1 ⟹ existe inverso!");
            res.addLine("✅ a⁻¹ mod |b| = " + inv);
        } else {
            res.addLine("⚠️ gcd ≠ 1 ⟹ inverso não existe");
        }

        return res;
    }

    // Estrutura para retornar 3 valores
    private static class Result {
        final BigInteger g, x, y;
        Result(BigInteger g, BigInteger x, BigInteger y) {
            this.g = g; this.x = x; this.y = y;
        }
    }

    // Algoritmo recursivo: caso base b=0, senão reduz para egcd(b, a mod b)
    private static Result egcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            // Caso base: gcd(a,0) = |a|, coefs: a×1 + 0×0 = a
            int sign = a.signum() >= 0 ? 1 : -1;
            return new Result(a.abs(), BigInteger.valueOf(sign), BigInteger.ZERO);
        }

        // Passo recursivo
        Result next = egcd(b, a.mod(b));
        BigInteger x = next.y;
        BigInteger y = next.x.subtract(a.divide(b).multiply(next.y));
        return new Result(next.g, x, y);
    }
}
