package number_theory_algorithms.lab.algoritms.rsa;

import number_theory_algorithms.lab.algoritms.modular.ModularExponentiation;
import number_theory_algorithms.lab.core.Algorithm;
import number_theory_algorithms.lab.core.AlgorithmResult;
import number_theory_algorithms.lab.core.InputUtils;

import java.math.BigInteger;
import java.util.Scanner;

// RSA Encrypt/Decrypt: c = m^e mod n (encrypt), m = c^d mod n (decrypt)
public class RSAEncryptor implements Algorithm {

    private static RSAKeyPair keyPair; // compartilhado (simples para CLI)

    public static void setKeyPair(RSAKeyPair kp) {
        keyPair = kp;
    }

    @Override
    public String name() {
        return "RSA: Encrypt/Decrypt";
    }

    @Override
    public String description() {
        return "Cifra/decifra número m < n usando chaves geradas anteriormente.";
    }

    @Override
    public AlgorithmResult run(Scanner sc) {
        AlgorithmResult res = new AlgorithmResult(name());

        // Verifica se chave foi gerada
        if (keyPair == null) {
            res.addLine("❌ Nenhuma chave carregada!");
            res.addLine("   Execute 'RSA: Gerar Par de Chaves' primeiro.");
            return res;
        }

        BigInteger n = keyPair.n;
        res.addLine("📌 Chave carregada (n = " + n.bitLength() + " bits)");

        // Menu: encrypt ou decrypt
        System.out.println("\n1) Encrypt (c = m^e mod n)");
        System.out.println("2) Decrypt (m = c^d mod n)");
        System.out.print("▶ Escolha: ");
        String opt = sc.nextLine().trim();

        if (opt.equals("1")) {
            // Cifrar
            BigInteger m = InputUtils.readBigInt(sc, "m (mensagem, 0 ≤ m < n) = ");

            if (m.signum() < 0 || m.compareTo(n) >= 0) {
                return res.addLine("❌ Mensagem fora do range [0, n-1]");
            }

            BigInteger c = ModularExponentiation.modPowFast(m, keyPair.e, n);
            res.addLine("🔒 Cifrando: c = m^e mod n");
            res.addLine("✅ Ciphertext: " + c);

        } else if (opt.equals("2")) {
            // Decifrar
            BigInteger c = InputUtils.readBigInt(sc, "c (ciphertext, 0 ≤ c < n) = ");

            if (c.signum() < 0 || c.compareTo(n) >= 0) {
                return res.addLine("❌ Ciphertext fora do range [0, n-1]");
            }

            BigInteger m = ModularExponentiation.modPowFast(c, keyPair.d, n);
            res.addLine("🔓 Decifrando: m = c^d mod n");
            res.addLine("✅ Mensagem: " + m);

        } else {
            return res.addLine("❌ Opção inválida");
        }

        return res;
    }
}