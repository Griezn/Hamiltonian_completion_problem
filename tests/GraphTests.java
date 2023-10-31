import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTests {
    @Test
    public void testAddEdge() {
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
    public void testTreeAddEdge(){
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

        assertThrows(IllegalArgumentException.class, () -> tree.addEdge(0, 0));
        assertThrows(IllegalArgumentException.class, () -> tree.addEdge(2, 3));
        assertThrows(IllegalArgumentException.class, () -> tree.addEdge(3, 4));
        assertThrows(IllegalArgumentException.class, () -> tree.addEdge(1, 4));
        assertThrows(IllegalArgumentException.class, () -> tree.addEdge(5, 4));
    }

    @Test
    public void testPathCover(){
        Tree<Integer> tree = new Tree<>();
        tree.addEdge(1,2);
        tree.addEdge(1,3);

        tree.addEdge(2,4);
        tree.addEdge(2,5);

        tree.addEdge(3,6);
        tree.addEdge(3,7);

        tree.addEdge(4,8);
        tree.addEdge(4,9);
        tree.addEdge(4,10);
        tree.addEdge(4,11);

        tree.addEdge(5, 12);

        tree.addEdge(7, 13);

        tree.addEdge(9, 14);

        tree.addEdge(11, 15);

        tree.addEdge(13, 16);

        assertEquals(16, tree.getNumberOfVertices());
        assertEquals(15, tree.getNumberOfEdges());

        assertEquals(4, tree.getMinimumPathPartitionNumber());
    }

}
