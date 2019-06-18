package code;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import autograder.Util;
import code.BaseGraph.AbsVertex;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {

	/*
	 * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
	 * 
	 */

	public static boolean usageCheck = false;

	/*
	 * WARNING: MUST USE THIS FUNCTION TO SORT THE 
	 * NEIGHBORS (the adjacent call in the pseudocodes)
	 * FOR DFS AND BFS
	 * 
	 * THIS IS DONE TO MAKE AUTOGRADING EASIER
	 */
	public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
		usageCheck = true;
		List<V> sorted = new ArrayList<>();
		for (V i : inIterable) {
			sorted.add(i);
		}
		Collections.sort(sorted);
		return sorted;
	}

	/*
	 * Runs depth first search on the given graph G and
	 * returns a list of vertices in the visited order, 
	 * starting from the startvertex.
	 * 
	 */
	public List<V> DFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		List<V> A = new ArrayList<V>();
		Stack<V> frontier = new Stack<V>();
		frontier.push(startVertex);
		while (!frontier.isEmpty()) {
			V u = frontier.pop();
			if (!A.contains(u)) {
				A.add(u);
				for(V w : iterableToSortedIterable(G.outgoingNeighbors(u))){
					if (!A.contains(w))
						frontier.push(w);
				}
			}
		}
		return A;
	}

	/*
	 * Runs breadth first search on the given graph G and
	 * returns a list of vertices in the visited order, 
	 * starting from the startvertex.
	 * 
	 */
	public List<V> BFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		List<V> A = new ArrayList<V>();
		ArrayDeque<V> frontier = new ArrayDeque<V>();
		frontier.addLast(startVertex);
		while (!frontier.isEmpty()) {
			V u = frontier.pop();
			if (!A.contains(u)) {
				A.add(u);
				for(V w : iterableToSortedIterable(G.outgoingNeighbors(u))){
					if (!A.contains(w))
						frontier.addLast(w);
				}
			}
		}
		return A;
	}

	/*
	 * Runs Dijkstras single source all-destinations shortest path 
	 * algorithm on the given graph G and returns a map of vertices
	 * and their associated minimum costs, starting from the startvertex.
	 * 
	 */
	private class PQEntry implements Comparable<PQEntry> {
		public V v;
		public float cost;
		
		public PQEntry(V vi, float costi){
			v = vi;
			cost = costi;
		}
		
		@Override
		public int compareTo(PQEntry other){
			return Float.compare(cost, other.cost);
		}
	}
	
	public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		HashMap<V,Float> A = new HashMap<V,Float>();
		PriorityQueue<PQEntry> pq = new PriorityQueue<PQEntry>();
		G.setCost(startVertex, 0);
		pq.add(new PQEntry(startVertex, 0));
		while (!pq.isEmpty()) {
			PQEntry uEntry = pq.remove();
			if (!A.containsKey(uEntry.v)) {
				A.put(uEntry.v, uEntry.cost);
				for (V w : iterableToSortedIterable(G.outgoingNeighbors(uEntry.v))) {
					if (!(A.containsKey(w) && G.cost(w) > G.cost(uEntry.v) + G.getEdgeWeight(uEntry.v, w))) {
						G.setCost(w, uEntry.cost + G.getEdgeWeight(uEntry.v, w));
						pq.add(new PQEntry(w,uEntry.cost + G.getEdgeWeight(uEntry.v, w)));
						G.setPath(w, uEntry.v);
					}
				}
			}
		}
		return A;
	}

	/*
	 *  Returns true if the given graph is cyclic, false otherwise
	 */
	public boolean isCyclic(BaseGraph<V> G) {
		//TODO
		ArrayList<V> marked = new ArrayList<V>();
		for (AbsVertex vtx : G.vertices) {
			if (isCyclicHelper(G, (V)vtx.element, marked)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCyclicHelper(BaseGraph<V> G, V v, ArrayList<V> marked) {
		if (marked.contains(v)) 
			return true;
		else
			marked.add(v);
		for (V u : G.outgoingNeighbors(v)) {
			if (!marked.contains(u))
				isCyclicHelper(G, u, marked);
		}
		return false;
	}

}
