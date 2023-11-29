import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Benchmarks {

    private static final String path = "./Benchmarks/testingConnected/";
    private static final String data = "./Benchmarks/global_optima.csv";
    private static final String outputDir = "./Benchmarks/results/";
    private static final ArrayList<String> graphs = new ArrayList<>();
    private static final ArrayList<Integer> optimalSolutions = new ArrayList<>();
    private static File outputFile;


    public static void main(String[] args)
    {
        readData();
        createFile();
        runBenchmarks();
    }


    public static void readData()
    {
        try (Scanner scanner = new Scanner(new File(data))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                graphs.add(values[0]);
                optimalSolutions.add(Integer.parseInt(values[1]));
            }
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
    }


    public static void createFile()
    {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        outputFile = new File(outputDir + timestamp + ".csv");
    }


    public static void runBenchmarks()
    {
        int size = graphs.size();
        for (int i = 0; i < size; i++) {
            Graph<Integer> graph = (Graph<Integer>) Constructor.createGraphFromFile(path + graphs.get(i));
            int optimalSolution = optimalSolutions.get(i);


            long startTime = System.nanoTime();
            int localSolution = graph.applyLocalSearchAlgorithm(50) - 1;
            long endTime = System.nanoTime();
            long localDuration = (endTime - startTime) / 1000000;

            graph = (Graph<Integer>) Constructor.createGraphFromFile(path + graphs.get(i));
            startTime = System.nanoTime();
            int metaSolution = graph.applyMetaheuristic(3000) - 1;
            endTime = System.nanoTime();
            long metaDuration = (endTime - startTime) / 1000000;

            saveToCsv(graphs.get(i), optimalSolution, localSolution, metaSolution, localDuration, metaDuration);
        }
    }


    public static void printResult(String graph, int optimal, int localSolution, int metaSolution, long localTime, long metaTime)
    {
        System.out.println("Graph: " + graph);
        System.out.println("Optimal solution: " + optimal);
        System.out.println("Local search solution: " + localSolution);
        System.out.println("Metaheuristic solution: " + metaSolution);
        System.out.println("Local search time: " + localTime + "ms");
        System.out.println("Metaheuristic time: " + metaTime + "ms");
        System.out.println();
    }


    public static void saveToCsv(String graph, int optimal, int localSolution, int metaSolution, long localTime, long metaTime)
    {
        String line = graph + "," + optimal + "," + localSolution + "," + metaSolution + "," + localTime + "," + metaTime;
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            System.out.println("Error writing to file");
        }
    }
}
