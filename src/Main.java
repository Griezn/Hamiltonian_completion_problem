

public class Main {

    public static void main(String[] args)
    {
        Graph<Integer> graph = (Graph<Integer>) Constructor.createGraphFromFile("./Benchmarks/testingConnected/circle_like_2000_10.in");

        long startTime = System.nanoTime();
        graph.applyLocalSearchAlgorithm(3000);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Time to search: " + duration/1000000 + "ms");
    }
}