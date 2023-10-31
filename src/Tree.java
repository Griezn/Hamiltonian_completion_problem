import java.util.HashSet;

public class Tree<Vertex> extends Graph<Vertex> implements TreeInterface<Vertex> {
    private Vertex root = null;

    /**
     * Sets the root of the tree if it is a vertex of the tree.
     * @param root the root of the tree
     */
    public void setRoot(Vertex root) {
        if (canHaveAsRoot(root)) this.root = root;
    }

    /**
     * Getter for the root.
     * @return the root or the first vertex if no root is se
     */
    public Vertex getRoot() {
        return root != null ? root : (Vertex) getVertices().toArray()[0];
    }

    /**
     * Checks if a Vertex can be the root of this tree.
     * @param root the vertex to be checked
     * @return true if allowed, false otherwise
     */
    public Boolean canHaveAsRoot(Vertex root) {
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
    public void addEdge(Vertex start, Vertex end) throws IllegalArgumentException {
        if (!canHaveAsEdge(start, end)) {
            throw new IllegalArgumentException("No loops allowed!");
        }

        super.addEdge(start, end);
    }

    /**
     * Returns whether the edge can be added to the graph.
     *
     * @param start the starting point of the edge
     * @param end   the end point of the edge
     */
    public Boolean canHaveAsEdge(Vertex start, Vertex end) {
        if (start == end) {
            return false;
        }
        if (!getVertices().contains(start)) {
            return true;
        }
        if (getVertices().contains(end)) {
            return false;
        }
        return !getNeighborsOf(start).contains(end);
    }

    /**
     * Returns the minimum path partition number of this tree. Can be computed in linear time!
     */
    @Override
    public int getMinimumPathPartitionNumber() {
        pathCover(getRoot(), null);
        return calculateNumberOfPath()-1;
    }

    /**
     * Reduces the tree to a tree with only vertices of degree two
     */
    private void pathCover(Vertex v, Vertex x) {
        for (Vertex z : getNeighborsOf(v)) {
            if (z == x) continue;
            pathCover(z, v);
        }
        process(v);
    }


    /**
     * Removes all neighbours of v except the first two.
     * @param v the vertex to remove the neighbours from
     */
    private void process(Vertex v) {
        if (getDegree(v) < 3) {
           return;
        }


        HashSet<Vertex> copy = new HashSet<>(getNeighborsOf(v));
        Vertex x, y;
        x = (Vertex) getNeighborsOf(v).toArray()[0];
        y = (Vertex) getNeighborsOf(v).toArray()[1];

        for (Vertex z : copy) {
            if (z == x || z == y) continue;

            getNeighborsOf(z).remove(v);
            getNeighborsOf(v).remove(z);
        }
    }


    /**
     * Calculates the number of paths in the tree
     * @return the amount of paths in the tree
     * @apiNote only works correctly after {@link Tree#pathCover(Vertex, Vertex)}
     */
    private int calculateNumberOfPath(){
        int count = 0;

        for (Vertex v : getVertices()) {
            if (getDegree(v) == 1) count++;
        }

        return count % 2 == 0 ? count/2 : (count+1)/2 ;
    }
}
