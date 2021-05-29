
package dsgroupassignment;

public class Vertex<T extends Comparable<T>, N extends Comparable<N>> {
    T vertexInfo;
    int indeg;
    int outdeg;
    int x, y, capacity, ID;
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;
    
    public Vertex(){
        vertexInfo = null;
        indeg = 0;
        outdeg = 0;
        nextVertex = null;
        firstEdge = null;
        ID = 0;
        x=0;
        y=0;
        capacity=0;
    }
    
    public Vertex(T vInfo, int ID, Vertex<T,N> next, int x, int y, int capacity){
        vertexInfo = vInfo;
        this.ID = ID;
        indeg = 0;
        outdeg = 0;
        nextVertex = next;
        firstEdge = null;
        this.x = x;
        this.y = y;
        this.capacity = capacity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
