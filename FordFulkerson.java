
import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/
		// traverse graph starting at source until destination
		int[] colors = new int[graph.getNbNodes()]; // coloring nodes
		ArrayList<Edge> edges = graph.getEdges(); // list of all edges in graph
		boolean foundpath = visit(source,destination,path,edges,colors); // start recursing from the source
		if (!foundpath){
			path.clear(); // nooo path :(
		}
		return path;
	}

	public static boolean visit(Integer source, Integer destination, ArrayList<Integer> path, ArrayList<Edge> edges, int[] colors){
		if (colors[source] == 0) { // if white
			path.add(source); // add to the path
			colors[source] = 1; // 1 is grey
			// 0 is white
			// 2 is black
			if (source.equals(destination)) {
				return true; // if you are already at the end
			}
			for (Edge edge : edges) {
				if (edge.nodes[0] == source && colors[edge.nodes[1]] == 0 && edge.weight>0) { // go through da neighbours
					boolean foundpath = visit(edge.nodes[1], destination, path, edges, colors); // recursive call for DFS
					if (foundpath) { // if you found a path!
						return true;
					}
				}
			}
			path.removeLast(); // otherwiseeee backtrack that shiiii
			colors[source] = 2; // black
		}
		return false; // false :(
	}


	//The method fordfulkerson must compute an integer corresponding to the max flow of
	//the “graph”, as well as the graph encoding the assignment associated with this max flo
	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		int source = graph.getSource();
		int sink = graph.getDestination();
		// we want the OG graph to hold capacity
		// need a residual graph
		// find path then need to augment the path
			// find bottle neck -> smallest residual cap of edges in path
			// add b to flow on each edge in P
		WGraph flow = new WGraph(graph); // keeps track of the current flow
		for (Edge edge : flow.getEdges()) {
			flow.setEdge(edge.nodes[0], edge.nodes[1], 0);
		} // set flow to 0 for all edges
		WGraph residual = new WGraph(graph); // residual graph
		ArrayList<Integer> path = pathDFS(source,sink,residual);
		while(!path.isEmpty()){
			maxFlow = augment(residual, flow, path);
			updateResidual(residual, flow, maxFlow);
			path = pathDFS(source,sink,residual);
		}
		// for every node expcept source and sink,
		// total incoming flow = total outgoing flow
		for (Edge e : flow.getEdges()) {
			if (graph.getEdge(e.nodes[0], e.nodes[1]) != null) {
				graph.setEdge(e.nodes[0], e.nodes[1], e.weight);
			}
		}
		/* YOUR CODE GOES HERE		*/
		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}

	public static void updateResidual (WGraph residual, WGraph flow, int max){
		for (Edge edge : flow.getEdges()) {
			if (residual.getEdge(edge.nodes[0], edge.nodes[1]) != null) { // flow < cap
				residual.setEdge(edge.nodes[0], edge.nodes[1], residual.getEdge(edge.nodes[0], edge.nodes[1]).weight - max);
			}
			if (residual.getEdge(edge.nodes[1], edge.nodes[0]) == null) {
				Edge back = new Edge(edge.nodes[1], edge.nodes[0], max);
				residual.addEdge(back);
			} else {
				residual.setEdge(edge.nodes[1], edge.nodes[0], residual.getEdge(edge.nodes[1], edge.nodes[0]).weight + edge.weight);
			}

		}
	}

	public static int augment(WGraph residual, WGraph flow, ArrayList<Integer> path ){
		int bottle = Integer.MAX_VALUE;
		for(int i = 0 ; i < path.size() - 1 ; i++){
			if(residual.getEdge(path.get(i), path.get(i+1)).weight < bottle){
				bottle = residual.getEdge(path.get(i), path.get(i+1)).weight;
			}
		} // finds the bottleneck

		for (int i = 0 ; i < path.size() - 1 ; i++){
			if(residual.getEdge(path.get(i), path.get(i+1)) != null){
				flow.setEdge(path.get(i), path.get(i+1), flow.getEdge(path.get(i), path.get(i + 1)).weight + bottle);
			}
			else{
				flow.setEdge(path.get(i+1), path.get(i), flow.getEdge(path.get(i+1), path.get(i)).weight - bottle);
			}
		} // augments using the bottleneck

		return bottle;

	}





	 public static void main(String[] args){
//		String file = args[0];
//		File f = new File(file);
//		WGraph g = new WGraph(file);
//	    System.out.println(fordfulkerson(g));
		 WGraph g = new WGraph();
		 g.setSource(0);
		 g.setDestination(5);
		 Edge[] edges = new Edge[] {
				 new Edge(0, 1, 10),
				 new Edge(0, 2, 5),
				 new Edge(2, 4, 5),
				 new Edge(1, 3, 10),
				 new Edge(1, 6, 5),
				 new Edge(3, 0, 10),
				 new Edge(3, 5, 5)
		 };

		 Arrays.stream(edges).forEach(e->g.addEdge(e));
		 ArrayList<Integer> path = FordFulkerson.pathDFS(0, 5, g);
		 System.out.println(path);

		 WGraph f = new WGraph();
		 f.setSource(0);
		 f.setDestination(9);
		 Edge[] edged = new Edge[] {
				 new Edge(0, 1, 10),
				 new Edge(0, 2, 5),
				 new Edge(2, 3, 5),
				 new Edge(1, 3, 10),
				 new Edge(3, 4, 5),
				 new Edge(4, 5, 10),
				 new Edge(4, 6, 5),
				 new Edge(6, 7, 5),
				 new Edge(6, 8, 10),
				 new Edge(8, 9, 10),
		 };
		 Arrays.stream(edged).forEach(e->f.addEdge(e));
		 ArrayList<Integer> path2 = FordFulkerson.pathDFS(0,9,f);
		 System.out.println(path2);

		 WGraph h = new WGraph();
		 h.setSource(0);
		 h.setDestination(9);
		 Edge[] edges2 = new Edge[] {
				 new Edge(0, 1, 10),
				 new Edge(0, 2, 5),
				 new Edge(2, 3, 5),
				 new Edge(1, 3, 10),
				 new Edge(3, 4, 5),
				 new Edge(4, 5, 10),
				 new Edge(4, 6, 5),
				 new Edge(6, 7, 5),
				 new Edge(6, 8, 10),
				 new Edge(8, 9, 10),
		 };
		 Arrays.stream(edges2).forEach(e->h.addEdge(e));
		 String result = FordFulkerson.fordfulkerson(h);
		 System.out.println(result);
	 }
}

