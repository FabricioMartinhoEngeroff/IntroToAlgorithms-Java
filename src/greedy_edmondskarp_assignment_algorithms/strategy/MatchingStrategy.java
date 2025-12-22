package greedy_edmondskarp_assignment_algorithms.strategy;

import greedy_edmondskarp_assignment_algorithms.model.Cargo;
import greedy_edmondskarp_assignment_algorithms.model.MatchingResult;
import greedy_edmondskarp_assignment_algorithms.model.Truck;

import java.util.List;

public interface MatchingStrategy {

    String getName();

    MatchingResult run(List<Truck> trucks,
                       List<Cargo> cargos,
                       int[][] valueMatrix);
}
