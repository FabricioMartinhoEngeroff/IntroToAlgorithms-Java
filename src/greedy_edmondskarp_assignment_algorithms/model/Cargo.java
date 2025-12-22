package greedy_edmondskarp_assignment_algorithms.model;

public class Cargo {

    private int id;
    private String name;
    private int baseValue;

    public Cargo(int id, String name, int baseValue) {
        this.id = id;
        this.name = name;
        this.baseValue = baseValue;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getBaseValue() { return baseValue; }

    @Override
    public String toString() {
        return "Cargo{" + name + ", baseValue=" + baseValue + "}";
    }
}
