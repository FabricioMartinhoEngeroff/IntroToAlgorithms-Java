package galeshapley;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class StableMatching {

    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Trainer> trainers = new HashMap<>();

    public void addClient(Client c){
        clients.put(c.getName(), c);
    }

    public void addTrainer(Trainer t){
        trainers.put(t.getName(), t);
    }

    public void runGaleShapley() {

        Queue<Client> freeClients = new LinkedList<>();

        for (Client c : clients.values()){
            freeClients.add(c);
        }

        while (!freeClients.isEmpty()) {
            Client client = freeClients.poll();

            String trainerName = client.getNextPreferred();
            if (trainerName == null) continue;

            Trainer trainer = trainers.get(trainerName);

            if (trainer.isFree()){
                trainer.setPartner(client);
                client.setPartner(trainer);
            }

            else {
                Client current = (Client)  trainer.getPartner();

                if(trainer.prefers(client.getName())){
                    current.setPartner(null);
                    freeClients.add(current);

                    trainer.setPartner(client);
                    client.setPartner(trainer);

                }else {
                    freeClients.add(client);
                }
            }
        }
    }

    public void printResults() {
        System.out.println("\n=== FINAL MATCHING ===");
        for (Trainer t : trainers.values()) {
            System.out.println(t.getName() + " ↔ " + t.getPartner().getName());
        }
    }
}
