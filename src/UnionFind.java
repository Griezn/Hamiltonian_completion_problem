import java.util.HashMap;


public class UnionFind<Vertex> {

    private final HashMap<Vertex, Integer> size = new HashMap<>();
    private final HashMap<Vertex, Vertex> parent = new HashMap<>();


    UnionFind()
    {
    }


    public Vertex getParent(Vertex vertex)
    {
        return parent.get(vertex);
    }


    public void makeSet(Vertex vertex)
    {
        parent.put(vertex, vertex);
        size.put(vertex, 1);
    }


    public Vertex find(Vertex vertex)
    {
        if (parent.get(vertex) != vertex) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }


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
