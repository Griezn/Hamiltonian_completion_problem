import java.util.*;


/**
 * A tree is a graph with no cycles.
 * @param <Vertex> the type of the vertices
 * @see Graph
 * @author Seppe Degryse
 * @version 2.4
 */
public class Tree<Vertex> extends Graph<Vertex> implements TreeInterface<Vertex> {

    private Vertex root = null;

    private final UnionFind<Vertex> vertexUnionFind = new UnionFind<>();


    /**
     * Resets the union find of the tree.
     */
    public void resetUnionFind()
    {
        ArrayList<Vertex> vertices = new ArrayList<>(getVertices());
        for (Vertex v : vertices)
            vertexUnionFind.makeSet(v);
    }


    /**
     * Generates the union find of the tree.
     */
    public void generateUnionFind()
    {
        for (Vertex v : getVertices()) {
            for (Vertex w : getNeighborsOf(v)) {
                vertexUnionFind.union(v, w);
            }
        }
    }


    /**
     * Sets the root of the tree if it is a vertex of the tree.
     *
     * @param root the root of the tree
     */
    public void setRoot(Vertex root)
    {
        if (canHaveAsRoot(root)) this.root = root;
    }


    /**
     * Getter for the root.
     *
     * @return the root or the first vertex if no root is se
     */
    @SuppressWarnings("unchecked")
    public Vertex getRoot()
    {
        return root != null ? root : (Vertex) getVertices().toArray()[0];
    }


    /**
     * Checks if a Vertex can be the root of this tree.
     *
     * @param root the vertex to be checked
     * @return true if allowed, false otherwise
     */
    public Boolean canHaveAsRoot(Vertex root)
    {
        return getVertices().contains(root);
    }


    /**
     * Adds an edge to the graph.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     * @throws IllegalArgumentException if the edge creates a loop
     */
    @Override
    public void addEdge(Vertex start, Vertex end) throws IllegalArgumentException
    {
        if (!canHaveAsEdge(start, end))
            throw new IllegalArgumentException("Edge creates a loop!");

        vertexUnionFind.union(start, end);
        super.addEdge(start, end);
    }


    /**
     * Adds an edge to the graph without checking if it creates a loop.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     * @implNote Only use this method if you are sure that the edge does not create a loop!
     * @see Tree#canHaveAsEdge(Object, Object)
     */
    private void checkedAddEdge(Vertex start, Vertex end)
    {
        vertexUnionFind.union(start, end);
        super.addEdge(start, end);
    }


    /**
     * Returns whether the edge can be added to the graph.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     */
    public Boolean canHaveAsEdge(Vertex start, Vertex end)
    {
        if (vertexUnionFind.find(start) == null)
            vertexUnionFind.makeSet(start);

        if (vertexUnionFind.find(end) == null)
            vertexUnionFind.makeSet(end);

        return vertexUnionFind.find(start) != vertexUnionFind.find(end);
    }


    /**
     * Returns the minimum path partition number of this tree. Can be computed in linear time!
     */
    @Override
    public int getMinimumPathPartitionNumber()
    {
        pathCover(getRoot());
        return calculateNumberOfPath();
    }


    /**
     * Creates the path cover of a tree
     *
     * @param v the vertex to start the path cover from, usually root
     * @see Tree#process(Vertex)
     */
    private void pathCover(Vertex v)
    {
        Deque<Vertex> stack = new ArrayDeque<>();
        Deque<Vertex> processingOrder = new ArrayDeque<>();
        Set<Vertex> visited = new HashSet<>();

        stack.push(v);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);

                Collection<Vertex> neighbors = getNeighborsOf(current);
                for (Vertex neighbor : neighbors) {
                    if (!neighbor.equals(current) && !visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
                processingOrder.push(current);
            }
        }

        while (!processingOrder.isEmpty()) {
            Vertex vertex = processingOrder.pop();
            process(vertex);
        }
    }


    /**
     * Removes all neighbours of v except the first two.
     *
     * @param v the vertex to remove the neighbours from
     */
    @SuppressWarnings("unchecked")
    private void process(Vertex v)
    {
        if (getDegree(v) < 3) {
            return;
        }

        Collection<Vertex> copy = new ArrayList<>(getNeighborsOf(v));
        Vertex x, y;
        x = (Vertex) getNeighborsOf(v).toArray()[0];
        y = (Vertex) getNeighborsOf(v).toArray()[1];

        for (Vertex z : copy) {
            if (z == x || z == y) continue;
            removeEdge(v, z);
        }
    }


    /**
     * Calculates the number of paths in the tree, only works correctly after {@link Tree#pathCover(Vertex)}.
     *
     * @return the amount of paths in the tree
     */
    private int calculateNumberOfPath()
    {
        int oneDegree = 0;
        int zeroDegree = 0;

        for (Vertex v : getVertices()) {
            if (getDegree(v) == 1) oneDegree++;
            if (getDegree(v) == 0) zeroDegree++;
        }

        int count = oneDegree % 2 == 0 ? oneDegree / 2 : (oneDegree + 1) / 2;
        return count + zeroDegree;
    }


    /**
     * Applies the move operator to the tree.
     *
     * @param graph the graph to apply the move operator with
     * @see Tree#connectPaths(Graph)
     * @see Tree#rotationMoves(Graph)
     * @see Tree#restoreTree(Graph)
     */
    public void perturb(Graph<Vertex> graph)
    {
        connectPaths(graph);
        //rotationMoves(graph);
        restoreTree(graph);
    }


    /**
     * Connects path partitions of the tree by adding edges from the graph.
     *
     * @param graph the graph to connect the paths with
     * @see Tree#pathCover(Object)
     */
    public void connectPaths(Graph<Vertex> graph)
    {
        resetUnionFind();
        generateUnionFind();

        shuffleVertices();
        for (Vertex v : getVertices()) {
            if (getDegree(v) > 1) continue;

            for (Vertex w : graph.getNeighborsOf(v)) {
                if (getDegree(w) > 1) continue;

                if (canHaveAsEdge(v, w)) {
                    checkedAddEdge(v, w);
                }
                break;
            }
        }
    }


    /**
     * Changes routes on a certain path partition of the tree.
     *
     * @param graph the graph to apply the rotation move operator with
     */
    @SuppressWarnings("unchecked")
    public void rotationMoves(Graph<Vertex> graph)
    {
        for (Vertex v : getVertices()) {
            if (getDegree(v) != 2) continue;

            for (Vertex w : graph.getNeighborsOf(v)) {
                if (getDegree(w) != 1) continue;
                if (canHaveAsEdge(v, w)) continue;

                Vertex x = (Vertex) getNeighborsOf(v).toArray()[0];
                Vertex y = (Vertex) getNeighborsOf(v).toArray()[1];

                if (getDegree(x) != 1){
                    removeEdge(v, x);
                    checkedAddEdge(v, w);
                    break;
                } else if(getDegree(y) != 1){
                    removeEdge(v, y);
                    checkedAddEdge(v, w);
                    break;
                }
            }
        }
    }


    /**
     * Restores the tree to a valid tree by adding edges from the graph until it contains n-1 edges.
     *
     * @param graph the graph to restore the tree from
     */
    public void restoreTree(Graph<Vertex> graph)
    {
        resetUnionFind();
        generateUnionFind();
        int n = getNumberOfVertices() - 1;

        shuffleVertices();
        for (Vertex v : getVertices()) {
            for (Vertex w : graph.getNeighborsOf(v)) {
                if (canHaveAsEdge(v, w)) {
                    checkedAddEdge(v, w);
                }

                if (getNumberOfEdges() >= n) {
                    return;
                }
            }
        }
    }


    /**
     * Returns the number of isolated vertices in the tree.
     *
     * @return the number of isolated vertices
     */
    public int getNumberOfIsolated()
    {
        int count = 0;
        for (Vertex v : getVertices()) {
            if (getDegree(v) == 0) count++;
        }
        return count;
    }
}
