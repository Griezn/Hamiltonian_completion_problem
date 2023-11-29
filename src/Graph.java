import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;


/**
 * A class representing a graph.
 *
 * @param <Vertex> the type of the vertices
 * @author Seppe Degryse
 * @version 1.0
 */
public class Graph<Vertex> implements GraphInterface<Vertex> {

    private final HashMap<Vertex, HashSet<Vertex>> adjacencyList = new HashMap<>();

    private int numberOfEdges = 0;


    /**
     * Adds an edge to the graph.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     */
    public void addEdge(Vertex start, Vertex end)
    {
        if (!adjacencyList.containsKey(start)) {
            adjacencyList.put(start, new HashSet<>());
        }
        if (!adjacencyList.containsKey(end)) {
            adjacencyList.put(end, new HashSet<>());
        }
        adjacencyList.get(start).add(end);
        adjacencyList.get(end).add(start);

        numberOfEdges++;
    }


    public void removeEdge(Vertex start, Vertex end)
    {
        adjacencyList.get(start).remove(end);
        adjacencyList.get(end).remove(start);

        numberOfEdges--;
    }


    /**
     * Returns a collection of integers representing the vertices of the graph.
     */
    @Override
    public Collection<Vertex> getVertices()
    {
        return adjacencyList.keySet();
    }


    /**
     * Returns the number of edges present in the graph.
     */
    @Override
    public int getNumberOfEdges()
    {
        return numberOfEdges;
    }


    /**
     * Returns the number of nodes present in the graph.
     */
    @Override
    public int getNumberOfVertices()
    {
        return adjacencyList.size();
    }


    /**
     * Returns whether node u and node v are neighbours.
     *
     * @param u the first vertex
     * @param v the second vertex
     */
    @Override
    public boolean areNeighbors(Vertex u, Vertex v)
    {
        return adjacencyList.get(u).contains(v);
    }


    /**
     * Returns the degree of node u.
     *
     * @param u the vertex to get the degree of
     */
    @Override
    public int getDegree(Vertex u)
    {
        return adjacencyList.get(u) != null ? adjacencyList.get(u).size() : 0;
    }


    /**
     * Returns a collection of integers representing the neighbours of node u.
     *
     * @param u the vertex to get the neighbours of
     */
    @Override
    public Collection<Vertex> getNeighborsOf(Vertex u)
    {
        return adjacencyList.get(u);
    }


    /**
     * Construct a spanning tree of the graph, which is used as the initial solution for the local search
     * (metaheuristic) algorithm.
     */
    @Override
    @SuppressWarnings("unchecked")
    public TreeInterface<Vertex> getInitialSpanningTree()
    {
        Tree<Vertex> tree = new Tree<>();
        Vertex root = (Vertex) getVertices().toArray()[0];

        Stack<Vertex> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            for (Vertex u : getNeighborsOf(v)) {
                if (tree.getVertices().contains(u)) continue;
                tree.addEdge(v, u);
                stack.push(u);
            }
        }

        tree.setRoot(root);
        return tree;
    }


    /**
     * Apply the local search algorithm. It returns an estimate for the hamiltonian completion number and needs to stop
     * after maxIterations iterations.
     *
     * @param maxIterations the maximum number of iterations
     */
    @Override
    public int applyLocalSearchAlgorithm(int maxIterations)
    {
        Tree<Vertex> tree = (Tree<Vertex>) getInitialSpanningTree();
        int pp = tree.getMinimumPathPartitionNumber();
        int amountOfBadPerms = 0;

        while (amountOfBadPerms < maxIterations) {
            tree.perturb(this);
            int newPP = tree.getMinimumPathPartitionNumber();

            if (newPP < pp) {
                pp = newPP;
            } else {
                amountOfBadPerms++;
            }
            if (pp == 0) {
                return 0;
            }
        }

        return pp;
    }


    /**
     * Apply your chosen metaheuristic. It returns an estimate for the hamiltonian completion number.
     *
     * @param maxIterations the maximum number of iterations
     */
    @Override
    public int applyMetaheuristic(int maxIterations)
    {
        double Tmax = 100;
        double Tmin = 0.1;
        double alpha = 0.99;
        Tree<Vertex> tree = (Tree<Vertex>) getInitialSpanningTree();
        int pp = tree.getMinimumPathPartitionNumber();

        while (Tmax > Tmin) {
            tree.perturb(this);
            int newPP = tree.getMinimumPathPartitionNumber();

            if (newPP < pp) {
                pp = newPP;
            } else {
                double p = Math.exp((pp - newPP) / Tmax);
                if (Math.random() < p) {
                    pp = newPP;
                }
            }

            Tmax *= alpha;
        }

        return pp;
    }
}
