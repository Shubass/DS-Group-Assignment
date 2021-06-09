package alwaysontime2;

public class Customer implements Comparable<Customer>{
    private int demand = 0, id;
    private int x = 0,y = 0;

    public Customer(int x, int y, int demand, int id) {
        this.demand = demand;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getDemand() {
        return demand;
    }

    public double calc(Customer b){
        return Math.sqrt((b.x-this.x)*(b.x-this.x) + (b.y-this.y)*(b.y-this.y));
    }

    @Override
    public String toString() {
        return id +"";
    }

    @Override
    public int compareTo(Customer o) {
        if(o.x == this.x && o.y==this.y)return 0;
        return -1;
    }

    
    
}
