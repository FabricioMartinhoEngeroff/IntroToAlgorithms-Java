package number_theory_algorithms.lab.algoritms.primality;


import number_theory_algorithms.lab.algoritms.modular.ModularExponentiation;
import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.util.Scanner;

// Teste de Fermat (base 2): se 2^(n-1) mod n ≠ 1 ⟹ composto
public class PseudoprimeTest implements Algorithm {

    @Override
    public String name() {
        return "Teste Pseudoprimo (Fermat base 2)";
    }

    @Override
    public String description() {
        return "Pequeno Teorema de Fermat: se n primo ⟹ 2^(n-1) ≡ 1 (mod n)";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        BigInteger n = InputUtils.readBigInt(sc, "n (número a testar) = ");

        AlgorithmResult res = new AlgorithmResult(name());

        // Validações
        if (n.compareTo(BigInteger.valueOf(3)) < 0) {
            return res.addLine("❌ Use n ≥ 3");
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return res.addLine("✅ n é par ⟹ COMPOSTO");
        }

        res.addLine("📌 Teste: 2^(n-1) mod n = ?");

        BigInteger a = BigInteger.TWO;
        BigInteger exp = n.subtract(BigInteger.ONE);
        BigInteger result = ModularExponentiation.modPowFast(a, exp, n);

        res.addLine("Resultado: " + result);

        if (!result.equals(BigInteger.ONE)) {
            res.addLine("✅ Resultado ≠ 1 ⟹ COMPOSTO (certeza absoluta)");
        } else {
            res.addLine("⚠️ Resultado = 1 ⟹ POSSIVELMENTE PRIMO");
            res.addLine("   (mas pode ser pseudoprimo, ex: números de Carmichael)");
        }

        return res;
    }
}
