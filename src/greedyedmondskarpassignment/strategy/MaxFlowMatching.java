package greedyedmondskarpassignment.strategy;

import greedyedmondskarpassignment.model.Cargo;
import greedyedmondskarpassignment.model.Match;
import greedyedmondskarpassignment.model.MatchingResult;
import greedyedmondskarpassignment.model.Truck;

import java.util.*;

public class  MaxFlowMatching implements MatchingStrategy {

    @Override
    public String getName() {
        return "Max-Flow Matching";
    }

    @Override
    public MatchingResult run(List<Truck> trucks, List<Cargo> cargos, int[][] valueMatrix) {

        int numberTruck = trucks.size();
        int numberCargo = cargos.size();

        if (numberCargo < numberTruck) {
            throw new IllegalArgumentException("Cargos must be >= Trucks");
        }


        int source = 0;
        int sink = numberTruck + numberCargo + 1;
        int totalNode = sink + 1;

        int[][] capacity = new int[totalNode][totalNode];
        int[][] flow = new int[totalNode][totalNode];

        for (int t = 0; t < numberTruck; t++) {
            capacity[source][1 + t] = 1;
        }

        for (int t = 0; t < numberTruck; t++) {
            for (int c = 0; c < numberCargo; c++) {
                if (valueMatrix[t][c] > 0) {
                    capacity[1 + t][1 + numberTruck + c] = 1;
                }
            }
        }

        for (int c = 0; c < numberCargo; c++) {
            capacity[1 + numberTruck + c][sink] = 1;
        }

        int maxFlow = edmondsKarp(source, sink, capacity, flow);

        List<Match> matches = new ArrayList<>();
        int totalValue = 0;

        for (int t = 0; t < numberTruck; t++) {
            for (int c = 0; c < numberCargo; c++) {
                if (flow[1 + t][1 + numberTruck + c] > 0) {
                    matches.add(new Match(
                            trucks.get(t),
                            cargos.get(c),
                            valueMatrix[t][c]
                    ));
                    totalValue += valueMatrix[t][c];
                }
            }
        }

        return new MatchingResult(
                getName() + " (maxFlow=" + maxFlow + ")",
                matches,
                totalValue
        );
    }

    private int edmondsKarp(int source, int sink, int[][] capacity, int[][] flow) {

        int n = capacity.length;
        int maxFlow = 0;

        while (true) {

            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            parent[source] = source;

            int[] minCap = new int[n];
            minCap[source] = Integer.MAX_VALUE;

            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(source);

            while (!queue.isEmpty() && parent[sink] == -1) {
                int u = queue.poll();

                for (int v = 0; v < n; v++) {
                    int residual = capacity[u][v] - flow[u][v];
                    if (residual > 0 && parent[v] == -1) {
                        parent[v] = u;
                        minCap[v] = Math.min(minCap[u], residual);
                        queue.add(v);
                    }
                }
            }

            if (parent[sink] == -1) break;

            int inc = minCap[sink];
            maxFlow += inc;

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += inc;
                flow[v][u] -= inc;
            }
        }

        return maxFlow;
    }
}