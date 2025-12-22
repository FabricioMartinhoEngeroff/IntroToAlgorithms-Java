package greedy_edmondskarp_assignment_algorithms.model;

public class Truck {

    private int id;
    private String name;
    private int minRequirement;

    public Truck(int id, String name, int minRequirement) {
        this.id = id;
        this.name = name;
        this.minRequirement = minRequirement;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getMinRequirement() { return minRequirement; }

    @Override
    public String toString() {
        return "Truck{" + name + ", minReq=" + minRequirement + "}";
    }
}
