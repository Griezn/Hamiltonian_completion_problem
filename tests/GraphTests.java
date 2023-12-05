import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GraphTests {

    @Test
    public void testAddEdge()
    {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(5, 3);
        graph.addEdge(5, 4);

        assertEquals(9, graph.getNumberOfEdges());
        assertEquals(6, graph.getNumberOfVertices());
        assertTrue(graph.areNeighbors(0, 1));
        assertTrue(graph.areNeighbors(0, 2));
        assertTrue(graph.areNeighbors(0, 3));
        assertTrue(graph.areNeighbors(0, 4));
        assertTrue(graph.areNeighbors(1, 4));
        assertTrue(graph.areNeighbors(2, 3));
        assertTrue(graph.areNeighbors(3, 4));
        assertTrue(graph.areNeighbors(5, 3));
        assertTrue(graph.areNeighbors(5, 4));
        assertFalse(graph.areNeighbors(0, 5));
        assertFalse(graph.areNeighbors(1, 2));
        assertFalse(graph.areNeighbors(1, 3));
        assertFalse(graph.areNeighbors(1, 5));
    }


    @Test
    public void testTreeAddEdge()
    {
        Tree<Integer> tree = new Tree<>();
        tree.addEdge(0, 1);
        tree.addEdge(0, 2);
        tree.addEdge(0, 3);
        tree.addEdge(0, 4);
        tree.addEdge(5, 3);
        tree.addEdge(6, 5);

        assertEquals(6, tree.getNumberOfEdges());
        assertEquals(7, tree.getNumberOfVertices());
        assertTrue(tree.areNeighbors(0, 1));
        assertTrue(tree.areNeighbors(0, 2));
        assertTrue(tree.areNeighbors(0, 3));
        assertTrue(tree.areNeighbors(0, 4));
        assertFalse(tree.areNeighbors(1, 4));
        assertFalse(tree.areNeighbors(2, 3));
        assertFalse(tree.areNeighbors(3, 4));
        assertTrue(tree.areNeighbors(5, 3));
        assertFalse(tree.areNeighbors(5, 4));
        assertFalse(tree.areNeighbors(0, 5));
        assertFalse(tree.areNeighbors(1, 2));
        assertFalse(tree.areNeighbors(1, 3));
        assertFalse(tree.areNeighbors(1, 5));

        assertFalse(tree.canHaveAsEdge(0, 0));
        assertFalse(tree.canHaveAsEdge(2, 3));
        assertFalse(tree.canHaveAsEdge(3, 4));
        assertFalse(tree.canHaveAsEdge(1, 4));
        assertFalse(tree.canHaveAsEdge(5, 4));
        assertFalse(tree.canHaveAsEdge(6, 5));
        assertFalse(tree.canHaveAsEdge(0, 1));
    }


    private Tree<Integer> createTestTree1()
    {
        Tree<Integer> tree = new Tree<>();
        tree.addEdge(1, 2);
        tree.addEdge(1, 3);

        tree.addEdge(2, 4);
        tree.addEdge(2, 5);

        tree.addEdge(3, 6);
        tree.addEdge(3, 7);

        tree.addEdge(4, 8);
        tree.addEdge(4, 9);
        tree.addEdge(4, 10);
        tree.addEdge(4, 11);

        tree.addEdge(5, 12);

        tree.addEdge(7, 13);

        tree.addEdge(9, 14);

        tree.addEdge(11, 15);

        tree.addEdge(13, 16);

        tree.setRoot(1);
        assertEquals(16, tree.getNumberOfVertices());
        assertEquals(15, tree.getNumberOfEdges());

        return tree;
    }


    private Graph<Integer> createTestGraph()
    {
        Graph<Integer> graph = new Graph<>();

        // outer circle
        graph.addEdge(1, 2);
        graph.addEdge(1, 6);

        graph.addEdge(2, 3);
        graph.addEdge(2, 8);

        graph.addEdge(3, 4);
        graph.addEdge(3, 10);

        graph.addEdge(4, 5);
        graph.addEdge(4, 12);

        graph.addEdge(5, 1);
        graph.addEdge(5, 14);

        // middle circle
        graph.addEdge(6, 7);

        graph.addEdge(7, 8);
        graph.addEdge(7, 16);

        graph.addEdge(8, 9);

        graph.addEdge(9, 10);
        graph.addEdge(9, 17);

        graph.addEdge(10, 11);

        graph.addEdge(11, 12);
        graph.addEdge(11, 18);

        graph.addEdge(12, 13);

        graph.addEdge(13, 14);
        graph.addEdge(13, 19);

        graph.addEdge(14, 15);

        graph.addEdge(15, 6);
        graph.addEdge(15, 20);

        // inner circle
        graph.addEdge(16, 17);

        graph.addEdge(17, 18);

        graph.addEdge(18, 19);

        graph.addEdge(19, 20);

        graph.addEdge(20, 16);


        assertEquals(20, graph.getNumberOfVertices());
        assertEquals(30, graph.getNumberOfEdges());

        return graph;
    }


    private Graph<Integer> createTestGraph2()
    {
        return (Graph<Integer>) Constructor.createGraphFromFile("./Benchmarks/testingConnected/structured_tree_2_3");
    }


    private Graph<Integer> createTestGraphStarRandomLeaves()
    {
        return (Graph<Integer>) Constructor.createGraphFromFile("./Benchmarks/testingConnected/star_random_leaves_connected_100.in");
    }


    private Graph<Integer> createTestGridGraph(int n)
    {
        Graph<Integer> graph = new Graph<>();

        for (int i = 0; i < n * n; i++) {
            if (i % n != 0) {
                graph.addEdge(i, i - 1);
            }
            if (i >= n) {
                graph.addEdge(i, i - n);
            }
        }

        assertEquals(n*n, graph.getNumberOfVertices());
        assertEquals((n-1)*n*2, graph.getNumberOfEdges());
        return graph;
    }


    @Test
    public void testPathCover()
    {
        Tree<Integer> tree = createTestTree1();
        assertEquals(6, tree.getMinimumPathPartitionNumber());
    }


    @Test
    public void testInitialSpanningTree()
    {
        Graph<Integer> graph = createTestGraph();
        Tree<Integer> tree = (Tree<Integer>) graph.getInitialSpanningTree();
        assertEquals(20, tree.getNumberOfVertices());
        assertEquals(19, tree.getNumberOfEdges());
    }


    @Test
    public void testStructuredTree()
    {
        Graph<Integer> graph = createTestGraph2();
        Tree<Integer> tree = (Tree<Integer>) graph.getInitialSpanningTree();
        assertEquals(13, tree.getNumberOfVertices());
        assertEquals(12, tree.getNumberOfEdges());

        int hcn = graph.applyLocalSearchAlgorithm(20);
    }


    @Test
    public void testStarRandomLeaves15()
    {
        Graph<Integer> graph = createTestGraphStarRandomLeaves();
        Tree<Integer> tree = (Tree<Integer>) graph.getInitialSpanningTree();
        assertEquals(101, tree.getNumberOfVertices());
        assertEquals(100, tree.getNumberOfEdges());

        int hcn = graph.applyLocalSearchAlgorithm(100);
    }


    @Test
    public void testGridGraph()
    {
        int n = 20;
        Graph<Integer> graph = createTestGridGraph(n);
        Tree<Integer> tree = (Tree<Integer>) graph.getInitialSpanningTree();
        assertEquals(n * n, tree.getNumberOfVertices());
        assertEquals(n*n - 1, tree.getNumberOfEdges());

        int hcn = graph.applyLocalSearchAlgorithm(100);
    }
}
