package parallel_algorithms;

public class RaceConditionExample implements Algorithm  {

    private int x = 0;

    @Override
    public String getName() {
        return "Race Condition Example";
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.CONCURRENCY;
    }

    @Override
    public String getDescription() {
        return "Demonstrates a race condition using parallel increments.";
    }

    @Override
    public void execute() {
        x = 0;

        Thread t1 = new Thread(() -> x++);
        Thread t2 = new Thread(() -> x++);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value of x (expected 2): " + x);
    }
}
