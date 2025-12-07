/**
* ============================================================
*  PROJETO: Algoritmos de Fluxo e Matching – Arquivo Único
* ============================================================
* Este arquivo contém:
*  - Edge                  → representa arestas e arestas reversas
*  - FlowNetwork           → representa o grafo de fluxo
*  - EdmondsKarp           → algoritmo de fluxo máximo com BFS
*  - FordFulkerson         → algoritmo de fluxo máximo com DFS
*  - BipartiteMatcher      → matching bipartido via fluxo máximo
*  - GraphAlgorithmsMenu   → MENU PRINCIPAL (classe pública)
*
* Cada classe possui explicação completa ANTES da classe.
*
* Basta colar este arquivo na sua IDE e rodar.
*
* Para executar:
*      javac GraphAlgorithmsMenu.java
*      java GraphAlgorithmsMenu
*
* ============================================================
* CONCEITOS IMPORTANTES
* ============================================================
* 1. Fluxo máximo:
*      - Queremos enviar o máximo de fluxo do SOURCE ao SINK
*      - Cada aresta tem capacidade limitada
*      - O fluxo não pode ultrapassar capacidades
*      - O fluxo que entra em um nó deve ser igual ao que sai
*
* 2. Rede Residual:
*      - Mostra quanto "ainda cabe" em cada aresta
*      - Para cada aresta normal, há uma aresta reversa
*      - A reversa permite "desfazer" fluxo previamente colocado
*
* 3. Ford-Fulkerson:
*      - Encontra qualquer caminho aumentante
*      - Usa DFS (pode ser lento em casos ruins)
*
* 4. Edmonds-Karp:
*      - Versão otimizada do Ford–Fulkerson
*      - Usa BFS → garante limite O(VE²)
*      - Sempre pega o caminho mais curto em arestas
*
* 5. Maximum Bipartite Matching:
*      - Grafo bipartido L — R
*      - Queremos formar o maior número de pares L–R
*      - Converter o bipartido em rede de fluxo resolve o problema
*
* ============================================================
  */

import java.util.*;

/* ============================================================
*                    CLASSE EDGE
* ============================================================
* Representa uma aresta do grafo de fluxo.
*
* Arestas têm:
*      - origem (from)
*      - destino (to)
*      - capacidade
*      - fluxo atual
*      - aresta reversa (para desfazer fluxo)
*
* O conceito de ARESTA REVERSA é essencial:
*  Se mandamos 5 unidades de fluxo A→B
*  então existe uma reversa B→A com capacidade 5
*
* Isso permite "desfazer" decisões ruins de fluxo.
* ============================================================
  */
  class Edge {
  int from, to;
  int capacity, flow;
  Edge reverse;

  Edge(int from, int to, int capacity) {
  this.from = from;
  this.to = to;
  this.capacity = capacity;
  this.flow = 0;
  }

  int residualCapacity() {
  return capacity - flow;
  }

  void addFlow(int value) {
  flow += value;
  reverse.flow -= value;
  }
  }


/* ============================================================
*                    CLASSE FLOWNETWORK
* ============================================================
* Representa o grafo de fluxo.
*
* Cada nó tem uma lista de arestas.
*
* Quando adicionamos uma aresta normal, também criamos:
*  - uma aresta reversa com capacidade zero
*
* Isso monta automaticamente o grafo residual.
* ============================================================
  */
  class FlowNetwork {
  private final int n;
  private final List<Edge>[] adj;

  @SuppressWarnings("unchecked")
  FlowNetwork(int n) {
  this.n = n;
  adj = new ArrayList[n];
  for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
  }

  int size() { return n; }

  List<Edge> getEdges(int v) { return adj[v]; }

  void addEdge(int from, int to, int capacity) {
  Edge e1 = new Edge(from, to, capacity);
  Edge e2 = new Edge(to, from, 0);

       e1.reverse = e2;
       e2.reverse = e1;

       adj[from].add(e1);
       adj[to].add(e2);
  }
  }


/* ============================================================
*                    CLASSE EDMONDS-KARP
* ============================================================
* Implementa o algoritmo Edmonds–Karp:
*
*  - Baseado em BFS
*  - Garante melhor eficiência que Ford-Fulkerson
*  - Sempre escolhe o caminho aumentante mais curto
*
* PASSOS:
* 1. Rodar BFS para achar caminho aumentante
* 2. Descobrir gargalo (aresta crítica)
* 3. Aumentar fluxo nesse caminho
* ============================================================
  */
  class EdmondsKarp {

  int maxFlow(FlowNetwork net, int source, int sink) {
  int flow = 0;

       while (true) {
           Edge[] parent = new Edge[net.size()];
           if (!bfs(net, parent, source, sink)) break;

           int bottleneck = Integer.MAX_VALUE;
           for (int v = sink; v != source; v = parent[v].from) {
               bottleneck = Math.min(bottleneck, parent[v].residualCapacity());
           }

           for (int v = sink; v != source; v = parent[v].from) {
               parent[v].addFlow(bottleneck);
           }

           flow += bottleneck;
       }

       return flow;
  }

  private boolean bfs(FlowNetwork net, Edge[] parent, int source, int sink) {
  boolean[] visited = new boolean[net.size()];
  Queue<Integer> q = new ArrayDeque<>();
  q.add(source);
  visited[source] = true;

       while (!q.isEmpty()) {
           int u = q.poll();
           for (Edge e : net.getEdges(u)) {
               int v = e.to;
               if (!visited[v] && e.residualCapacity() > 0) {
                   visited[v] = true;
                   parent[v] = e;
                   q.add(v);
                   if (v == sink) return true;
               }
           }
       }

       return false;
  }
  }


/* ============================================================
*                 CLASSE FORD-FULKERSON (DFS)
* ============================================================
* Versão clássica do algoritmo usando DFS.
*
* MENOS eficiente que Edmonds–Karp.
*
* Mostra a ideia original:
*  - Procura qualquer caminho aumentante
*  - Aumenta o fluxo
* ============================================================
  */
  class FordFulkerson {

  private boolean dfs(FlowNetwork net, int u, int sink, boolean[] visited, Edge[] parent) {
  visited[u] = true;
  if (u == sink) return true;

       for (Edge e : net.getEdges(u)) {
           if (!visited[e.to] && e.residualCapacity() > 0) {
               parent[e.to] = e;
               if (dfs(net, e.to, sink, visited, parent)) return true;
           }
       }
       return false;
  }

  int maxFlow(FlowNetwork net, int source, int sink) {
  int flow = 0;

       while (true) {
           boolean[] visited = new boolean[net.size()];
           Edge[] parent = new Edge[net.size()];

           if (!dfs(net, source, sink, visited, parent)) break;

           int bottleneck = Integer.MAX_VALUE;
           for (int v = sink; v != source; v = parent[v].from) {
               bottleneck = Math.min(bottleneck, parent[v].residualCapacity());
           }

           for (int v = sink; v != source; v = parent[v].from) {
               parent[v].addFlow(bottleneck);
           }

           flow += bottleneck;
       }

       return flow;
  }
  }


/* ============================================================
*                 CLASSE BIPARTITEMATCHER
* ============================================================
* Resolve Maximum Bipartite Matching usando fluxo máximo.
*
* IDEIA:
*  Criamos um grafo de fluxo:
*
*      source → L → R → sink
*
* Cada aresta L→R tem capacidade = 1.
*
* O fluxo máximo corresponde a um matching máximo.
* ============================================================
  */
  class BipartiteMatcher {

  private int nLeft, nRight;

  BipartiteMatcher(int nLeft, int nRight) {
  this.nLeft = nLeft;
  this.nRight = nRight;
  }

  List<int[]> maximumMatching(List<int[]> edgesLR) {

       int total = 2 + nLeft + nRight;
       int source = 0;
       int sink = total - 1;

       FlowNetwork net = new FlowNetwork(total);

       int offsetL = 1;
       int offsetR = 1 + nLeft;

       // source → L
       for (int i = 0; i < nLeft; i++) {
           net.addEdge(source, offsetL + i, 1);
       }

       // L → R
       for (int[] e : edgesLR) {
           net.addEdge(offsetL + e[0], offsetR + e[1], 1);
       }

       // R → sink
       for (int i = 0; i < nRight; i++) {
           net.addEdge(offsetR + i, sink, 1);
       }

       EdmondsKarp ek = new EdmondsKarp();
       ek.maxFlow(net, source, sink);

       List<int[]> match = new ArrayList<>();

       for (int u = 0; u < nLeft; u++) {
           for (Edge e : net.getEdges(offsetL + u)) {
               if (e.to >= offsetR && e.to < offsetR + nRight && e.flow == 1) {
                   match.add(new int[]{u, e.to - offsetR});
               }
           }
       }

       return match;
  }
  }


/* ============================================================
*                 CLASSE PRINCIPAL – MENU
* ============================================================
* Menu interativo para escolher o algoritmo.
*
* 1 – Ford-Fulkerson (DFS)
* 2 – Edmonds–Karp (BFS)
* 3 – Matching Bipartido
* ============================================================
  */
  public class GraphAlgorithmsMenu {

  public static void main(String[] args) {

       Scanner sc = new Scanner(System.in);

       while (true) {

           System.out.println("\n=== MENU DE ALGORITMOS DE GRAFOS ===");
           System.out.println("1 - Ford-Fulkerson (DFS)");
           System.out.println("2 - Edmonds-Karp (BFS)");
           System.out.println("3 - Matching Bipartido");
           System.out.println("0 - Sair");
           System.out.print("Escolha: ");

           int op = sc.nextInt();

           switch (op) {
               case 1 -> rodarFordFulkerson();
               case 2 -> rodarEdmondsKarp();
               case 3 -> rodarMatching();
               case 0 -> { System.out.println("Encerrando..."); return; }
               default -> System.out.println("Opção inválida!");
           }
       }
  }

  private static void rodarFordFulkerson() {
  System.out.println("\n--- FORD-FULKERSON ---");

       FlowNetwork net = new FlowNetwork(6);
       net.addEdge(0,1,10);
       net.addEdge(0,2,5);
       net.addEdge(1,3,8);
       net.addEdge(2,3,4);
       net.addEdge(3,4,15);
       net.addEdge(4,5,10);

       FordFulkerson ff = new FordFulkerson();
       int flow = ff.maxFlow(net, 0, 5);

       System.out.println("Fluxo máximo = " + flow);
  }

  private static void rodarEdmondsKarp() {
  System.out.println("\n--- EDMONDS-KARP ---");

       FlowNetwork net = new FlowNetwork(6);
       net.addEdge(0,1,16);
       net.addEdge(0,2,13);
       net.addEdge(1,2,10);
       net.addEdge(2,1,4);
       net.addEdge(1,3,12);
       net.addEdge(3,2,9);
       net.addEdge(2,4,14);
       net.addEdge(4,3,7);
       net.addEdge(3,5,20);
       net.addEdge(4,5,4);

       EdmondsKarp ek = new EdmondsKarp();
       int flow = ek.maxFlow(net, 0, 5);

       System.out.println("Fluxo máximo = " + flow);
  }

  private static void rodarMatching() {
  System.out.println("\n--- MATCHING BIPARTIDO ---");

       BipartiteMatcher bm = new BipartiteMatcher(3, 3);

       List<int[]> edges = new ArrayList<>();
       edges.add(new int[]{0,0});
       edges.add(new int[]{0,2});
       edges.add(new int[]{1,1});
       edges.add(new int[]{2,0});
       edges.add(new int[]{2,1});

       List<int[]> result = bm.maximumMatching(edges);

       System.out.println("Matching máximo encontrado:");
       for (int[] p : result) {
           System.out.println("L(" + p[0] + ") → R(" + p[1] + ")");
       }
  }
  }