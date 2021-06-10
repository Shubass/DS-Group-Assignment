package alwaysontime2;

import java.util.ArrayList;

public class Vehicle {
    private int capacity = 0, numDemand = 0;
    private double vehicleCost = 0;
    private ArrayList<Customer> route = new ArrayList();

    public Vehicle(int capacity) {
        this.capacity = capacity;
    }

    public Vehicle(int capacity, int numDemand) {
        this.capacity = capacity;
        this.numDemand = numDemand;
    }
    
    public double calcRoute(){
        vehicleCost = 0;
        for (int i = 0; i < route.size()-1; i++) {
            vehicleCost += route.get(i).calc(route.get(i+1));
        }
        return vehicleCost;
    }
    
    public double getVehicleCost() {
        return calcRoute();
    }

    public ArrayList<Customer> getRoute() {
        return route;
    }

    public int getNumDemand() {
        return numDemand;
    }

    public void setNumDemand(int numDemand) {
        this.numDemand = numDemand;
    }

    public boolean addNumDemand(int demand) {
        if(numDemand+demand <= capacity){
            this.numDemand += demand;
            return true;
        }
        return false;
    }
    
    public boolean checkNumDemand(int demand) {
        return numDemand+demand <= capacity;
    }
    
    public void addRoute(Customer a){
        route.add(a);
    }
    
    public String printRoute(){
        String str = route.get(0).getId()+"";
        for(int i = 1;i < route.size(); i++)
            str += " -> " + route.get(i).getId();
        return str;
    }

    @Override
    public String toString() {
        String str = printRoute() + "\nCapacity : " + numDemand + "\nCost : "+getVehicleCost()+"\n";
        return str;
    }
    
}
