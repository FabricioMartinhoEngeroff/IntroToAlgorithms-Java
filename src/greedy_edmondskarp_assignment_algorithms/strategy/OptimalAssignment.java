package greedy_edmondskarp_assignment_algorithms.strategy;

import greedy_edmondskarp_assignment_algorithms.model.Cargo;
import greedy_edmondskarp_assignment_algorithms.model.Match;
import greedy_edmondskarp_assignment_algorithms.model.MatchingResult;
import greedy_edmondskarp_assignment_algorithms.model.Truck;

import java.util.ArrayList;
import java.util.List;

public class OptimalAssignment implements MatchingStrategy {
    @Override
    public String getName() {
        return "Optimal Assignment (Brute Force)";
    }

    @Override
    public MatchingResult run(List<Truck> trucks, List<Cargo> cargos, int[][] valueMatrix) {

        int numberTruck = trucks.size();
        int numberCargo = cargos.size();

        // Pré-condição do brute force:
        if (numberCargo < numberTruck) {
            throw new IllegalArgumentException(
                    "Brute force assignment requires cargos >= trucks");
        }

        int[] best = new int[numberTruck];
        int[] permutation = new int[numberTruck];

        boolean[] used = new boolean[numberCargo];

        BestValue holder = new BestValue();

        backtrack(0, permutation, used, best, holder, valueMatrix, numberTruck);

        List<Match> matches = new ArrayList<>();
        int totalValue = 0;

        for (int i = 0; i < numberTruck; i++) {
            int cargoIndex = best[i];

            matches.add(new Match(
                    trucks.get(i),
                    cargos.get(cargoIndex),
                    valueMatrix[i][cargoIndex]
            ));

            totalValue += valueMatrix[i][cargoIndex];
        }

        return new MatchingResult(getName(), matches, totalValue);
    }
    private static class BestValue {
        int value = Integer.MIN_VALUE;
    }

    private void backtrack(int currentPosition, int[] permutation, boolean[] used, int[] best,
                           BestValue holder, int[][] valueMatrix, int n) {

        if (currentPosition == n) {

            int total = 0;
            for (int i = 0; i < n; i++) {
                if (valueMatrix[i][permutation[i]] <= 0) return;
                total += valueMatrix[i][permutation[i]];
            }

            if (total > holder.value) {
                holder.value = total;
                System.arraycopy(permutation, 0, best, 0, n);
            }

            return;

        }

        for (int c = 0; c < n; c++) {
            if (!used[c]) {
                used[c] = true;
                permutation[currentPosition] = c;

                backtrack(currentPosition + 1, permutation, used, best, holder, valueMatrix, n);
                used[c] = false;
            }
        }
    }
}
