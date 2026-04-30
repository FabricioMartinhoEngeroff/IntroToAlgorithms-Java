# probabilityLab CLRS — Appendix C Cheat Sheet

Mini-resumo do **Appendix C do CLRS (Probability)** com visão de dev. Sem matemática pesada.

---

## 1. Conceitos (resumo curto)

### Sample Space & Event
- **Sample space (S)** — todos os resultados possíveis de um experimento. Ex: `{ HH, HT, TH, TT }`.
- **Event** — subconjunto do espaço amostral. Ex: "pelo menos uma cara" = `{ HH, HT, TH }`.
- **Probability** — `P = casos favoráveis / total`. Regras: nunca negativa, tudo junto = 1, sem sobreposição → soma.

### Complemento
- `P(A) = 1 − P(não A)`. Às vezes calcular o contrário é mais fácil.

### Probabilidade Condicional
- `P(A | B) = P(A ∩ B) / P(B)` — chance de A sabendo que B já aconteceu.
- Dev: filtra o espaço amostral para só os casos onde B ocorreu.

### Independência
- A e B são independentes quando `P(A ∩ B) = P(A) · P(B)`.
- Se saber A muda P(B) → **não são independentes**.

### Teorema de Bayes
- `P(A | B) = P(B | A) · P(A) / P(B)` — atualiza a crença com nova evidência.
- Fluxo: prior → evidência → posterior.
- Aparece em: spam filter, detecção de fraude, diagnóstico médico, ML.

### Variável Aleatória
- Função que transforma resultados aleatórios em números.
- **Variável indicadora**: `X_i = 1` se evento i aconteceu, `0` caso contrário. Ferramenta essencial para análise de algoritmos.

### Valor Esperado ⭐
- `E[X] = Σ x · P(X = x)` — média no longo prazo.
- **Linearidade (regra de ouro):** `E[X + Y] = E[X] + E[Y]` mesmo sem independência.
- Usado em: análise de QuickSort, hashing, custo esperado de algoritmos.

### Variância
- `Var(X) = E[X²] − (E[X])²` — dispersão em torno da média.
- Alta variância = imprevisível. Baixa = consistente.
- Se independentes: `Var(X + Y) = Var(X) + Var(Y)`.

### Distribuição Geométrica
- Modela: **quantas tentativas até o primeiro sucesso?**
- `E[tentativas] = 1/p`. Ex: `p = 0.5` → 2 tentativas esperadas.
- Aparece em: hash probing, retry loops, algoritmos randomizados.

### Distribuição Binomial
- Modela: **quantos sucessos em n tentativas independentes?**
- `E[X] = n · p`, `Var(X) = n · p · (1 − p)`.
- Aparece em: A/B testing, taxa de erro, análise de colisões em hashing.

### Resumo em 1 linha
> Probabilidade mede incerteza; valor esperado e linearidade são as ferramentas de análise de algoritmos; distribuições geométrica e binomial modelam os padrões mais comuns em código.

---

## 2. Código

Implementação completa em Java na pasta [`src/`](./src):

| Arquivo | Conceito |
|---|---|
| [`Main.java`](./src/Main.java) | Menu interativo para testar tudo |
| [`probability/ConditionalProbability.java`](./src/probability/ConditionalProbability.java) | P(A\|B) com exemplo de moeda |
| [`probability/Independence.java`](./src/probability/Independence.java) | Teste de independência (moeda vs baralho) |
| [`probability/BayesTheorem.java`](./src/probability/BayesTheorem.java) | Bayes com exemplo de diagnóstico médico |
| [`probability/RandomVariable.java`](./src/probability/RandomVariable.java) | Mapeamento de outcome → número + variável indicadora |
| [`probability/ExpectedValue.java`](./src/probability/ExpectedValue.java) | E[X], linearidade e exemplo com QuickSort |
| [`probability/Variance.java`](./src/probability/Variance.java) | Var(X) com exemplos de dispersão baixa vs alta |
| [`probability/GeometricDistribution.java`](./src/probability/GeometricDistribution.java) | PMF, CDF, E[X] = 1/p e exemplo com hash probing |
| [`probability/BinomialDistribution.java`](./src/probability/BinomialDistribution.java) | PMF com histograma ASCII, E[X] e Var(X) |

### Como rodar

```bash
cd src
javac -d . *.java probability/*.java
java Main
```

O `Main` mostra um menu numerado (1 a 8) — escolha a opção para rodar a demo de cada conceito.
