package greedy_edmondskarp_assignment_algorithms.model;

public class Match {

    private Truck truck;
    private Cargo cargo;
    private int  value;

    public Match(Truck truck, Cargo cargo, int value) {
        this.truck = truck;
        this.cargo = cargo;
        this.value = value;
    }

    public Truck getTruck() { return truck; }
    public Cargo getCargo() { return cargo; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        return truck.getName() + " → " + cargo.getName() + " (value = " + value + ")";
    }
}
