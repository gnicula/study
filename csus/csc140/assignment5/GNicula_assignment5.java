import java.util.*;

public class Dijkstra {
    private int dist[];
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    private int V; // Number of vertices
    List<List<Node>> adj;

    public Dijkstra(int V) {
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
    }

    // Function for Dijkstra's Algorithm
    public void dijkstra(List<List<Node>> adj, int src) {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add source node to the priority queue
        pq.add(new Node(src, 0));

        // Distance to the source is always 0
        dist[src] = 0;
        while (settled.size() != V) {

            // Remove the minimum distance node
            // from the priority queue
            int u = pq.remove().node;

            // Add the node to the set of settled nodes
            settled.add(u);

            e_Neighbours(u);
        }
    }

    // Function to process all the neighbours
    // of the passed node
    private void e_Neighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        // All the neighbors of the current node
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            // If current node hasn't already been processed
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;

                // Add the current node to the queue
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }

    // Driver code
    public static void main(String arg[]) {
        int V, E, src;
        Scanner scanner = new Scanner(System.in);

        V = scanner.nextInt();
        E = scanner.nextInt();

        List<List<Node>> adj = new ArrayList<List<Node>>(V);

        // Initialize adjacency list for every node
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        // Adding edges to adjacency list
        for (int i = 0; i < E; i++) {
            int u, v, w;
            u = scanner.nextInt();
            v = scanner.nextInt();
            w = scanner.nextInt();
            adj.get(u).add(new Node(v, w));
        }

        // Source vertex
        src = scanner.nextInt();

        Dijkstra d = new Dijkstra(V);
        d.dijkstra(adj, src);

        // Output shortest distances
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + "\t\t" + d.dist[i]);
    }
}

class Node implements Comparator<Node> {
    public int node;
    public int cost;

    public Node() {}

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }
}
