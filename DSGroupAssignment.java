package dsgroupassignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DSGroupAssignment {

    public static void main(String[] args) {
        Graph<String, Double> deliveryGraph = new Graph<>();
        int maximumCapacity = 0;
        try {
            Scanner in = new Scanner(new FileInputStream("delivery.txt"));
            int numberOfCustomers = in.nextInt();
            maximumCapacity = in.nextInt();
            int ID = 0;
            int xCoordinateDepot = in.nextInt();
            int yCoordinateDepot = in.nextInt();
            int demandSizeDepot = in.nextInt();
            deliveryGraph.addVertex("depot", ID, xCoordinateDepot, yCoordinateDepot, demandSizeDepot);
            ID += 1;
            while (in.hasNextInt()) {
                int xCoordinate = in.nextInt();
                int yCoordinate = in.nextInt();
                int demandSize = in.nextInt();
                String vertex = "customer" + ID;
                deliveryGraph.addVertex(vertex, ID, xCoordinate, yCoordinate, demandSize);  
                ID++;
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
        }

        int size = deliveryGraph.getAllVertexObjects().size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    double weight = deliveryGraph.calculateEdgeWeight(deliveryGraph.getVertex(i), deliveryGraph.getVertex(j));
                    deliveryGraph.addEdge(deliveryGraph.getVertex(i), deliveryGraph.getVertex(j), weight);
                }
            }
        }
        showGraphPlot show = new showGraphPlot(deliveryGraph);
//        bestFirstSearch bestfs = new bestFirstSearch(deliveryGraph,maximumCapacity);
//        bestfs.getRoute();
    }
}
