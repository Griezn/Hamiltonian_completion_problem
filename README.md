# Hamiltonian completion problem
This is a Java project that implements an random multistart simulated annealing algorithm to solve the HCP.

## Getting started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
You need to have installed Java 8 or higher.
```bash
java -version
```

### Installing
Clone the repository
```bash
git clone https://github.com/Griezn/Hamiltonian_completion_problem.git
```
or download the zip file.

Navigate to the project folder
```bash
cd Hamiltonian_completion_problem
```

### Running the project
Compile the project
```bash
javac -d bin src/*.java
```

Run the project
```bash
java -cp bin Main
```

Run the project with a custom graph
```bash
java -cp bin Main <path_to_graph>
```


Run the project with a custom graph and a custom number of iterations
```bash
java -cp bin Main <path_to_graph> <number_of_iterations>
```


### Running the benchmark
Compile the project
```bash
javac -d bin src/*.java tests/Benchmarks.java
```

Run the benchmark
```bash
java -cp bin Benchmarks
```

Results will be saved in the results folder.


## Project structure
The project consists of four classes:
* Main.java - the main class that runs the algorithm
* Graph.java - the class that represents the graph
* Tree.java - the class that represents the tree
* UnionFind.java - the class that represents the union-find data structure

## Authors
* Seppe Degryse