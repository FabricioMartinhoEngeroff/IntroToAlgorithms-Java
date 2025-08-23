package DijkstraAlgorithm;

import java.util.*;
import java.util.stream.Stream;

public class Service {

    public void calculatePath(Node start){
        start.setDistance(0);

        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()){
            Node current = priorityQueue.poll();

            for(Map.Entry<Node, Integer> entry : current.getNeighbors().entrySet()){
                Node neighbor = entry.getKey();
                int weight = entry.getValue();

                if(!visited.contains(neighbor)){
                    updateDistance(current, neighbor, weight);
                        priorityQueue.add(neighbor);
                    }
                }
            visited.add(current);
            }
        }

    private void updateDistance(Node current, Node neighbor, int weight) {
        if (current.getDistance() == Integer.MAX_VALUE)
            return;
        int newDistance = current.getDistance() + weight;

        if(newDistance < neighbor.getDistance()){
            neighbor.setDistance(newDistance);

            List<Node> newPath = Stream
                    .concat(current.getShortesPath().stream(),Stream.of(current))
                    .toList();
            neighbor.setShortesPath(newPath);
        }
    }

    public void printPath(List<Node> nodes){
        for(Node node : nodes){
            StringBuilder path = new StringBuilder();
            for(Node step : node.getShortesPath()) {
                path.append(step.getName()).append(" --> ");
            }
            path.append(node.getName());
            System.out.println(path +  " : "  + node.getDistance());
            }
        }

}
