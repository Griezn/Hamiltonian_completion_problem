public class Tree<Vertex> extends Graph<Vertex> implements TreeInterface<Vertex> {

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
        return 0;
    }
}
