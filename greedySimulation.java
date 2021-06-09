package alwaysontime2;

import java.util.ArrayList;
import java.util.Collections;

public class greedySimulation{

    private Graph<Customer,Double> map;
    private int capacity;
    private ArrayList<Customer> visited = new ArrayList<>();
    private ArrayList<Vertex<Customer,Double>> allRoute = new ArrayList<>();
    private double tourCost = 0;

    public greedySimulation(Graph map, int capacity) {
        this.map = map;
        this.capacity = capacity;
    }

    public Graph getDeliveryGraph() {
        return map;
    }

    public void setDeliveryGraph(Graph deliveryGraph) {
        this.map = deliveryGraph;
    }

    public int getCapacity() {
        return capacity;
    }

    public void Greedy(Vertex<Customer,Double> currentVertex, Graph<Customer,Double> map, Vehicle a){
        if(currentVertex != null){
            Edge<Customer,Double> min = null;
            for (Edge<Customer,Double> i : map.getNeighbours(currentVertex)) {
                if(!visited.contains(i.toVertex.vertexInfo)){
                    if(a.checkNumDemand(i.toVertex.vertexInfo.getDemand())){
                        if(min == null || min.weight > i.weight) min = i;
                    }
                }
            }
            if(min != null && a.addNumDemand(min.toVertex.vertexInfo.getDemand())){
                a.addRoute(min.toVertex.vertexInfo);
                allRoute.add(min.toVertex);
                visited.add(min.toVertex.vertexInfo);
                Greedy(min.toVertex,map,a);
            }
        }
    }
    
    public void Greedydisconnected(Graph<Customer,Double> map, ArrayList<Vehicle> vehicles,int capacity){
        ArrayList<Edge<Customer,Double>> all = map.getNeighbours(map.head);
        for (int i = 0; i < all.size(); i++) { 
            for (int j = i+1; j < all.size(); j++) {
                if(all.get(i).weight > all.get(j).weight){
                    Edge<Customer,Double> temp = all.get(i);
                    all.set(i, all.get(j));
                    all.set(j, temp);
                }
            }
        }
        visited.add(map.head.vertexInfo);
        for (Edge<Customer,Double> i : all) {
            if(!visited.contains(i.toVertex.vertexInfo)){
                Vehicle a = new Vehicle(capacity);
                vehicles.add(a);
                a.addRoute(map.head.vertexInfo);
                allRoute.add(map.head);
                a.addRoute(i.toVertex.vertexInfo);
                allRoute.add(i.toVertex);
                a.addNumDemand(i.toVertex.vertexInfo.getDemand());
                visited.add(i.toVertex.vertexInfo);
                Greedy(i.toVertex,map,a);
                a.addRoute(map.head.vertexInfo);
                allRoute.add(map.head);
            }
        }
    }
    
    public void printGreedy(){
        System.out.println("Greedy Simulation");
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Greedydisconnected(map,vehicles,capacity);
        for(Vehicle i : vehicles){ 
            tourCost += i.calcRoute();
        }
        System.out.println("Tour Cost: "+ tourCost);
        System.out.println(vehicles.size());
        for (Vehicle i : vehicles) {
            System.out.print(i);
        }
    }
    
    public ArrayList<Vertex<Customer,Double>> getAllRoute(){
        return allRoute;
    }

}
