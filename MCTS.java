package alwaysontime2;

import java.util.ArrayList;
import java.util.Random;

public class MCTS {
    int level, iteration, ALPHA, N, capacity;
    double policy[][][], globalPolicy[][];
    Graph<Customer,Double> map;
    ArrayList<Customer> allRoute = new ArrayList<>();
    private double tourCost = 0;

    public MCTS(Graph<Customer,Double> deliveryGraph, int capacity) {
       this(deliveryGraph,capacity,3,100);
    }

    public double getTourCost() {
        return tourCost;
    }

    public MCTS(Graph deliveryGraph, int capacity, int level, int iteration) {
        this.map = deliveryGraph;
        this.capacity = capacity;
        this.level = level;
        this.iteration = iteration;
        N = deliveryGraph.size;
        ALPHA = 1;
        policy = new double[level][N][N];
        globalPolicy = new double[N][N];
    }

    public void printMCTS(){
        System.out.println("MCTS Simulation");
        System.out.println("Tour");
        ArrayList<Vehicle> vehicles = SearchMCTS(level-1,iteration);
        for(Vehicle i : vehicles){ 
            tourCost += i.calcRoute();
            allRoute.addAll(i.getRoute());
        }
        System.out.println("Tour Cost: "+ tourCost);
        for(int i=0 ; i<vehicles.size() ; i++){
            System.out.println("Vehicle " + (i+1));
            System.out.print(vehicles.get(i));
        }
    } 
    
    public ArrayList<Vehicle> SearchMCTS(int level, int iteration){
        ArrayList<Vehicle> bestTour = new ArrayList<>();
        double bestTourCost = Double.MAX_VALUE;
        if(level == 0){
            return rollout();
        }
        else{
            policy[level] = globalPolicy;
            for (int i = 0; i < iteration; i++) {
                ArrayList<Vehicle> newTour = SearchMCTS(level-1,iteration);
                double newTourCost = 0;
                for (Vehicle j : newTour) {
                    newTourCost += j.calcRoute();
                }
                if(newTourCost < bestTourCost){
                    bestTour.clear();
                    for(Vehicle k : newTour){
                        bestTour.add(k);
                    }
                    bestTourCost = newTourCost;
                    adapt(bestTour, level);
                }
            for(Vertex<Customer,Double> j : map.getAllVertex()){
                j.setVisited(false);
            }
//                if(processing_time exceed time limit) return bestTour;
            }
            globalPolicy = policy[level];
        }
        return bestTour;
    }
    
    public void adapt(ArrayList<Vehicle> tour, int level){
        ArrayList<Vertex<Customer,Double>> allStops = map.getAllVertex();
        for (int i = 0; i < tour.size(); i++) {
            ArrayList<Customer> stops = tour.get(i).getRoute();
            for (int j = 0; j < stops.size(); j++){
                Customer stop = stops.get(j);
                Customer nextStop = stops.get(0);
                if(j != stops.size()-1)
                    nextStop = stops.get(j+1);
                policy[level][stop.getId()][nextStop.getId()] += ALPHA;
                double z = 0;
                for(Vertex<Customer,Double> k : allStops){ 
                    if(k.equals(stop)) continue;
                    if(!k.isVisited()) z += Math.exp(globalPolicy[stop.getId()][k.vertexInfo.getId()]);
                }
                for(Vertex<Customer,Double> k : allStops){
                    if(k.equals(stop)) continue;
                    if(!k.isVisited()) policy[level][stop.getId()][k.vertexInfo.getId()] -= ALPHA * (Math.exp(globalPolicy[stop.getId()][k.vertexInfo.getId()])/z);
                }
                map.getVertex(stop).setVisited(true);
            }
        }
    }

    public ArrayList<Vehicle> rollout(){
        ArrayList<Vehicle> newTour = new ArrayList<>();
        Vehicle start = new Vehicle(capacity);
        start.addRoute(map.head.vertexInfo);
        newTour.add(start);
        ArrayList<Vertex<Customer,Double>> allStops = map.getAllVertex();
        ArrayList<Boolean> checked = new ArrayList<>();
        for (int i = 0; i < allStops.size(); i++) {
            checked.add(false);
        }
        while(true){
            Customer currentStop = newTour.get(newTour.size()-1).getRoute().get(newTour.get(newTour.size()-1).getRoute().size()-1);
            ArrayList<Customer> possible_successors = new ArrayList<>();
            for (int i = 0; i < allStops.size(); i++) {
                if(allStops.get(i).vertexInfo.equals(currentStop))continue;
                if(!allStops.get(i).isVisited()&&!checked.get(i))possible_successors.add(allStops.get(i).vertexInfo);
            }
            if(possible_successors.isEmpty()){
                if(!currentStop.equals(map.head.vertexInfo)) newTour.get(newTour.size()-1).addRoute(map.head.vertexInfo);
                boolean allVisited = true;
                for(Vertex i : allStops){
                    if(!i.isVisited()) allVisited = false;
                }
                if(allVisited) break;
                Vehicle startAgain = new Vehicle(capacity);
                startAgain.addRoute(map.head.vertexInfo);
                newTour.add(startAgain);
                for(int i = 0; i < checked.size();i++)
                    checked.set(i,false);
                continue;
            }
            Customer nextStop = select_next_move(currentStop, possible_successors);
            if(newTour.get(newTour.size()-1).addNumDemand(nextStop.getDemand())){
                newTour.get(newTour.size()-1).addRoute(nextStop);
                map.getVertex(nextStop).setVisited(true);
            }
            else{
                checked.set(allStops.indexOf(map.getVertex(nextStop)),true);
            }
        }
        return newTour;
    }
    
    public Customer select_next_move(Customer currentStop, ArrayList<Customer> possible_successors){
        double[] probability = new double[possible_successors.size()];
        double sum = 0;
        for (int i = 0; i < possible_successors.size(); i++) {
            probability[i] = Math.exp(globalPolicy[currentStop.getId()][possible_successors.get(i).getId()]);
            sum += probability[i];
        }
        double mrand = new Random().nextDouble() * sum;
        int i = 0;
        sum = probability[0];
        while(sum < mrand){
            sum += probability[++i];
        }
        return possible_successors.get(i);
    } 
    
    public ArrayList<Customer> getAllRoute(){
        return allRoute;
    }
    
}
