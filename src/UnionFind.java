import java.util.HashMap;


/**
 * A class representing a disjoint set.
 *
 * @param <Vertex> the type of the vertices
 * @author Seppe Degryse
 * @version 1.1
 */
public class UnionFind<Vertex> {

    private final HashMap<Vertex, Integer> size;
    private final HashMap<Vertex, Vertex> parent;


    UnionFind()
    {
        size = new HashMap<>();
        parent = new HashMap<>();
    }


    UnionFind(int capacity)
    {
        size = new HashMap<>(capacity, 1);
        parent = new HashMap<>(capacity, 1);
    }


    /**
     * Getter for the direct parent of a vertex.
     *
     * @param vertex the vertex
     * @return the parent of the vertex
     */
    public Vertex getParent(Vertex vertex)
    {
        return parent.get(vertex);
    }


    /**
     * Adds a vertex to the disjoint set.
     *
     * @param vertex the vertex to add
     */
    public void makeSet(Vertex vertex)
    {
        parent.put(vertex, vertex);
        size.put(vertex, 1);
    }


    /**
     * Finds the parent of the set of a vertex.
     *
     * @param vertex the vertex
     * @return the parent of the set of the vertex
     */
    public Vertex find(Vertex vertex)
    {
        if (parent.get(vertex) != vertex) {
            parent.put(vertex, find(parent.get(vertex)));
            return parent.get(vertex);
        }
        return vertex;
    }


    /**
     * Unites the sets of two vertices.
     *
     * @param v the first vertex
     * @param u the second vertex
     */
    public void union(Vertex v, Vertex u)
    {
        v = find(v);
        u = find(u);

        if (v == u) return;

        if (size.get(v) < size.get(u)) {
            Vertex temp = v;
            v = u;
            u = temp;
        }

        parent.put(u, v);
        size.put(v, size.get(v) + size.get(u));
    }
}
