package galeshapley;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        StableMatching sm = new StableMatching();

        // Clients
        sm.addClient(new Client("Alice", Arrays.asList("Thiago", "Marcelo", "João", "Rafael")));
        sm.addClient(new Client("Bruna", Arrays.asList("Rafael", "Thiago", "João", "Marcelo")));
        sm.addClient(new Client("Carla", Arrays.asList("João", "Rafael", "Marcelo", "Thiago")));
        sm.addClient(new Client("Denise", Arrays.asList("Thiago", "João", "Rafael", "Marcelo")));

        // Trainers
        sm.addTrainer(new Trainer("Thiago", Arrays.asList("Bruna", "Alice", "Carla", "Denise")));
        sm.addTrainer(new Trainer("Rafael", Arrays.asList("Denise", "Carla", "Bruna", "Alice")));
        sm.addTrainer(new Trainer("Marcelo", Arrays.asList("Alice", "Carla", "Bruna", "Denise")));
        sm.addTrainer(new Trainer("João", Arrays.asList("Carla", "Denise", "Alice", "Bruna")));

        sm.runGaleShapley();
        sm.printResults();
    }
}