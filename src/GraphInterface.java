import java.util.Collection;

public interface GraphInterface<Vertex> {
    /**
     *  Returns a collection of integers representing the vertices of the graph.
     */
    Collection<Vertex> getVertices();

    /**
     *  Returns the number of edges present in the graph.
     */
    int getNumberOfEdges();

    /**
     *  Returns the number of nodes present in the graph.
     */
    int getNumberOfVertices();

    /**
     *  Returns whether node u and node v are neighbours.
     */
    boolean areNeighbors(Vertex u, Vertex v);

    /**
     *  Returns the degree of node u.
     */
    int getDegree(Vertex u);

    /**
     *  Returns a collection of integers representing the neighbours of node u.
     */
    Collection<Vertex> getNeighborsOf(Vertex u);

    /**
     *  Construct a spanning tree of the graph, which is used as the initial solution for the local search
     *  (metaheuristic) algorithm.
     */
    TreeInterface<Vertex> getInitialSpanningTree();

    /**
     *  Apply the local search algorithm. It returns an estimate for the hamiltonian completion number and needs to stop
     *  after maxIterations iterations.
     */
    int applyLocalSearchAlgorithm(int maxIterations);

    /**
     * Apply your chosen metaheuristic. It returns an estimate for the hamiltonian completion number.
     */
    int applyMetaheuristic(int maxIterations);

}