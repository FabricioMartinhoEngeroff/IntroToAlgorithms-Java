package parallel_algorithms;

public interface Algorithm {

    String getName();

    AlgorithmType getType();

    String getDescription();

    void execute();

}
