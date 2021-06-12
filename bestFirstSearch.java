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
    private double time;

    public bestFirstSearch(Graph<Customer,Double> deliveryGraph, int capacity) {
        this.deliveryGraph = deliveryGraph;
        this.capacity = capacity;
    }

    public double getCost() {
        return cost;
    }

    public double getTime() {
        return time;
    }

    public void printBestFirstSearch() {
        long startTime = System.nanoTime();
        System.out.println("Best First Search Simulation" + "\nTour");
        open = deliveryGraph.getNeighbours(deliveryGraph.head.vertexInfo);
        while (!open.isEmpty()) {
            getRoute();
        }
        for(int i=0 ; i<vehicles.size() ; i++){
            System.out.println("Vehice " + (i+1));
            System.out.print(vehicles.get(i));
            cost += vehicles.get(i).calcRoute();
        }
        Deliverytime a = new Deliverytime(cost);
        System.out.println("Tour cost " + cost);
        if(!(a.calcT1()>24)){
        System.out.println("Expexted delivery time : " +(int) a.calcT1() +" hours");
        }
        else{
            int day ;
            day = (int)a.calcT1() - 24;
            System.out.println("Expexted delivery time : " +day +" day " +((int) a.calcT1()-24) +" hours");
        }
        
        if(!(a.calcT2()>24)){
       System.out.println("Have to deliver before : " +(int) a.calcT2() +"hours");        }
        else{
            int day ;
            day = (int)a.calcT1() - 24;
            System.out.println("Have to deliver before : " +day +"day " +((int) a.calcT2()-24) +" hours");
        }
        long endTime = System.nanoTime();
        time = (double)(endTime - startTime) / Math.pow(10, 9);
        System.out.printf("Time taken : %.5f\n",time);
        System.out.println();
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
