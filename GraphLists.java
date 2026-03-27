// Simple weighted graph representation - adjacency lists representation and implementation of DF & BF
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*; // java.io means a package which contains classes for input and output

class GraphLists {
    class Node {
        public int vert; // destination vertex
        public int wgt; // edge weight
        public Node next; // pointer to next node in the adjacency list
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    
    // used for traversing graph
    private int[] visited;
    private int id;
    
    // default constructor
    public GraphLists(String graphFile) throws IOException
    {
        int u, v;
        int e, wgt;
        Node t;

        FileReader fr = new FileReader(graphFile); // opening the file for reading
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +"; // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        // reading and converting the vertices and edges from the file
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z
        visited = new int[V+1]; // initialising the visited array
        adj = new Node[V+1]; // initialising the adjacency list array
        for(v = 1; v <= V; ++v) // initialising a for loop to read the vertices from the file
            adj[v] = z;
        
        // read the edges from the text file
        System.out.println("Reading edges from text file");
        // added for loop to read the edges from the file and create the adjacency lists
        for(e = 1; e <= E; ++e) {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]);
            wgt = Integer.parseInt(parts[2]);

            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));

            t = new Node(); t.vert = v; t.wgt = wgt;
            t.next = adj[u]; adj[u] = t;

            t = new Node(); t.vert = u; t.wgt = wgt;
            t.next = adj[v]; adj[v] = t;
        }
        reader.close(); // closing file after reading to free up the resources memory
    }
   
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");    
        }
        System.out.println("");
    }

    // method to initialise Depth First Traversal of Graph
    public void DF(int s) {
        id = 0; // means rechecking and reinitialising the visited array
        // visited = new int[V+1]; // already declared, just ensure it's reset

        for(int v = 1; v <= V; v++) // initisialising a for loop to reset the visited array
            visited[v] = 0;

        System.out.println("\nDepth First Traversal");
        System.out.println("Starting at vertex " + toChar(s));

        dfVisit(0, s, 0); // calling the recursive method
    }

    // Recursive Depth First Traversal for adjacency matrix
    private void dfVisit(int prev, int v, int wgt) {
        Node t; // means declaring and initialising a node
        visited[v] = ++id; // setting visited array assigning vertex to the next id

        // printing visited vertex and specific edge together with the id
        System.out.println("DF visited " + toChar(v) + " along edge " + toChar(prev) + "--(" + wgt + ")--" + toChar(v) + " (id=" + id + ")");

        for(t = adj[v]; t != z; t = t.next) { // initialising a for loop to traverse the adjacency list of vertex v
            if(visited[t.vert] == 0) // means program will only visit the vertex if it has not been visited before
                dfVisit(v, t.vert, t.wgt);
        }
    }

    // method to initialise Breadth First Traversal of Graph
    public void BF(int s) {
        int v, u;
        int[] queue = new int[V+1]; // initialising an array as a queue to store vertices to be visited
        int[] dist = new int[V+1]; // initialising an array to store the distance of each vertex from starting one
        int front = 0, rear = 0; // initialising front and rear pointers for the queue
        Node t; // means declaring and initialising a node

        id = 0; // means rechecking and reinitialising the visited array
        for(v = 1; v <= V; v++) { // initisialising a for loop to reset the visited and dist arrays
            visited[v] = 0;
            dist[v] = -1;
        }

        System.out.println("\nBreadth First Traversal");
        System.out.println("Starting at vertex " + toChar(s));

        visited[s] = ++id; // setting visited array assigning starting vertex to the next id
        dist[s] = 0; // setting distance of starting vertex to 0
        queue[rear++] = s; // adding starting vertex to the queue and incrementing the rear pointer

        while(front < rear) { // initialising a while loop to set and manage vertices
            v = queue[front++]; // means removing the front vertex and incrementing the front pointer
            for(t = adj[v]; t != z; t = t.next) { // initialising a for loop to go through adjacency list of vertex v
                u = t.vert;
                if(visited[u] == 0) { // means program will only visit the vertex if it has not been visited before
                    visited[u] = ++id; // setting visited array assigning vertex to the next id
                    dist[u] = dist[v] + 1;
                    System.out.println("BF visited " + toChar(u) + " along edge " + toChar(v) + "--(" + t.wgt + ")--" + toChar(u) + " (dist=" + dist[u] + ")");
                    queue[rear++] = u; // adding vertex u to the queue and incrementing the rear pointer
                }
            }
        }
    }

    // main function
    public static void main(String[] args) throws IOException
    {
        int s = 4;
        String fname = "wGraph3.txt";               

        GraphLists g = new GraphLists(fname);
       
        g.display(); // calling display method to show the graph representation
        
        g.DF(s); // calling Depth First Traversal method with starting vertex s

        g.BF(s); // calling Breadth First Traversal method with starting vertex s
    }
}