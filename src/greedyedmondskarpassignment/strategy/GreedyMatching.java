package greedyedmondskarpassignment.strategy;

import greedyedmondskarpassignment.model.Cargo;
import greedyedmondskarpassignment.model.Match;
import greedyedmondskarpassignment.model.MatchingResult;
import greedyedmondskarpassignment.model.Truck;

import java.util.ArrayList;
import java.util.List;

public class GreedyMatching implements MatchingStrategy{

    @Override
    public String getName() {
        return "Greedy Matching";
    }

    @Override
    public MatchingResult run(List<Truck> trucks, List<Cargo> cargos, int[][] valueMatrix) {
        boolean[] used = new boolean[cargos.size()];
        List<Match> matches = new ArrayList<>();
        int total = 0;

        for (int t = 0; t < trucks.size(); t++) {
            for (int c = 0; c < cargos.size(); c++) {

                if (!used[c] && valueMatrix[t][c] > 0) {
                    used[c] = true;

                    matches.add(new Match(
                            trucks.get(t),
                            cargos.get(c),
                            valueMatrix[t][c]
                    ));

                    total += valueMatrix[t][c];
                    break;
                }
            }
        }

        return new MatchingResult(getName(), matches, total);
    }
}
