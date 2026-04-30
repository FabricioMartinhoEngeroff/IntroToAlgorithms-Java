# MatrixLab CLRS — Appendix D Cheat Sheet

Mini-resumo do **Appendix D do CLRS (Matrices)** com visão de dev. Sem matemática pesada.

---

## 1. Conceitos (resumo curto)

### Matriz
Array 2D acessado por `[linha][coluna]`. Base para grafos, ML, física e sistemas lineares.

### Vetor
Matriz de 1 dimensão (linha ou coluna).

### Tipos especiais
- **Identidade (`I`)** — diagonal = 1, resto = 0. Neutro da multiplicação: `I * A = A`.
- **Diagonal** — só a diagonal principal é não-zero. Otimiza memória e cálculo.
- **Triangular (upper/lower)** — zeros de um lado da diagonal. Útil em decomposição LU.
- **Tridiagonal** — só diagonal principal + uma acima + uma abaixo. Super eficiente.
- **Permutação** — exatamente um `1` por linha/coluna, resto zero. Reordena dados.
- **Simétrica** — `A == Aᵀ`. Aparece em grafos não-direcionados e otimização.

### Operações
- **Soma/Subtração** — elemento por elemento, exige mesma shape.
- **Multiplicação por escalar** — `k * A` multiplica cada elemento.
- **Multiplicação de matrizes** — só funciona se `A.cols == B.rows`. Custo `O(n³)`. **Não comutativa**: `A*B ≠ B*A`.
- **Transposta** — `(Aᵀ)[i][j] = A[j][i]`.
- **Dot product** — `u · v = Σ uᵢ * vᵢ`. Base de similaridade e distância.
- **Norma** — `||v|| = √(v · v)`. Tamanho do vetor.

### Inversa, Rank e Determinante
- **Rank** — número de linhas/colunas linearmente independentes. Mede "informação útil".
- **Full rank** — `rank == min(rows, cols)`. Matriz "saudável".
- **Determinante** — número que resume a matriz. Se `det == 0` → singular → **sem inversa**.
- **Singular** — não tem inversa. Geralmente tem linhas dependentes.
- **Regra de ouro:** `full rank ⇔ não singular ⇔ tem inversa`.

### Positive-definite
- Para todo vetor `x ≠ 0`: `xᵀ A x > 0`.
- Garante "estabilidade" da transformação. Aparece em ML (loss), otimização e energia.
- **Critério de Sylvester:** simétrica + todos os menores principais > 0.
- **Semidefinite:** mesma ideia, mas permite `≥ 0`.

### Resumo em 1 linha
> Matriz é um array 2D; tipos especiais otimizam cálculo e memória; rank e determinante dizem se a matriz é "saudável" (invertível); positive-definite garante estabilidade.

---

## 2. Código

Implementação completa em Java na pasta [`src/`](./src):

| Arquivo | Conceito |
|---|---|
| [`Main.java`](./src/Main.java) | Menu interativo para testar tudo |
| [`Matrix.java`](./src/Matrix.java) | Estrutura imutável + identidade + transposta |
| [`MatrixOperations.java`](./src/MatrixOperations.java) | Soma, subtração, escalar, multiplicação, dot, norma |
| [`MatrixTypes.java`](./src/MatrixTypes.java) | Identidade, diagonal, triangular, tridiagonal, permutação, simétrica |
| [`RankAndDeterminant.java`](./src/RankAndDeterminant.java) | Determinante (Laplace), rank (Gauss), singular, full rank |
| [`PositiveDefinite.java`](./src/PositiveDefinite.java) | Positive-definite e semidefinite via Sylvester |

### Como rodar

```bash
cd src
javac *.java
java Main
```

O `Main` mostra um menu numerado — basta escolher a opção (1 a 10) para rodar a demo de cada conceito.
