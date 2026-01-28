package number_theory_algorithms.lab.algoritms.rsa;

import java.math.BigInteger;

// Par de chaves RSA: pública (e,n) e privada (d,n)
public class RSAKeyPair {
    public final BigInteger n;   // módulo (público)
    public final BigInteger e;   // expoente público
    public final BigInteger d;   // expoente privado (secreto)

    public RSAKeyPair(BigInteger n, BigInteger e, BigInteger d) {
        this.n = n;
        this.e = e;
        this.d = d;
    }

    @Override
    public String toString() {
        return String.format("🔓 Pública: (e=%s, n=%s...)\n🔐 Privada: (d=%s..., n=%s...)",
                e, truncate(n), truncate(d), truncate(n));
    }

    private String truncate(BigInteger x) {
        String s = x.toString();
        return s.length() > 20 ? s.substring(0, 20) + "..." : s;
    }
}

