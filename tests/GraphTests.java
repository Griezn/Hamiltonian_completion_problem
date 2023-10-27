import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTests {
    @Test
    public void testAddEdge() {
        Graph<Integer> graph = new Graph<Integer>();
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

}
