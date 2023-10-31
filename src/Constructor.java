import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Constructor {
    /**
     * Constructs a graph from a file.
     * @param path the path to the file describing the graph
     * @return the constructed graph;
     * @throws RuntimeException if the file is not found
     * @throws RuntimeException if the constructed graph does not match the expected values
     */
    public static GraphInterface<Integer> createGraphFromFile(String path){
        Graph<Integer> graph = new Graph<>();

        int vertices;
        int edges;

        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);

            String line = reader.nextLine();
            int[] data = extractIntegers(line);
            vertices = data[0];
            edges = data[1];

            while (reader.hasNextLine()) {
                line = reader.nextLine();
                data = extractIntegers(line);

                graph.addEdge(data[0], data[1]);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (vertices != graph.getNumberOfVertices() ||
        edges != graph.getNumberOfEdges())
            throw new RuntimeException("Construction failed, number of edges or vertices don't match expected values!");

        return graph;
    }

    /**
     * Extract two integers from a string seperated with one space
     * @param line the line containing the two integers
     * @return a array with two integers
     */
    private static int[] extractIntegers(String line) {
        String[] strings = line.split(" ");
        int[] numbers = new int[2];

        numbers[0] = Integer.parseInt(strings[0]);
        numbers[1] = Integer.parseInt(strings[1]);

        return numbers;
    }
}
