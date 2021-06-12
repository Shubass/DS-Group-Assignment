
package alwaysontime2;


public class Deliverytime {

//average speed in Malaysian roads
private final double velocity = 60;
private double cost;
private double t1,t2;


    public Deliverytime() {
    }

    public Deliverytime(double cost) {
        this.cost = cost;
    }
       

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public double getT1() {
        return t1;
    }

    public double getT2() {
        return t2;
    }

    public double calcT1(){
        t1 = cost/velocity;
        
        return t1;
    }
    
    public double calcT2(){
        
        t2 = t1+(0.5*t1);
        

        return t2;
}
    
}