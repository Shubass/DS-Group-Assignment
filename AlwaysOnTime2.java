package alwaysontime2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AlwaysOnTime2 {

    public static void main(String[] args) {
        Graph<Customer, Double> map = new Graph<>();
        int Capacity = 0;
        int numberOfCustomers = 0;
        try {
            Scanner in = new Scanner(new FileInputStream("delivery4.txt"));
            numberOfCustomers = in.nextInt();
            Capacity = in.nextInt();
            int ID = 0;
            while (in.hasNextInt()) {
                int x = in.nextInt();
                int y = in.nextInt();
                int demand = in.nextInt();
                map.addVertex(new Customer(x, y, demand, ID));
                ID++;
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
        }
        ArrayList<Customer> customerList = map.getAllVertexObjects();
        for (int i = 0; i < numberOfCustomers; i++) {
            for (int j = 0; j < numberOfCustomers; j++) {
                if (i != j) {
                    Double weight = customerList.get(i).calc(customerList.get(j));
                    map.addEdge(customerList.get(i), customerList.get(j),weight);
                }
            } 
        }
        HomePage home = new HomePage(map, Capacity);
        home.setVisible(true);
        
    }
}
