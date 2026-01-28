package number_theory_algorithms.lab.algoritms.gcd;


import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.util.Scanner;

// GCD de Euclides: gcd(a,b) = gcd(b, a mod b) até b=0
public class EuclidGCD implements Algorithm {

    @Override
    public String name() {
        return "MDC (Algoritmo de Euclides)";
    }

    @Override
    public String description() {
        return "Calcula o maior divisor comum: gcd(a,b) recursivamente.";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        BigInteger a = InputUtils.readBigInt(sc, "a = ");
        BigInteger b = InputUtils.readBigInt(sc, "b = ");

        AlgorithmResult res = new AlgorithmResult(name());
        res.addLine("📌 Ideia: repetir 'a mod b' até resto = 0");

        BigInteger x = a.abs();
        BigInteger y = b.abs();

        int step = 0;
        while (!y.equals(BigInteger.ZERO)) {
            BigInteger r = x.mod(y);
            res.addLine(String.format("Passo %d: %s mod %s = %s", ++step, x, y, r));
            x = y;
            y = r;
        }

        res.addLine("✅ MDC = " + x);
        return res;
    }
}
