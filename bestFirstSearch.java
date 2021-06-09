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
public class bestFirstSearch<T extends Comparable<T>> { //f(n) = h(n) where h(n) is the demand
    private Graph<Customer,Double> deliveryGraph;
    private final int capacity;
    private ArrayList<Customer> open = new ArrayList<>();
    private ArrayList<Customer> totalRoute = new ArrayList<>();
    private double cost = 0;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();

    public bestFirstSearch(Graph<Customer,Double> deliveryGraph, int capacity) {
        this.deliveryGraph = deliveryGraph;
        this.capacity = capacity;
    }
    
    public void printBestFirstSearch() {
        System.out.println("Best First Search Simulation" + "\nTour");
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
        totalRoute.add(temp.vertexInfo);
        a.addRoute(temp.vertexInfo);
        while (!open.isEmpty()) {
            Vertex<Customer,Double> nextVertex = getNextVertextoVisit(temp, open);
            if (a.addNumDemand(nextVertex.vertexInfo.getDemand())){
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).equals(nextVertex.vertexInfo)) {
                        open.remove(i);
                        a.addRoute(nextVertex.vertexInfo);
                        totalRoute.add(nextVertex.vertexInfo);
                    }
                } 
                temp = nextVertex;
            }else{
                break;
            }
        }
        a.addRoute(deliveryGraph.head.vertexInfo);
        totalRoute.add(deliveryGraph.head.vertexInfo);
        vehicles.add(a);
    }

    public Vertex<Customer,Double> getNextVertextoVisit(Vertex<Customer,Double> current, ArrayList<Customer> adjacents) {
        Vertex<Customer,Double> goalVertex = deliveryGraph.getVertex(adjacents.get(0));
        int currentDemand = adjacents.get(0).getDemand();
        for (int i = 0; i < adjacents.size(); i++) {
            int nextDemand = adjacents.get(i).getDemand();
            if (nextDemand < currentDemand) {
                currentDemand = nextDemand;
                goalVertex = deliveryGraph.getVertex(adjacents.get(i));
            }
        }
        return goalVertex;
    }
    
    public ArrayList<Customer> getAllRoute(){
        return totalRoute;
    }
}

