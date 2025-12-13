package greedyedmondskarpassignment.strategy;

import greedyedmondskarpassignment.model.Cargo;
import greedyedmondskarpassignment.model.MatchingResult;
import greedyedmondskarpassignment.model.Truck;

import java.util.List;

public interface MatchingStrategy {

    String getName();

    MatchingResult run(List<Truck> trucks,
                       List<Cargo> cargos,
                       int[][] valueMatrix);
}
