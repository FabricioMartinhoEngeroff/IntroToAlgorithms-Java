package greedyedmondskarpassignment;

import java.util.*;

import greedyedmondskarpassignment.model.Cargo;
import greedyedmondskarpassignment.model.MatchingResult;
import greedyedmondskarpassignment.model.Truck;
import greedyedmondskarpassignment.strategy.GreedyMatching;
import greedyedmondskarpassignment.strategy.MatchingStrategy;
import greedyedmondskarpassignment.strategy.MaxFlowMatching;
import greedyedmondskarpassignment.strategy.OptimalAssignment;

public class TruckMatchingDemo {

    public static void main(String[] args) {

        List<Truck> trucks = List.of(
                new Truck(0, "Truck A", 8),
                new Truck(1, "Truck B", 7),
                new Truck(2, "Truck C", 6),
                new Truck(3, "Truck D", 5)
        );

        List<Cargo> cargos = List.of(
                new Cargo(0, "Cargo X", 10),
                new Cargo(1, "Cargo Y", 9),
                new Cargo(2, "Cargo Z", 8),
                new Cargo(3, "Cargo W", 7)
        );

        int[][] value = {
                {10, 5, 0, 6},
                {9, 8, 7, 0},
                {0, 6, 11, 5},
                {4, 0, 9, 10}
        };

        List<MatchingStrategy> strategies = List.of(
                new GreedyMatching(),
                new MaxFlowMatching(),
                new OptimalAssignment()
        );

        System.out.println("=== TRUCK MATCHING LAB ===");
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\nChoose algorithm:");
            for (int i = 0; i < strategies.size(); i++)
                System.out.println(" " + (i + 1) + " - " + strategies.get(i).getName());
            System.out.println(" 0 - Exit");

            System.out.print("Option: ");
            int opt = Integer.parseInt(sc.nextLine());

            if (opt == 0) break;

            MatchingResult result = strategies.get(opt - 1).run(trucks, cargos, value);
            result.print();
        }

        sc.close();
    }
}

