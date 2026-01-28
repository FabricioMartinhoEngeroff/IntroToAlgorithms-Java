Algoritmos Implementados
1. MDC (Algoritmo de Euclides)
   Complexidade: O(log min(a,b))
   Calcula o maior divisor comum usando divisões sucessivas.
   Exemplo: gcd(48, 18)
   48 mod 18 = 12
   18 mod 12 = 6
   12 mod 6 = 0
   → MDC = 6

2. Euclides Estendido (Inverso Modular)
   Complexidade: O(log min(a,b))
   Encontra coeficientes x e y tais que: ax + by = gcd(a,b)
   Aplicação: Se gcd(a,b) = 1, então x é o inverso modular de a mod b
   Exemplo: a=7, b=26
   7×(-11) + 26×3 = 1
   → 7⁻¹ mod 26 = 15 (porque -11 mod 26 = 15)

3. Exponenciação Modular (Repeated Squaring)
   Complexidade: O(log b) multiplicações
   Calcula a^b mod n eficientemente usando os bits do expoente.
   Exemplo: 3^13 mod 7
   13 = 1101₂ (binário)
   3¹ × 3⁴ × 3⁸ mod 7 = 6
   Princípio:

Bit 1: multiplica resultado pela base
Sempre: base = base² mod n


4. Teste Pseudoprimo (Fermat Base 2)
   Complexidade: O(log n)
   Pequeno Teorema de Fermat: Se n é primo, então 2^(n-1) ≡ 1 (mod n)
   Se 2^(n-1) mod n ≠ 1 → COMPOSTO (certeza)
   Se 2^(n-1) mod n = 1 → POSSIVELMENTE PRIMO (incerteza)
   Limitação: Números de Carmichael passam no teste mesmo sendo compostos.

5. Miller-Rabin (Teste Probabilístico Forte)
   Complexidade: O(s × log³ n) onde s = tentativas
   Probabilidade de erro: ≤ 2^(-s)

s=5 → erro < 3.1%
s=10 → erro < 0.1%
s=20 → erro < 0.0001%

Princípio:

Escreve n-1 = 2^t × u (u ímpar)
Testa s bases aleatórias
Procura por "testemunhas" de que n é composto
Se nenhuma testemunha for encontrada → PROVAVELMENTE PRIMO


6. RSA: Geração de Chaves
   Complexidade: O(k³) onde k = número de bits
   Passos:

Gera dois primos grandes: p e q
Calcula n = p × q (módulo público)
Calcula φ(n) = (p-1)(q-1) (função totiente de Euler)
Escolhe e coprimo com φ(n) (geralmente 65537)
Calcula d = e⁻¹ mod φ(n) (inverso multiplicativo)

Chaves:

🔓 Pública: (e, n)
🔐 Privada: (d, n)


7. RSA: Encrypt/Decrypt
   Complexidade: O(log e) para cifrar, O(log d) para decifrar
   Operações:

Cifrar: c = m^e mod n
Decifrar: m = c^d mod n

Segurança: Baseia-se na dificuldade de fatorar n em p×q

Executar o Programa
bash# No IntelliJ: Run → Run 'Main'
# Ou: Shift + F10
```

### Exemplo de Fluxo

1. **Escolher algoritmo** do menu (1-7)
2. **Inserir dados** conforme solicitado
3. **Ver resultado** formatado com passos
4. **Pressionar Enter** para voltar ao menu

### Exemplo Prático: RSA Completo
```
1. Escolha opção 6 (RSA: Gerar Par de Chaves)
   → Digite: 512 (bits)
   → Chaves geradas!

2. Escolha opção 7 (RSA: Encrypt/Decrypt)
   → Opção 1 (Encrypt)
   → Digite mensagem: 42
   → Ciphertext: 123456...

3. Escolha opção 7 novamente
   → Opção 2 (Decrypt)
   → Digite ciphertext: 123456...
   → Mensagem original: 42
```

---

## 🧠 Dicas para Estudar/Decorar

### 1. Euclides (MDC)
**Mnemônico:** "Mod até zero, último não-zero é o MDC"
```
while b ≠ 0:
r = a mod b
a = b
b = r
```

### 2. Euclides Estendido
**Mnemônico:** "Sobe recursão trocando x↔y e subtraindo quociente×y"
```
if b = 0: return (a, 1, 0)
else:
(g, x', y') = egcd(b, a mod b)
x = y'
y = x' - (a/b) × y'
```

### 3. Exponenciação Modular
**Mnemônico:** "Bits: 1=multiplica, sempre quadra"
```
Para 3^13 mod 7:
13 = 1101₂
↑ ↑ ↑ ↑
| | | └─ bit 1: multiplica
| | └─── bit 0: pula
| └───── bit 1: multiplica
└─────── bit 1: multiplica
```

### 4. Miller-Rabin
**Mnemônico:** "Quadra até n-1 ou acha raiz de 1"
```
x₀ = a^u mod n
x₁ = x₀² mod n
x₂ = x₁² mod n
...
Se xᵢ = 1 mas xᵢ₋₁ ≠ ±1 → COMPOSTO!
```

### 5. RSA
**Mnemônico:** "p×q=n, phi=(p-1)(q-1), e×d≡1 (mod phi)"
```
Geração: p,q → n → φ → e → d
Uso:     m → c (cifra)  ou  c → m (decifra)


Tabelas em Formato Texto para MarkdownComplexidades dos Algoritmos╔══════════════════════════╦════════════════════════╦═══════════════════════╗
║ Algoritmo                ║ Complexidade Temporal  ║ Complexidade Espacial ║
╠══════════════════════════╬════════════════════════╬═══════════════════════╣
║ Euclides                 ║ O(log min(a,b))        ║ O(1)                  ║
║ Euclides Estendido       ║ O(log min(a,b))        ║ O(log min(a,b))       ║
║ Mod Exp                  ║ O(log b) mults         ║ O(1)                  ║
║ Fermat                   ║ O(log n)               ║ O(1)                  ║
║ Miller-Rabin             ║ O(s × log³ n)          ║ O(1)                  ║
║ RSA Geração              ║ O(k³)                  ║ O(k)                  ║
║ RSA Encrypt/Decrypt      ║ O(log e) ou O(log d)   ║ O(1)                  ║
╚══════════════════════════╩════════════════════════╩═══════════════════════

Estrutura do Projeto (Árvore ASCII)
number-theory-lab/
│
└── src/
└── lab/
│
├── Main.java ...................... Ponto de entrada
│
├── core/ .......................... Classes base
│   ├── Algorithm.java ............. Interface
│   ├── AlgorithmResult.java ....... Formatação
│   └── InputUtils.java ............ Validação de entrada
│
├── ui/ ............................ Interface usuário
│   └── ConsoleMenu.java ........... Menu interativo
│
└── algorithms/ .................... Implementações
│
├── gcd/ ....................... Máximo Divisor Comum
│   ├── EuclidGCD.java
│   └── ExtendedEuclid.java
│
├── modular/ ................... Aritmética Modular
│   └── ModularExponentiation.java
│
├── primality/ ................. Testes de Primalidade
│   ├── PseudoprimeTest.java
│   └── MillerRabinTest.java
│
└── rsa/ ....................... Criptografia RSA
├── RSAKeyPair.java
├── RSAKeyGenerator.java
└── RSAEncryptor.java