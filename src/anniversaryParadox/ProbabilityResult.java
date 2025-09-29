package anniversaryParadox;

public class ProbabilityResult {

    private final int people;
    private final double probabilityMatch; // 0..1

    public ProbabilityResult(int people, double probabilityMatch) {
        this.people = people;
        this.probabilityMatch = probabilityMatch;
    }

    public int getPeople() { return people; }
    public double getProbabilityMatch() { return probabilityMatch; }

    @Override
    public String toString() {
        return String.format("Pessoas: %2d -> Coincidência: %.2f%%",
                people, probabilityMatch * 100.0);
    }
}
