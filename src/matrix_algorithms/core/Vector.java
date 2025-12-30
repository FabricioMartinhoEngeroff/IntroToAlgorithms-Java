package matrix_algorithms.core;

public class Vector {

    private final int size;
    private final double[] data;

    public Vector(int size) {
        this.size = size;
        this.data = new double[size];
    }

    public Vector(double[] data) {
        this.size = data.length;
        this.data = data;
    }

    public double get(int i) {
        return data[i];
    }

    public void set(int i, double value) {
        data[i] = value;
    }

    public int size() {
        return size;
    }
}
