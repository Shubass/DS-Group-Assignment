package alwaysontime2;

class Vertex<T extends Comparable<T>, N extends Comparable<N>> {
    T vertexInfo;
    boolean Visited;
    int indeg, outdeg;
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;

    public Vertex() {
        vertexInfo = null;
        nextVertex = null;
        firstEdge = null;
        indeg = 0;
        outdeg = 0;       
    }

    public Vertex(T vertexInfo, Vertex<T, N> nextVertex) {
        this.vertexInfo = vertexInfo;
        this.nextVertex = nextVertex;
        firstEdge = null;
        indeg = 0;
        outdeg = 0;       
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setVisited(boolean isVisited) {
        this.Visited = isVisited;
    }

    @Override
    public String toString() {
        return vertexInfo+"";
    }

    
}