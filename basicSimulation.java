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
public class basicSimulation {
    private Graph<Customer,Double> map;
    private int capacity;
    private ArrayList<Customer> allRoute = new ArrayList<>();
    private double tourCost = 0;
    private long time = 0, startTime = 0;
    
    public basicSimulation(Graph<Customer, Double> map, int capacity) {
        this.map = map;
        this.capacity = capacity;
    }

    public double getTourCost() {
        return tourCost;
    }

    public double getTime() {
        return (double)(time/ Math.pow(10, 9));
    }

    public long getStartTime() {
        return startTime;
    }
    
    
    
    public void dfs(Graph<Customer,Double> map,ArrayList<ArrayList<Vehicle>> allTour,Customer currentVertex, ArrayList<Vehicle> initial, Vehicle vehicle, int capacity, ArrayList<Vehicle> lastRoute){
        if(time >= 60e9){
            return ;
        }else{
            ArrayList<Customer> possible = new ArrayList();
            ArrayList<Customer> neighbours = map.getNeighbours(currentVertex);
            for(int i=0; i<neighbours.size(); i++){
                boolean has = false;
                for (int j = 0; j < initial.size(); j++) {
                    if(initial.get(j).getRoute().contains(neighbours.get(i))){
                        has = true;break;
                    }
                }
                if(!has&&vehicle.checkNumDemand(neighbours.get(i).getDemand())&&!vehicle.getRoute().contains(neighbours.get(i))){
                    possible.add(neighbours.get(i));
                }
            }
            for (int i = 0; i < possible.size(); i++) {
                if(time >= 60e9){
                    break;
                }else{
                    if(vehicle.addNumDemand(possible.get(i).getDemand())){
                        vehicle.addRoute(possible.get(i));
                        dfs(map,allTour,possible.get(i),initial,vehicle,capacity, lastRoute);
                        Vehicle newVehicle = new Vehicle(capacity);
                        for (Customer j : vehicle.getRoute()) {
                            newVehicle.addRoute(j);
                            newVehicle.addNumDemand(j.getDemand());
                        }
                        newVehicle.addRoute(map.head.vertexInfo);
                        lastRoute.remove(lastRoute.size()-1);
                        lastRoute.add(newVehicle);
                        initial.add(newVehicle);
                        ArrayList<Vehicle> newTour = new ArrayList();
                        newTour.addAll(initial);
                        allTour.add(newTour);
                        initial.remove(newVehicle);
                        vehicle.setNumDemand(vehicle.getNumDemand()-vehicle.getRoute().get(vehicle.getRoute().size()-1).getDemand());
                        vehicle.getRoute().remove(vehicle.getRoute().size()-1);
                    }
                }
                long endTime = System.nanoTime();
                time += (endTime - startTime);
                startTime = endTime;
            }
        }
    }

    public void dfsdisconnected(Graph<Customer,Double> map,ArrayList<ArrayList<Vehicle>> allTour,Customer currentVertex, int capacity, ArrayList<Vehicle> lastRoute){
        ArrayList<Vehicle> initial = new ArrayList();
        Vehicle a = new Vehicle(capacity);
        a.addRoute(map.head.vertexInfo);
        a.addNumDemand(currentVertex.getDemand());
        a.addRoute(currentVertex);
        lastRoute.add(a);
        dfs(map,allTour,currentVertex,initial,a,capacity, lastRoute);
        a.addRoute(map.head.vertexInfo);
        initial.add(a);
        allTour.add(initial);
        for (Customer i : map.getAllVertexObjects()) {
            ArrayList<ArrayList<Vehicle>> justTour = (ArrayList<ArrayList<Vehicle>>)allTour.clone();
        T:  for (int j = 0; j < justTour.size(); j++) {
                if(time >= 60e9){
                    return;
                }else{
                    ArrayList<ArrayList<Vehicle>> temp = new ArrayList();
                    initial = (ArrayList<Vehicle>)justTour.get(j).clone();
                    for (int k = 0; k < initial.size(); k++) {
                        if(initial.get(k).getRoute().contains(i)) continue T;
                    }
                    Vehicle b = new Vehicle(capacity);
                    b.addRoute(map.head.vertexInfo);
                    b.addNumDemand(i.getDemand());
                    b.addRoute(i);
                    lastRoute.add(b);
                    dfs(map,temp,i,initial,b,capacity,lastRoute);
                    b.addRoute(map.head.vertexInfo);
                    initial.add(b);
                    temp.add(initial);
                    allTour.remove(justTour.get(j));
                    allTour.addAll(temp);
                }
                lastRoute.remove(lastRoute.size()-1);  
            }
        }
    }

    private void AlternateDFS(Customer currentVertex, Vehicle a, ArrayList<Vehicle> vehicles, int capacity){
        if(currentVertex != null){
            ArrayList<Customer> neighbours = map.getNeighbours(currentVertex);
            for (int i = 0; i < neighbours.size()&& a.getNumDemand()!= capacity; i++) {
                Customer temp = neighbours.get(i);
                boolean has = false;
                for (int j = 0; j < vehicles.size(); j++) {
                    if(vehicles.get(j).getRoute().contains(temp)){
                        has = true;break;
                    }
                }
                if(!has){
                    if(a.addNumDemand(temp.getDemand())){
                        a.addRoute(temp);
                        AlternateDFS(temp,a,vehicles,capacity);
                        break;
                    }
                }
            }

        }
    }
    
    private void AlternateDFSdisconnected(ArrayList<Vehicle> vehicles,int capacity){
        if(!vehicles.get(vehicles.size()-1).getRoute().get(vehicles.get(vehicles.size()-1).getRoute().size()-1).equals(map.head.vertexInfo)){
            Vehicle a = vehicles.get(vehicles.size()-1);
            Vertex<Customer,Double> currentVertex = map.getVertex(a.getRoute().get(a.getRoute().size()-1));
            AlternateDFS(currentVertex.vertexInfo,a,vehicles,capacity);
            a.addRoute(map.head.vertexInfo);
        }
        for (Customer i : map.getAllVertexObjects()) {
            boolean has = false;
            for (int j = 0; j < vehicles.size(); j++) {
                if(vehicles.get(j).getRoute().contains(i)){
                    has = true;break;
                }
            }
            if(!has){
                Vehicle b = new Vehicle(capacity);
                b.addRoute(map.head.vertexInfo);
                vehicles.add(b);
                b.addRoute(i);
                b.addNumDemand(i.getDemand());
                AlternateDFS(i,b,vehicles,capacity);
                b.addRoute(map.head.vertexInfo);
            }
        }
    }
    
    public void printDFS(){
        System.out.println("DFS Simulation");
        System.out.println("Tour");
        ArrayList<Vehicle> BestVehicles = new ArrayList<>();
        ArrayList<ArrayList<Vehicle>> allTour = new ArrayList();
        Vertex<Customer,Double> temp = map.head.nextVertex;
        tourCost = Double.MAX_VALUE;
        startTime = System.nanoTime();
        while(temp != null){
            ArrayList<Vehicle> lastRoute = new ArrayList();
            dfsdisconnected(map,allTour,temp.vertexInfo, capacity, lastRoute);
            for (ArrayList<Vehicle> i : allTour) {
                double currentCost = 0;
                if(i==null || time >= 60e9){
                    BestVehicles.clear();
                    AlternateDFSdisconnected(lastRoute, capacity);
                    BestVehicles = lastRoute;
                    for(Vehicle j : BestVehicles){
                        currentCost += j.calcRoute();
                    }
                    tourCost = currentCost;
                    break;
                }
                for(Vehicle j : i){
                    currentCost += j.calcRoute();
                }
                if(tourCost > currentCost){
                    BestVehicles.clear();
                    BestVehicles.addAll(i);
                    tourCost = currentCost;
                }
            }
            long endTime = System.nanoTime();
            time += (endTime - startTime);
            startTime = endTime;
            if(time >= 60e9){
                break;
            }   
            temp = temp.nextVertex;
        }
        if(BestVehicles.isEmpty()){
            System.out.println("Couldn't find a solution due to taking too long to complete search");
        }else{
            System.out.println("Tour Cost: "+ tourCost);
            int index = 1;
            for (Vehicle i : BestVehicles) {
                System.out.println("Vehicle " + index);
                System.out.print(i);
                for(Customer j : i.getRoute()){
                    allRoute.add(j);
                }
                index++;
            }
            System.out.printf("Time taken : %.5f\n",(double)(time/ Math.pow(10, 9)));
        }
        System.out.println();
    }
    
    public ArrayList<Customer> getAllRoute(){
        return allRoute;
    }
    
}
