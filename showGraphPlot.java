package alwaysontime2;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author User
 */
public class showGraphPlot extends JFrame {

    private ArrayList<Vertex<Customer, Double>> vertices = new ArrayList<>();
    private ArrayList<Customer> totalRouteList = new ArrayList<>();
    private String name;
    private double cost, time;

    /**
     * Creates new form showGraphPlot
     */
    public showGraphPlot(ArrayList<Vertex<Customer, Double>> vertices, ArrayList<Customer> totalRouteList,String name, Double cost, double time) {
        this.vertices = vertices;
        this.totalRouteList = totalRouteList;
        this.name = name;
        this.cost = cost;
        this.time = time;
        this.setTitle("Coordinates System");
        this.setSize(960, 960);
        this.setVisible(true);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
    }

    public showGraphPlot() {
        initComponents();
    }

    public void paint(Graphics g) {
        super.paint(g);
        int index = 0;
        Color[] arr = {Color.BLACK, Color.GREEN, Color.PINK, Color.ORANGE, Color.WHITE,Color.MAGENTA,Color.CYAN,Color.LIGHT_GRAY};
        g.setColor(arr[index]);
        for (int i = 0; i < totalRouteList.size() - 1; i++) {
            if (!totalRouteList.get(i).equals(totalRouteList.get(i + 1))) {
                int startingXPoint = totalRouteList.get(i).getX() * 3;
                int startingYPoint = totalRouteList.get(i).getY() * 4;
                int endingXPoint = totalRouteList.get(i + 1).getX() * 3;
                int endingYPoint = totalRouteList.get(i + 1).getY() * 4;
                g.drawLine(startingXPoint, startingYPoint, endingXPoint, endingYPoint);
            } else {
                index++;
                g.setColor(arr[index % 7]);
            }
        }
        Font f = new Font("Comic Sans MS", Font.BOLD, 10);
        g.setFont(f);
        for (int i = 0; i < vertices.size(); i++) {
            int startingXPoint = vertices.get(i).vertexInfo.getX() * 3;
            int startingYPoint = vertices.get(i).vertexInfo.getY() * 4;
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillOval(startingXPoint - 5, startingYPoint, 10, 10);
                g.drawString("DEPOT", startingXPoint-20, startingYPoint + 20);
            } else {
                g.setColor(Color.BLUE);
                g.fillRect(startingXPoint - 5, startingYPoint, 10, 10);
                g.drawString("CUSTOMER " + i, startingXPoint + 7, startingYPoint + 10);
            }
        }
        g.setColor(Color.GRAY);
        g.drawString(name, 700,480);
        g.drawString("Tour Cost : " + cost, 700, 500);
        int nextSpaceSize = 680;
        int nextLineSize = 520;
        for(int i=0 ; i<totalRouteList.size()-1 ; i++){
            nextSpaceSize += 20;
            if (!totalRouteList.get(i).equals(totalRouteList.get(i + 1))) {
                g.drawString(totalRouteList.get(i).toString() + "->", nextSpaceSize, nextLineSize);
            }
            else{
                g.drawString("0", nextSpaceSize, nextLineSize);
                nextSpaceSize = 680;
                nextLineSize += 20;
            }
        }
        nextSpaceSize += 20;
        g.drawString("0", nextSpaceSize, nextLineSize);
        g.drawString("Time Taken : " + time, 700, nextLineSize+20);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(960, 960));
        setSize(new java.awt.Dimension(960, 960));
        getContentPane().setLayout(null);

        jLabel1.setText("Delivey Route");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(270, 20, 70, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
