

public class Main {

    public static void main(String[] args)
    {
        Graph<Integer> graph = (Graph<Integer>) Constructor.createGraphFromFile(args[0]);

        int n;
        if (args.length == 1) {
            n = 30;
        } else {
            n = Integer.parseInt(args[1]);
        }

        long startTime = System.nanoTime();
        int sol = graph.applyMetaheuristic(n);
        long endTime = System.nanoTime();
        System.out.println("Solution: " + sol);
        System.out.println("Time: " + (endTime - startTime) / 1000000 + "ms");
    }
}