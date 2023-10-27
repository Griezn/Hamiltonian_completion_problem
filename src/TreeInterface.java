public interface TreeInterface<Vertex> extends GraphInterface<Vertex> {
    /**
     * Returns the minimum path partition number of this tree. Can be computed in linear time!
     */
    int getMinimumPathPartitionNumber();
}