import java.util.*;


/**
 * A class representing a graph.
 *
 * @param <Vertex> the type of the vertices
 * @author Seppe Degryse
 * @version 1.3
 */
public class Graph<Vertex> implements GraphInterface<Vertex> {

    private final HashMap<Vertex, HashSet<Vertex>> adjacencyList = new HashMap<>();

    private final ArrayList<Vertex> vertices = new ArrayList<>();

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
            vertices.add(start);
        }
        if (!adjacencyList.containsKey(end)) {
            adjacencyList.put(end, new HashSet<>());
            vertices.add(end);
        }
        adjacencyList.get(start).add(end);
        adjacencyList.get(end).add(start);

        numberOfEdges++;
    }


    /**
     * Removes an edge from the graph.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     */
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
        return vertices;
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
        return vertices.size();
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
        // random number between 0 and the number of vertices
        int random = (int) (Math.random() * getNumberOfVertices());
        Vertex root = (Vertex) getVertices().toArray()[random];

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
        int smallestPP = Integer.MAX_VALUE;
        int amountOfTrees = 0;

        int num = Math.min(maxIterations, getNumberOfVertices());
        while (amountOfTrees < num) {
            Tree<Vertex> tree = (Tree<Vertex>) getInitialSpanningTree();
            int pp = localSearch(tree);
            if (pp == 1) {
                return 0;
            }
            if (pp < smallestPP) {
                smallestPP = pp;
            }
            amountOfTrees++;
        }

        return smallestPP - 1;
    }


    /**
     * Apply your chosen metaheuristic. It returns an estimate for the hamiltonian completion number.
     *
     * @param maxIterations the maximum number of iterations
     */
    @Override
    public int applyMetaheuristic(int maxIterations)
    {
        int smallestPP = Integer.MAX_VALUE;
        int amountOfTrees = 0;

        int num = Math.min(maxIterations, getNumberOfVertices());
        while (amountOfTrees < num) {
            Tree<Vertex> tree = (Tree<Vertex>) getInitialSpanningTree();
            int pp = metaheuristicSearch(tree);
            if (pp == 1) {
                return 0;
            }
            if (pp < smallestPP) {
                smallestPP = pp;
            }
            amountOfTrees++;
        }

        return smallestPP - 1;
    }


    /**
     * Apply the local search algorithm. It returns an estimate for the minimum path partition number
     *
     * @param tree the tree to apply the local search algorithm on
     * @return the minimum path partition number
     */
    public int localSearch(Tree<Vertex> tree)
    {
        int pp = tree.getMinimumPathPartitionNumber();

        while (true) {
            tree.perturb(this);
            int newPP = tree.getMinimumPathPartitionNumber();

            if (newPP == 1) {
                return 1;
            }
            if (newPP < pp) {
                pp = newPP;
            } else {
                return pp;
            }
        }
    }


    /**
     * Apply the metaheuristic search algorithm. It returns an estimate for the minimum path partition number
     *
     * @param tree the tree to apply the metaheuristic search algorithm on
     * @return the minimum path partition number
     */
    public int metaheuristicSearch(Tree<Vertex> tree)
    {
        double Tmax = 100;
        double Tmin = 0.1;
        double alpha = 0.93;
        int pp = tree.getMinimumPathPartitionNumber();
        int smallestPP = pp;

        while (Tmax > Tmin) {
            tree.perturb(this);
            int newPP = tree.getMinimumPathPartitionNumber();

            if (newPP == 1) {
                return 1;
            }
            if (newPP < pp) {
                pp = newPP;
                if (pp < smallestPP) {
                    smallestPP = pp;
                }
            } else {
                double p = Math.exp((pp - newPP) / Tmax);
                if (Math.random() < p) {
                    pp = newPP;
                    Tmax *= alpha;
                } else {
                    return smallestPP;
                }
            }
        }

        return smallestPP;
    }


    /**
     * Evaluating a tree means calculating the density, connectivity and isolated vertices.
     *
     * @param tree the tree to evaluate
     * @return the evaluation of the tree
     * @see Graph#metaheuristicSearch(Tree)
     */
    @SuppressWarnings("unused")
    public float evaluate(Tree<Vertex> tree)
    {
        float connectivity = (float) tree.getMinimumPathPartitionNumber();
        float density = (float) getNumberOfEdges() / tree.getNumberOfEdges();
        float isolated = (float) tree.getNumberOfIsolated() / tree.getNumberOfVertices();

        return density + connectivity - isolated;
    }


    public void shuffleVertices()
    {
        Collections.shuffle((List<?>) getVertices());
    }
}
