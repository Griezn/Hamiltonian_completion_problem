import java.util.ArrayList;
import java.util.Collection;

public class Graph<Vertex extends Integer> implements GraphInterface<Vertex> {

    private int _vertices;
    private int _edges = 0;
    private int[][] adjacencyMatrix;

    public Graph(int vertices) {
        this._vertices = vertices;
        adjacencyMatrix = new int[vertices][vertices];
    }

    /**
     * Adds an edge to the graph.
     * @param start the starting point of the edge
     * @param end the end point of the edge
     */
    public void addEdge(int start, int end) {
        adjacencyMatrix[start][end] += 1;
        this._edges++;
    }

    /**
     * Returns a collection of integers representing the vertices of the graph.
     */
    @Override
    public Collection<Vertex> getVertices() {
        return null;
    }

    /**
     * Returns the number of edges present in the graph.
     */
    @Override
    public int getNumberOfEdges() {
        return _edges;
    }

    /**
     * Returns the number of nodes present in the graph.
     */
    @Override
    public int getNumberOfVertices() {
        return _vertices;
    }

    /**
     * Returns whether node u and node v are neighbours.
     *
     * @param u the first vertex
     * @param v the second vertex
     */
    @Override
    public boolean areNeighbors(Vertex u, Vertex v) {
        return adjacencyMatrix[u][v] >= 1;
    }

    /**
     * Returns the degree of node u.
     *
     * @param u the vertex to get the degree of
     */
    @Override
    public int getDegree(Vertex u) {
        return 0;
    }

    /**
     * Returns a collection of integers representing the neighbours of node u.
     *
     * @param u the vertex to get the neighbours of
     */
    @Override
    public Collection<Vertex> getNeighborsOf(Vertex u) {
        return null;
    }

    /**
     * Construct a spanning tree of the graph, which is used as the initial solution for the local search
     * (metaheuristic) algorithm.
     */
    @Override
    public TreeInterface<Vertex> getInitialSpanningTree() {
        return null;
    }

    /**
     * Apply the local search algorithm. It returns an estimate for the hamiltonian completion number and needs to stop
     * after maxIterations iterations.
     *
     * @param maxIterations the maximum number of iterations
     */
    @Override
    public int applyLocalSearchAlgorithm(int maxIterations) {
        return 0;
    }

    /**
     * Apply your chosen metaheuristic. It returns an estimate for the hamiltonian completion number.
     *
     * @param maxIterations the maximum number of iterations
     */
    @Override
    public int applyMetaheuristic(int maxIterations) {
        return 0;
    }
}
