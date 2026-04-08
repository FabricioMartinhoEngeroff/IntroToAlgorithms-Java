# Approximation Algorithms — CLRS Capítulo 35

## Ideia Central

Problemas NP-completos não têm solução exata eficiente (a menos que P=NP).
A saída é usar **algoritmos de aproximação**: rápidos e com **garantia de qualidade**.

---

## Razão de Aproximação (ρ)

Mede o quanto a solução encontrada se afasta da ótima:

- ρ = 1 → solução perfeita
- ρ = 2 → no máximo o dobro do ótimo
- Quanto menor ρ, melhor o algoritmo

---

## Algoritmos Implementados

### 1. Vertex Cover (2-aprox) — CLRS 35.1

**Ideia:** escolhe qualquer aresta, adiciona **ambos** os vértices, remove arestas incidentes. Repete.

**Passo a passo:**
1. Começa com cobertura vazia
2. Enquanto houver arestas: pega aresta (u,v), adiciona u e v, remove todas as arestas de u e v
3. Retorna a cobertura

**Garantia:** |C| ≤ 2 × |C\*| — nunca mais que o dobro do ótimo.

---

### 2. TSP com Triangle Inequality (2-aprox) — CLRS 35.2

**Pré-condição:** dist(u,w) ≤ dist(u,v) + dist(v,w) para quaisquer u, v, w.

**Passo a passo:**
1. Calcula a MST (Prim) a partir do vértice raiz
2. Percorre a MST em pré-ordem (DFS)
3. Os vértices nessa ordem formam o tour (ciclo hamiltoniano)

**Garantia:** custo_tour ≤ 2 × ótimo.

**Sem triangle inequality:** não existe algoritmo de aproximação eficiente com garantia constante (prova por redução do Hamiltonian Cycle).

---

### 3. Set Cover Greedy (O(log n)-aprox) — CLRS 35.3

**Ideia:** a cada passo, escolhe o subconjunto que cobre mais elementos ainda descobertos.

**Passo a passo:**
1. Começa com todos os elementos sem cobertura
2. Enquanto houver elementos: escolhe o conjunto de maior cobertura, adiciona à solução, marca elementos cobertos
3. Retorna os subconjuntos escolhidos

**Garantia:** |solução| ≤ H(max\_size) × ótimo ≈ ln(n) × ótimo.

---

### 4. MAX-3-CNF Aleatorizado (7/8-aprox) — CLRS 35.4

**Ideia:** atribui true/false aleatoriamente a cada variável com prob. 1/2.

**Por que funciona:**
- Cláusula com 3 literais é insatisfeita somente se todos os 3 são falsos → Pr = (1/2)³ = 1/8
- Logo, Pr[satisfeita] = 7/8 por cláusula
- Por linearidade da esperança: E[satisfeitas] = 7/8 × total

**Garantia:** E[satisfeitas] ≥ 7/8 × OPT (em esperança, não determinístico).

---

### 5. Weighted Vertex Cover via LP Rounding (2-aprox) — CLRS 35.4

**Ideia:** resolve a versão LP relaxada (x ∈ [0,1]) e arredonda para 0 ou 1.

**Passo a passo:**
1. Configura o LP: minimizar Σ w[v]·x[v], sujeito a x[u]+x[v] ≥ 1 por aresta
2. Resolve o LP relaxado (simula via matching maximal)
3. Arredonda: inclui vértice v se x[v] ≥ 0.5

**Garantia:** custo ≤ 2 × ótimo — arredondar por 0.5 pode no máximo dobrar o custo.

---

### 6. Subset Sum Exato — CLRS 35.5

**Ideia:** constrói incrementalmente todas as somas possíveis ≤ alvo.

**Passo a passo:**
1. Começa com lista = {0}
2. Para cada item: gera novas somas = lista + item, junta, remove > alvo, elimina duplicatas
3. Retorna a maior soma da lista final

**Qualidade:** ótima. **Limitação:** cresce exponencialmente para entradas grandes.

---

### 7. Subset Sum FPTAS (Fully Polynomial-Time Approximation Scheme) — CLRS 35.5

**Ideia:** igual ao exato, mas aplica **trimming** a cada iteração para limitar o tamanho da lista.

**Trimming com parâmetro δ:**
Percorre a lista ordenada e remove um valor y se existe um representante recente z com y ≤ z·(1+δ). Mantém apenas valores significativamente diferentes.

**Parâmetro ε:** controla o erro aceito.
- ε menor → mais preciso, lista maior, mais lento
- ε maior → menos preciso, lista menor, mais rápido

**Garantia:** resultado ≥ (1−ε) × ótimo.
**Complexidade:** O(n²/ε) — polinomial em n e em 1/ε.

---

## Tipos de Esquema de Aproximação

| Tipo    | Descrição |
|---------|-----------|
| Constante (ρ fixo) | Garantia fixa, independente do tamanho (ex: 2-aprox) |
| Logarítmica | Solução cresce como ln(n) em relação ao ótimo |
| PTAS    | Escolhe precisão ε; tempo polinomial em n para ε fixo |
| FPTAS   | Como PTAS, mas polinomial em n **e** em 1/ε |

---

## Tema RouteOptix (demos do projeto)

| Algoritmo | Cenário simulado |
|-----------|-----------------|
| Vertex Cover | Quais depósitos cobrem todas as rotas? |
| TSP | Rota de entrega passando por todas as cidades |
| Set Cover | Quais equipes cobrem todos os tipos de entrega? |
| MAX-3-CNF | Restrições de entrega podem ser satisfeitas? |
| Weighted VC | Depósitos com menor custo cobrindo todas as rotas |
| Subset Sum | Carregar carga o mais próximo do limite do caminhão |
