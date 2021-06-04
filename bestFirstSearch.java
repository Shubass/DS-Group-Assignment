/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgroupassignment;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class bestFirstSearch<T extends Comparable<T>> { //f(n) = h(n) where h(n) is the demand
    private Graph deliveryGraph;
    private final int capacity;
    private ArrayList<Vertex> visited = new ArrayList<>();
    private ArrayList<T> open = new ArrayList<>();
    private double cost = 0;
    private int demand;

    public bestFirstSearch(Graph deliveryGraph, int capacity) {
        this.deliveryGraph = deliveryGraph;
        this.capacity = capacity;
    }
    
    public void getTotalRoute() {
        System.out.println("Best First Search Simulation" + "\nTour");
        open = deliveryGraph.getNeighbours(deliveryGraph.head.vertexInfo);
        int index = 1;
        while (!open.isEmpty()) {
            System.out.println("Vehicle " + index);
            ArrayList<Vertex> route = getRoute();
            System.out.println(route);
            cost += getCost(route);
            index++;
        }
        System.out.println("Tour cost " + cost);
    }

    public ArrayList<Vertex> getRoute() {
        visited.clear();
        demand = 0;
        Vertex temp = deliveryGraph.head;
        visited.add(temp);
        while (!open.isEmpty() && demand<=capacity) {
            Vertex nextVertex = getNextVertextoVisit(temp, open);
            demand += nextVertex.capacity;
            if (demand <= capacity){
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).equals(nextVertex.vertexInfo)) {
                        visited.add(nextVertex);
                        open.remove(i);
                    }
                } 
                temp = nextVertex;
            }else{
                demand -= nextVertex.capacity;
                break;
            }
        }
        System.out.println(demand);
        visited.add(deliveryGraph.head);
        return visited;
    }

    public Vertex getNextVertextoVisit(Vertex current, ArrayList<T> adjacents) {
        Vertex goalVertex = deliveryGraph.getTheVertex(adjacents.get(0));
        int currentdemand = deliveryGraph.getTheVertex(adjacents.get(0)).capacity;
        for (int i = 0; i < adjacents.size(); i++) {
            int nextDemand = deliveryGraph.getTheVertex(adjacents.get(i)).capacity;
            if (nextDemand < currentdemand) {
                currentdemand = nextDemand;
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
