package alwaysontime2;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class greedySimulation{

    private Graph<Customer,Double> map;
    private int capacity;
    private ArrayList<Customer> visited = new ArrayList<>();
    private ArrayList<Customer> allRoute = new ArrayList<>();
    private double tourCost = 0;
    private double time;

    public greedySimulation(Graph map, int capacity) {
        this.map = map;
        this.capacity = capacity;
    }

    public Graph getDeliveryGraph() {
        return map;
    }

    public double getTourCost() {
        return tourCost;
    }

    public double getTime() {
        return time;
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
                allRoute.add(min.toVertex.vertexInfo);
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
                allRoute.add(map.head.vertexInfo);
                a.addRoute(i.toVertex.vertexInfo);
                allRoute.add(i.toVertex.vertexInfo);
                a.addNumDemand(i.toVertex.vertexInfo.getDemand());
                visited.add(i.toVertex.vertexInfo);
                Greedy(i.toVertex,map,a);
                a.addRoute(map.head.vertexInfo);
                allRoute.add(map.head.vertexInfo);
            }
        }
    }
    
    public void printGreedy(){
        long startTime = System.nanoTime();
        System.out.println("Greedy Simulation");
        System.out.println("Tour");
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Greedydisconnected(map,vehicles,capacity);
        Deliverytime a = new Deliverytime(tourCost); 
           
            
        for(Vehicle i : vehicles){ 
            tourCost += i.calcRoute();
        }
        System.out.println("Tour Cost: "+ tourCost);
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
        for(int i=0 ; i<vehicles.size() ; i++){
            System.out.println("Vehicle " + (i+1));
            System.out.print(vehicles.get(i));
        }
        long endTime = System.nanoTime();
        time = (endTime - startTime) / Math.pow(10, 9);
        System.out.printf("Time taken : %.5f\n",time);
        System.out.println();
    }
    
    public ArrayList<Customer> getAllRoute(){
        return allRoute;
    }

}