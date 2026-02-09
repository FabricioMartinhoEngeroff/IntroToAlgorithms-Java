package stringMatching;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private final List<Integer> positions = new ArrayList<>();
    private long comparisons;
    private long elapsedNanos;

    public void addPosition(int pos) { positions.add(pos); }
    public List<Integer> getPositions() { return positions; }

    public long getComparisons() { return comparisons; }
    public void setComparisons(long comparisons) { this.comparisons = comparisons; }

    public long getElapsedNanos() { return elapsedNanos; }
    public void setElapsedNanos(long elapsedNanos) { this.elapsedNanos = elapsedNanos; }

    @Override
    public String toString() {
        return "positions=" + positions +
                ", comparisons=" + comparisons +
                ", elapsed=" + elapsedNanos + " ns";
    }
}
