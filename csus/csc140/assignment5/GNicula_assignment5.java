/*
 * Name: Gabriele Nicula
 * Class: CSC140
 * Student ID: 219969192
 * Tested on: MacBook Air M1
 * 			  ecs-pa-coding2.ecs.csus.edu
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class GNicula_assignment5 {

    private class Node implements Comparable<Node> {
        public int node;
        public int cost;
    
        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    // Override the built in compareTo function to be able to pass it our nodes cost.
        @Override
        public int compareTo(Node other) {
            if (cost < other.cost)
                return -1;
            if (cost > other.cost)
                return 1;
            return 0;
        }
    }
    
    private int V;
    private List<List<Node>> adj;
    private int[] dist;

    public GNicula_assignment5(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Node(v, w));
    }

    public void shortestPath(int src) {
        dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<Node>(V);
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().node;

            for (Node neighbor : adj.get(u)) {
                int v = neighbor.node;
                int w = neighbor.cost;

                if (dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
    }

    // Driver code
    public static void main(String args[]) {

    	// It is unclear whether the assignment expects the file in the command line as an argument, or should 
    	// automatically try an input.txt that is hardcoded. 
    	// If it is the former please uncomment the following code.
        // if (args.length < 1) {
        //     System.out.println("Program expects an input file name as the first command line argument.");
        //     System.out.println("Example: %java GNicula_assignment5.java input1.txt");
        //     return;
        // }
    	// String inputFileName = args[0];
        
    	try {
    		// If the file should be an argument passed with the execution command, uncomment the next
    		// line of code and comment the one after it.
    		// Scanner scanner = new Scanner(New File(InputFileName));
        	Scanner scanner = new Scanner(new File("input.txt"));
            PrintWriter outWriter = new PrintWriter(new File("output.txt"));
            int V = scanner.nextInt();
            int E = scanner.nextInt();

            GNicula_assignment5 graph = new GNicula_assignment5(V);

            for (int i = 0; i < E; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                int w = scanner.nextInt();

                graph.addEdge(u, v, w);
            }

            int src = 0;
            graph.shortestPath(src);

            // Now write the output file in the requested format.
            // I am not sure what the delimiter should be, it seems in
            // the testcases it's either double space "  " or triple space "   ".
            // NOTE: I have tested the output difference of my program vs testcases
            // with diff -w and diff -b and it matches; however there are some whitespace
            // differences when 'infinity' is printed. I hope the grading can ignore
            // the whitespace difference.
            outWriter.print("Vertex");
            for (int i = 0; i < V; i++) {
                String prefixWSpace = i == 0 ? "  " : "   ";
                outWriter.print(prefixWSpace + i);
            }
            outWriter.println();
            outWriter.print("Distance");
            for (int i = 0; i < V; i++) {
                int prettyPrintDistance = graph.dist[i];
                String prefixWSpace = i == 0 ? "  " : "   ";
                if (prettyPrintDistance == Integer.MAX_VALUE) {
                    outWriter.print(prefixWSpace + "infinity");
                } else {
                    outWriter.print(prefixWSpace + graph.dist[i] );
                }
            }
            outWriter.println();
            outWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: ");
        }
    }
}
