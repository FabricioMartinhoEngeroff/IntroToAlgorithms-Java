package MaxFlowAndMatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU DE ALGORITMOS DE GRAFOS ===");
            System.out.println("1 - Ford-Fulkerson (DFS)");
            System.out.println("2 - Edmonds-Karp (BFS)");
            System.out.println("3 - Matching Bipartido (via Fluxo)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> rodarFordFulkerson();
                case 2 -> rodarEdmondsKarp();
                case 3 -> rodarMatching();
                case 0 -> { System.out.println("Saindo..."); return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void rodarFordFulkerson() {
        System.out.println("\n--- FORD-FULKERSON ---");

        FlowNetwork net = new FlowNetwork(6);
        net.addEdge(0, 1, 10);
        net.addEdge(0, 2, 5);
        net.addEdge(1, 3, 8);
        net.addEdge(2, 3, 4);
        net.addEdge(3, 4, 15);
        net.addEdge(4, 5, 10);

        FordFulkerson ff = new FordFulkerson();
        int maxFlow = ff.maxFlow(net, 0, 5);

        System.out.println("Fluxo máximo = " + maxFlow);
    }

    private static void rodarEdmondsKarp() {
        System.out.println("\n--- EDMONDS-KARP ---");

        FlowNetwork net = new FlowNetwork(6);
        net.addEdge(0, 1, 16);
        net.addEdge(0, 2, 13);
        net.addEdge(1, 2, 10);
        net.addEdge(2, 1, 4);
        net.addEdge(1, 3, 12);
        net.addEdge(3, 2, 9);
        net.addEdge(2, 4, 14);
        net.addEdge(4, 3, 7);
        net.addEdge(3, 5, 20);
        net.addEdge(4, 5, 4);

        EdmondsKarp ek = new EdmondsKarp();
        int maxFlow = ek.maxFlow(net, 0, 5);

        System.out.println("Fluxo máximo = " + maxFlow);
    }

    private static void rodarMatching() {
        System.out.println("\n--- MATCHING BIPARTIDO ---");

        BipartiteMatcher bm = new BipartiteMatcher(3, 3);

        List<int[]> edges = new ArrayList<>();
        edges.add(new int[]{0, 0});
        edges.add(new int[]{0, 2});
        edges.add(new int[]{1, 1});
        edges.add(new int[]{2, 0});
        edges.add(new int[]{2, 1});

        List<int[]> match = bm.maximumMatching(edges);

        System.out.println("Matching máximo:");
        for (int[] p : match) {
            System.out.println("L(" + p[0] + ") → R(" + p[1] + ")");
        }
    }
}