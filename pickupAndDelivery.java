/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class pickupAndDelivery {

    private Graph<Customer, Double> map;
    private int capacity;
    private ArrayList<Vertex<Customer, Double>> allRoute = new ArrayList<>();
    private double tourCost = 0;
    private Scanner sc = new Scanner(System.in);
    private ArrayList<String> pick = new ArrayList();
    private ArrayList<String> dv = new ArrayList();

    public pickupAndDelivery(Graph map, int capacity) {
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

    public void print() {
        System.out.println("Pick&Delivery Simulation");
        String delivery = null;
        System.out.println("\nChecking for pickup and delivery...");
        ArrayList<String> deli = new ArrayList();
        //     System.out.print("Enter the pick up point : ");
        //    int a = sc.nextInt();
        //    System.out.print("\nEnter the delivery point : ");
        //   int b = sc.nextInt();
        //   String sen = a+">"+b;
        //   deli.add(sen);
        deli.add("1>4");
        deli.add("3>2");
        deli.add("4>3");
        deli.add("2>1");
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        int ind = 1;
        int x = 0;
        Simu(map, vehicles, capacity, deli);
        for (Vehicle i : vehicles) {
            tourCost += i.calcRoute();
        }
        System.out.println("");
        for (Vehicle i : vehicles) {
            System.out.println("Pick Up and Delivery " + ind + " : " + deli.get(x));
            System.out.println("Vehicle " + (ind));
            ind++;
            System.out.println("Vehicle Capacity = " + capacity);

            while (x < pick.size()) {
                System.out.println(pick.get(x));
                System.out.println(dv.get(x));
                x++;
                break;
            }

            System.out.println("");
            System.out.print(i);
            System.out.println("");
        }

        System.out.println("Tour Cost: " + tourCost);
        System.out.println("Vehicles : " + vehicles.size());
    }

    public void Simu(Graph<Customer, Double> map, ArrayList<Vehicle> vehicles, int capacity, ArrayList<String> deli) {

        for (int i = 0; i < deli.size(); i++) {
            String[] spl = deli.get(i).split(">");
            int fr = Integer.parseInt(spl[0]);
            int to = Integer.parseInt(spl[1]);

            for (Edge<Customer, Double> x : map.getNeighbours(map.head)) {
                if (x.toVertex.vertexInfo.getId() == fr) {
                    Vehicle a = new Vehicle(capacity);
                    vehicles.add(a);
                    a.addRoute(map.head.vertexInfo);
                    allRoute.add(map.head);
                    a.addRoute(x.toVertex.vertexInfo);
                    allRoute.add(x.toVertex);
                    a.addNumDemand(x.toVertex.vertexInfo.getDemand());
                    pick.add(a.printPick());
                    Extradelivery(x.toVertex, map, a, to);
                    a.addRoute(map.head.vertexInfo);
                    allRoute.add(map.head);

                }

            }
        }

    }

    public ArrayList<Vertex<Customer, Double>> getAllRoute() {
        return allRoute;
    }

    private void Extradelivery(Vertex<Customer, Double> toVertex, Graph<Customer, Double> map, Vehicle a, int to) {

        for (Edge<Customer, Double> x : map.getNeighbours(toVertex)) {
            if (x.toVertex.vertexInfo.getId() == to) {
                a.addRoute(x.toVertex.vertexInfo);
                allRoute.add(x.toVertex);
                dv.add(a.printDeli());

            }

        }
    }

    public void getPick() {
        for (int i = 0; i < pick.size(); i++) {
            System.out.println(pick.get(i));
        }
        for (int i = 0; i < pick.size(); i++) {
            System.out.println(dv.get(i));
        }
    }
}
