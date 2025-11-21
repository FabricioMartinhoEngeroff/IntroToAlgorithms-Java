package dijkstra;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println(" 🚗 Dijkstra RS - Escolha da Estrutura Heap ");
        System.out.println("===========================================\n");

        Node bomPrincipio = new Node("Bom Princípio");
        Node feliz = new Node("Feliz");
        Node saoSebastiao = new Node("São Sebastião do Caí");
        Node harmonia = new Node("Harmonia");
        Node tupandi = new Node("Tupandi");
        Node valeReal = new Node("Vale Real");
        Node saoVendelino = new Node("São Vendelino");
        Node barao = new Node("Barão");
        Node carlosBarbosa = new Node("Carlos Barbosa");
        Node garibaldi = new Node("Garibaldi");

        bomPrincipio.addNeighbor(feliz, 7);
        bomPrincipio.addNeighbor(saoSebastiao, 10);

        feliz.addNeighbor(bomPrincipio, 7);
        feliz.addNeighbor(tupandi, 6);
        feliz.addNeighbor(valeReal, 8);

        saoSebastiao.addNeighbor(bomPrincipio, 10);
        saoSebastiao.addNeighbor(harmonia, 12);
        saoSebastiao.addNeighbor(tupandi, 9);

        harmonia.addNeighbor(saoSebastiao, 12);
        harmonia.addNeighbor(valeReal, 9);

        tupandi.addNeighbor(feliz, 6);
        tupandi.addNeighbor(saoSebastiao, 9);
        tupandi.addNeighbor(saoVendelino, 10);

        valeReal.addNeighbor(feliz, 8);
        valeReal.addNeighbor(harmonia, 9);
        valeReal.addNeighbor(saoVendelino, 7);

        saoVendelino.addNeighbor(tupandi, 10);
        saoVendelino.addNeighbor(valeReal, 7);
        saoVendelino.addNeighbor(barao, 15);

        barao.addNeighbor(saoVendelino, 15);
        barao.addNeighbor(carlosBarbosa, 12);

        carlosBarbosa.addNeighbor(barao, 12);
        carlosBarbosa.addNeighbor(garibaldi, 8);

        garibaldi.addNeighbor(carlosBarbosa, 8);

        List<Node> todasCidades = List.of(
                bomPrincipio, feliz, saoSebastiao, harmonia, tupandi,
                valeReal, saoVendelino, barao, carlosBarbosa, garibaldi
        );

        System.out.println("Escolha qual estrutura de heap deseja usar:");
        System.out.println("1 - PriorityQueue (padrão do Java)");
        System.out.println("2 - MinHeap (implementada à mão)");
        System.out.println("3 - Fibonacci Heap (simples)");
        System.out.print("\nDigite a opção: ");

        int opcao = scanner.nextInt();

        Service service;

        switch (opcao) {
            case 1:
                service = new Service(new MinPriorityQueue<Node>() {
                    PriorityQueue<Node> pq = new PriorityQueue<>();
                    @Override public void insert(Node value) { pq.add(value); }
                    @Override public Node extractMin() { return pq.poll(); }
                    @Override public boolean isEmpty() { return pq.isEmpty(); }
                });
                System.out.println("\n👉 Usando PriorityQueue do Java");
                break;

            case 2:
                service = new Service(new MinHeapNode(100));
                System.out.println("\n👉 Usando MinHeap implementada à mão");
                break;

            case 3:
                service = new Service(new FibonacciHeap());
                System.out.println("\n👉 Usando Fibonacci Heap (simplificada)");
                break;

            default:
                System.out.println("\nOpção inválida. Usando PriorityQueue por padrão.");
                service = new Service(new MinPriorityQueue<Node>() {
                    PriorityQueue<Node> pq = new PriorityQueue<>();
                    @Override public void insert(Node value) { pq.add(value); }
                    @Override public Node extractMin() { return pq.poll(); }
                    @Override public boolean isEmpty() { return pq.isEmpty(); }
                });
                break;
        }

        System.out.println("\n📍 Cidade inicial: Bom Princípio");
        service.calculatePath(bomPrincipio);

        System.out.println("\n===== Caminhos calculados =====\n");
        service.printPath(todasCidades);
    }
}