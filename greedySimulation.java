package dsgroupassignment;

import java.util.ArrayList;

public class greedySimulation<T extends Comparable<T>> {

    private Graph deliveryGraph;
    private final int capacity;
    private ArrayList<Vertex> visited = new ArrayList<>();
    private ArrayList<T> open = new ArrayList<>();
    private double cost = 0;
    private int maximumCapacity;

    public greedySimulation(Graph deliveryGraph, int capacity) {
        this.deliveryGraph = deliveryGraph;
        this.capacity = capacity;
    }

    public Graph getDeliveryGraph() {
        return deliveryGraph;
    }

    public void setDeliveryGraph(Graph deliveryGraph) {
        this.deliveryGraph = deliveryGraph;
    }

    public int getCapacity() {
        return capacity;
    }

    public void getTotalRoute() {
        System.out.println("Greedy Simulation" + "\nTour");
        open = deliveryGraph.getNeighbours(deliveryGraph.head.vertexInfo);
        int index = 1;
        while (!open.isEmpty()) {
            System.out.println("Vehicle " + index);
            ArrayList<Vertex> route = getRoute();
            for (int i=0;i<route.size()-1;i++){
                System.out.print(route.get(i).ID + " -> ");
            }
            System.out.println("0 ");
            System.out.println("Cost : " + getCost(route));
            cost += getCost(route);
            index++;
            System.out.println("");
        }
        System.out.println("Tour cost " + cost);
    }

    public ArrayList<Vertex> getRoute() {
        ArrayList<T> adjacents = new ArrayList<>();
        for(int i = 0 ;i < open.size() ; i++){
            adjacents.add(open.get(i));
        }
        visited.clear();
        maximumCapacity = 0;
        Vertex temp = deliveryGraph.head;
        visited.add(temp);
        while (!adjacents.isEmpty() && maximumCapacity<=capacity) {
            Vertex nextVertex = getNextVertextoVisit(temp, adjacents);
            maximumCapacity += nextVertex.capacity;
            for (int i = 0; i < adjacents.size(); i++) {
                if (adjacents.get(i).equals(nextVertex.vertexInfo)) {
                    adjacents.remove(i);
                }
            }
            if (maximumCapacity <= capacity){
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).equals(nextVertex.vertexInfo)) {
                        visited.add(nextVertex);
                        open.remove(i);
                    }
                } 
                temp = nextVertex;
            }else{
                maximumCapacity -= nextVertex.capacity;
            }
        }
        System.out.println("Capacity : "+ maximumCapacity);
        visited.add(deliveryGraph.head);
        return visited;
    }

    public Vertex getNextVertextoVisit(Vertex current, ArrayList<T> adjacents) {
        Vertex goalVertex = deliveryGraph.getTheVertex(adjacents.get(0));
        double currentWeight = deliveryGraph.calculateEdgeWeight(current.vertexInfo, adjacents.get(0));
        for (int i = 0; i < adjacents.size(); i++) {
            double nextWeight = deliveryGraph.calculateEdgeWeight(current.vertexInfo, adjacents.get(i));
            if (nextWeight < currentWeight) {
                currentWeight = nextWeight;
                goalVertex = deliveryGraph.getTheVertex(adjacents.get(i));
            }
        }
        return goalVertex;
    }

    public double getCost(ArrayList<Vertex> route) {
        double totalCost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalCost += deliveryGraph.calculateEdgeWeight(route.get(i).vertexInfo, route.get(i + 1).vertexInfo);
        }
        return totalCost;
    }

}

