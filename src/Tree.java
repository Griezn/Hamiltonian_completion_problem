import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Tree<Vertex> implements TreeInterface<Vertex> {
    private final HashMap<Vertex, HashSet<Vertex>> adjacencyList = new HashMap<>();

    /**
     * Adds an edge to the graph.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     * @throws IllegalArgumentException if the edge creates a loop
     */
    public void addEdge(Vertex start, Vertex end) throws IllegalArgumentException {
        if (!adjacencyList.containsKey(start)) {
            adjacencyList.put(start, new HashSet<>());
        }
        if (!adjacencyList.containsKey(end)) {
            adjacencyList.put(end, new HashSet<>());
        }

        if (!canHaveAsEdge(start, end)) {
            throw new IllegalArgumentException("No loops allowed!");
        }

        adjacencyList.get(start).add(end);
        adjacencyList.get(end).add(start);
    }

    public Boolean canHaveAsEdge(Vertex start, Vertex end) {
        if (start == end) {
            return false;
        }
        if (!adjacencyList.containsKey(start)) {
            return true;
        }
        if (!adjacencyList.containsKey(end)) {
            return true;
        }
        return !adjacencyList.get(start).contains(end);
    }

    /**
     * Returns a collection of integers representing the vertices of the graph.
     */
    @Override
    public Collection<Vertex> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Returns the number of edges present in the graph.
     */
    @Override
    public int getNumberOfEdges() {
        int sum = 0;
        for (HashSet<Vertex> edges : adjacencyList.values()) {
            sum += edges.size();
        }
        return sum / 2;
    }

    /**
     * Returns the number of nodes present in the graph.
     */
    @Override
    public int getNumberOfVertices() {
        return adjacencyList.size();
    }

    /**
     * Returns whether node u and node v are neighbours.
     *
     * @param u the first vertex
     * @param v the second vertex
     */
    @Override
    public boolean areNeighbors(Vertex u, Vertex v) {
        return adjacencyList.get(u).contains(v);
    }

    /**
     * Returns the degree of node u.
     *
     * @param u the vertex to get the degree of
     */
    @Override
    public int getDegree(Vertex u) {
        return adjacencyList.get(u).size();
    }

    /**
     * Returns a collection of integers representing the neighbours of node u.
     *
     * @param u the vertex to get the neighbours of
     */
    @Override
    public Collection<Vertex> getNeighborsOf(Vertex u) {
        return adjacencyList.get(u);
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

    /**
     * Returns the minimum path partition number of this tree. Can be computed in linear time!
     */
    @Override
    public int getMinimumPathPartitionNumber() {
        return 0;
    }
}
