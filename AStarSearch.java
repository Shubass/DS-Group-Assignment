/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime2;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class AStarSearch<T extends Comparable<T>> { //f(n) = g(n) + h(n) where h(n) is the demand and g(n) will be the cost

    private Graph<Customer,Double> deliveryGraph;
    private final int capacity;
    private ArrayList<Customer> open = new ArrayList<>();
    private double cost = 0;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();

    public AStarSearch(Graph<Customer,Double> deliveryGraph, int capacity) {
        this.deliveryGraph = deliveryGraph;
        this.capacity = capacity;
    }
    
    public void printAStarSearch() {
        System.out.println("A* Search Simulation" + "\nTour");
        open = deliveryGraph.getNeighbours(deliveryGraph.head.vertexInfo);
        while (!open.isEmpty()) {
            getRoute();
        }
        for(int i=0 ; i<vehicles.size() ; i++){
            System.out.println("Vehice " + (i+1));
            System.out.println(vehicles.get(i));
            cost += vehicles.get(i).calcRoute();
        }
        System.out.println("Tour cost " + cost);
    }

    public void getRoute() {
        Vehicle a = new Vehicle(capacity);
        Vertex<Customer,Double> temp = deliveryGraph.head;
        a.addRoute(temp.vertexInfo);
        while (!open.isEmpty()) {
            Vertex<Customer,Double> nextVertex = getNextVertextoVisit(temp, open);
            if (a.addNumDemand(nextVertex.vertexInfo.getDemand())){
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).equals(nextVertex.vertexInfo)) {
                        open.remove(i);
                        a.addRoute(nextVertex.vertexInfo);
                    }
                } 
                temp = nextVertex;
            }else{
                break;
            }
        }
        a.addRoute(deliveryGraph.head.vertexInfo);
        vehicles.add(a);
    }

    public Vertex<Customer,Double> getNextVertextoVisit(Vertex<Customer,Double> current, ArrayList<Customer> adjacents) {
        Vertex<Customer,Double> goalVertex = deliveryGraph.getVertex(adjacents.get(0));
        double currentCost = adjacents.get(0).getDemand() + deliveryGraph.getEdgeWeight(current.vertexInfo, goalVertex.vertexInfo);
        for (int i = 0; i < adjacents.size(); i++) {
            double nextCost = adjacents.get(i).getDemand() + deliveryGraph.getEdgeWeight(current.vertexInfo, adjacents.get(i));
            if (nextCost < currentCost) {
                currentCost = nextCost;
                goalVertex = deliveryGraph.getVertex(adjacents.get(i));
            }
        }
        return goalVertex;
    }
}
