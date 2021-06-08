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
public class AStarSearch<T extends Comparable<T>> { //f(n) = g(n) + h(n) where h(n) is the deman and g(n) will be the cost

    private Graph deliveryGraph;
    private final int capacity;
    private ArrayList<Vertex> visited = new ArrayList<>();
    private ArrayList<T> open = new ArrayList<>();
    private double cost = 0;
    private int demand;

    public AStarSearch(Graph deliveryGraph, int capacity) {
        this.deliveryGraph = deliveryGraph;
        this.capacity = capacity;
    }

    public void getTotalRoute() {
        System.out.println("A*Search Simulation" + "\nTour");
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
        visited.clear();
        demand = 0;
        Vertex temp = deliveryGraph.head;
        visited.add(temp);
        while (!open.isEmpty() && demand <= capacity) {
            Vertex nextVertex = getNextVertextoVisit(temp, open);
            demand += nextVertex.capacity;
            if (demand <= capacity) {
                for (int i = 0; i < open.size(); i++) {
                    if (open.get(i).equals(nextVertex.vertexInfo)) {
                        visited.add(nextVertex);
                        open.remove(i);
                    }
                }
                temp = nextVertex;
            } else {
                demand -= nextVertex.capacity;
                break;
            }
        }
        System.out.println("Capacity : " + demand);
        visited.add(deliveryGraph.head);
        return visited;
    }

    public Vertex getNextVertextoVisit(Vertex current, ArrayList<T> adjacents) {
        Vertex goalVertex = deliveryGraph.getTheVertex(adjacents.get(0));
        double currentCost = deliveryGraph.getTheVertex(adjacents.get(0)).capacity + deliveryGraph.calculateEdgeWeight(current.vertexInfo, goalVertex.vertexInfo);
        for (int i = 0; i < adjacents.size(); i++) {
            double nextCost = deliveryGraph.getTheVertex(adjacents.get(i)).capacity + deliveryGraph.calculateEdgeWeight(current.vertexInfo, adjacents.get(i));
            if (nextCost < currentCost) {
                currentCost = nextCost;
                goalVertex = deliveryGraph.getTheVertex(adjacents.get(i));
            }
        }
        return goalVertex;
    }

    public double getCost(ArrayList<Vertex> route) {
        double totalCost = 0;
        for (int i = 0; i < route.size()-1; i++) {
                totalCost += deliveryGraph.calculateEdgeWeight(route.get(i).vertexInfo, route.get(i+1).vertexInfo);
        }
        return totalCost;
    }
}
