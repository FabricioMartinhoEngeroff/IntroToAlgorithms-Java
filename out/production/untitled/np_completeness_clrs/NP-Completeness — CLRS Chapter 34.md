# NP-Completeness — CLRS Chapter 34

## Ideia Central

O capítulo muda o foco do livro: em vez de ensinar como resolver problemas,
ele ensina **quando provavelmente não dá para resolver bem**.

A pergunta central é:

> Existem problemas que computacionalmente são impossíveis de resolver de forma eficiente?

---

## As Classes de Complexidade

### P — Polynomial Time
- Problemas que podem ser **resolvidos** em tempo polinomial.
- Exemplos: ordenação, menor caminho (Dijkstra), fluxo máximo.
- Considerados eficientes na prática.

### NP — Nondeterministic Polynomial
- Problemas cuja solução pode ser **verificada** em tempo polinomial.
- Dado um certificado (solução candidata), checar se é válido é rápido.
- Encontrar a solução pode ser difícil.
- **P ⊆ NP** — todo problema em P também está em NP.

### NP-Hard
- Pelo menos tão difícil quanto qualquer problema em NP.
- Todo problema em NP pode ser reduzido a ele.
- Não precisa estar em NP — pode nem ter verificação polinomial.

### NP-Complete (NPC)
- Está em NP **e** é NP-Hard.
- O "núcleo duro" de NP — os mais difíceis.
- Se qualquer NP-completo for resolvido em tempo polinomial, então **P = NP**.

```
         P ⊆ NP
                \
          NP ∩ NP-Hard = NP-Complete
```

---

## A Grande Pergunta Aberta

> **P = NP ?**

Ou seja: tudo que pode ser verificado rapidamente também pode ser encontrado rapidamente?

- Ninguém sabe.
- É o problema em aberto mais famoso da Ciência da Computação.
- A maioria dos especialistas acredita que **P ≠ NP**, mas ninguém provou.

---

## Verificação — O Coração de NP

### Resolver vs. Verificar

| Ação | Dificuldade |
|---|---|
| Encontrar a solução | Possivelmente exponencial |
| Verificar uma solução candidata | Polinomial (rápido) |

### Exemplo — SAT

- **Encontrar**: existe atribuição V/F para 50 variáveis que satisfaz a fórmula?
  → 2⁵⁰ > 1 quadrilhão de combinações para testar.

- **Verificar**: dado um certificado (x1=T, x2=F, ...), substitui os valores e avalia.
  → O(n × cláusulas) — polinomial e rápido.

### Estrutura de um Certificado

Todo problema em NP tem um verificador que recebe:
1. A instância do problema
2. Um certificado (solução candidata)

E responde: **VÁLIDO** ou **INVÁLIDO** em tempo polinomial.

---

## Redução — A Ferramenta Principal

### O que é uma Redução

```
L1 ≤p L2
```

Significa: qualquer instância de L1 pode ser transformada em instância de L2
em tempo polinomial, **preservando a resposta sim/não**.

### Interpretação Prática

> Se você sabe resolver L2, então sabe resolver L1.
> Logo, L1 não é mais difícil que L2.

### Direção Correta

Para provar que L é NP-Completo:

```
problema NP-completo conhecido  ≤p  L   (←  direção correta)
```

**Nunca ao contrário.**
Reduzir de L para um NP-completo só provaria que L é fácil de usar, não que é difícil.

---

## Como Provar NP-Completeness

### Passo 1 — Provar que L ∈ NP
Mostrar que existe um verificador polinomial para um certificado.

### Passo 2 — Provar que L é NP-Hard
Escolher um problema NP-completo já conhecido (ex: 3-CNF-SAT) e reduzir para L:

```
3-CNF-SAT  ≤p  L
```

A redução deve:
- Transformar qualquer instância de 3-CNF-SAT em instância de L
- Ser executada em tempo polinomial
- Preservar a equivalência: sim ⟺ sim

---

## Cadeia de Reduções do Capítulo

```
CIRCUIT-SAT
    │  ≤p
    ▼
   SAT
    │  ≤p
    ▼
 3-CNF-SAT ─────────────────── ≤p ──▶ SUBSET-SUM
    │  ≤p
    ▼
  CLIQUE
    │  ≤p
    ▼
VERTEX-COVER
    │  ≤p
    ▼
HAM-CYCLE
    │  ≤p
    ▼
   TSP
```

Cada seta significa: o problema de cima foi usado para provar o NP-Completeness do de baixo.

---

## Problemas Clássicos

### CIRCUIT-SAT
- Dado um circuito booleano, existe entrada que produz saída = 1?
- **Primeiro problema provado NP-Completo** (Cook, 1971).

### SAT
- Dada uma fórmula booleana, existe atribuição V/F que a satisfaz?
- Reduz de CIRCUIT-SAT convertendo cada porta em cláusula, cada fio em variável.

### 3-CNF-SAT
- SAT restrito a fórmulas em 3-CNF (AND de cláusulas com exatamente 3 literais cada).
- Ainda NP-Completo mesmo com o formato restrito.

### CLIQUE
- Existe subconjunto de k vértices todos conectados entre si?
- Reduz de 3-CNF-SAT construindo grafo onde literais compatíveis de cláusulas diferentes são conectados.

### VERTEX-COVER
- Existe conjunto de k vértices que cobre todas as arestas?
- Reduz de CLIQUE usando o grafo complementar:
  - Clique de tamanho k em G ⟺ Vertex Cover de tamanho |V|-k no complementar de G.

### HAM-CYCLE
- Existe ciclo que passa por todos os vértices exatamente uma vez?
- Reduz de VERTEX-COVER usando gadgets que forçam o ciclo a simular a escolha de vértices.

### TSP
- Existe tour visitando todas as cidades com custo ≤ k?
- Reduz de HAM-CYCLE usando a estratégia de pesos:
  - Aresta real → custo 0
  - Aresta falsa → custo 1
  - k = 0

### SUBSET-SUM
- Existe subconjunto de números cuja soma é exatamente t?
- Reduz de 3-CNF-SAT codificando variáveis e cláusulas como números sem carry.

---

## Estratégias de Redução

### Ir do Geral para o Específico
Transformar entrada geral em versão mais estruturada.
Ex: SAT → 3-CNF-SAT (fórmula geral → fórmula em formato 3-CNF).

### Usar Problemas do Mesmo Domínio
Facilita a intuição da redução.
Ex: CLIQUE → VERTEX-COVER (ambos em grafos).

### Usar Casos Especiais
Se o problema de destino generaliza o de origem, a redução é direta.

### Recompensas e Penalidades
Usar pesos para forçar comportamento.
Ex: HAM-CYCLE → TSP com custo 0/1.

### Gadgets
Construções artificiais que impõem comportamentos específicos.
Ex: VERTEX-COVER → HAM-CYCLE com peças que controlam o percurso do ciclo.

---

## O que Fazer na Prática

Quando o problema é NP-Completo:

| Abordagem | Quando usar |
|---|---|
| **Heurística / Greedy** | Solução rápida, sem garantia de ótimo |
| **Algoritmo de Aproximação** | Garante solução dentro de fator do ótimo |
| **Branch and Bound** | Instâncias pequenas com poda |
| **Programação Dinâmica** | Casos especiais com subestrutura |
| **Algoritmos FPT** | Parâmetro fixo (ex: k pequeno) |

> Nunca tente um algoritmo exato polinomial para um NP-Completo sem antes questionar se ele realmente resolve o problema geral.

---

## Projeto Java — FastLog NP-Completeness Lab

Este projeto implementa os conceitos acima com contexto real de uma empresa de logística.

| Problema Real | Mapeamento NP-Completo |
|---|---|
| Rota do entregador | TSP |
| Carga do caminhão | Subset Sum |
| Cobertura de rotas | Vertex Cover |
| Regras de serviço | SAT / 3-CNF-SAT |
| Escala de funcionários | Scheduling (reduz de 3-CNF-SAT) |

Para cada problema o programa permite:
1. Ver a classificação de complexidade explicada
2. Verificar certificados passo a passo (demonstração de NP)
3. Entender a cadeia de reduções (NP-Hardness)
