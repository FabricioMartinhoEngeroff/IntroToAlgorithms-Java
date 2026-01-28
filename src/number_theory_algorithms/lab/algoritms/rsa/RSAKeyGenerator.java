package number_theory_algorithms.lab.algoritms.rsa;


import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

// RSA Key Generation: gera p,q primos ⟹ n=pq, calcula φ(n), escolhe e, acha d
public class RSAKeyGenerator implements Algorithm {

    private static final SecureRandom rnd = new SecureRandom();

    @Override
    public String name() {
        return "RSA: Gerar Par de Chaves";
    }

    @Override
    public String description() {
        return "Gera p,q primos, calcula n=pq, φ=(p-1)(q-1), escolhe e, calcula d=e⁻¹ mod φ";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        int bits = InputUtils.readInt(sc, "Tamanho de n em bits (ex: 512, 1024) = ");

        AlgorithmResult res = new AlgorithmResult(name());

        if (bits < 64) {
            return res.addLine("❌ Use pelo menos 64 bits (demo)");
        }

        res.addLine("📌 Gerando primos p e q...");

        // Gera p e q distintos de ~bits/2 cada
        BigInteger p = BigInteger.probablePrime(bits / 2, rnd);
        BigInteger q;
        do {
            q = BigInteger.probablePrime(bits / 2, rnd);
        } while (q.equals(p));

        // Calcula n e φ(n)
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));

        res.addLine("✓ Primos gerados: p, q (~" + (bits/2) + " bits cada)");
        res.addLine("✓ n = p×q (" + n.bitLength() + " bits)");

        // Escolhe e (geralmente 65537)
        BigInteger e = BigInteger.valueOf(65537);
        if (!phi.gcd(e).equals(BigInteger.ONE)) {
            // Fallback: acha e ímpar coprimo com φ
            e = BigInteger.valueOf(3);
            while (!phi.gcd(e).equals(BigInteger.ONE)) {
                e = e.add(BigInteger.TWO);
            }
        }

        // Calcula d = e⁻¹ mod φ
        BigInteger d = e.modInverse(phi);

        // Salva em memória (para usar no RSAEncryptor)
        RSAEncryptor.setKeyPair(new RSAKeyPair(n, e, d));

        res.addLine("✅ Chaves geradas e salvas!");
        res.addLine("   e = " + e);
        res.addLine("   n = " + n.bitLength() + " bits");
        res.addLine("");
        res.addLine("💡 Agora use 'RSA: encrypt/decrypt' para testar");

        return res;
    }
}
