package code;

import java.util.ArrayList;

import code.BaseGraph.AbsVertex;

public class DirectedWeightedGraph<V> extends BaseGraph<V>  {

  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	private class Vertex extends AbsVertex{
		private V element;
		public float cost = Float.MAX_VALUE;
		private ArrayList<Edge> outgoingEdges = new ArrayList<Edge>();
		private ArrayList<Edge> incomingEdges = new ArrayList<Edge>();
		
		public Vertex(V ele){
			element = ele; 
		}
	}

	private class Edge {
		private float weight;
		private Vertex origin;
		private Vertex destination;
		
		public Edge(Vertex o, Vertex d){
			origin = o;
			destination = d;
		}
		
		public Edge(Vertex o, Vertex d, float w){
			origin = o;
			destination = d;
			weight = w;
		}

	}
	
	public void setCost(V v, float cst) {
		Vertex vtx = getVertex(v);
		vtx.cost = cst;
	}
	
	public boolean contains(V v) {
		for (Vertex vtx : vertices) {
			if (vtx.element == v) {
				return true;
			}
		}
		return false;
	}
	
	public Vertex getVertex(V v) {
		for (Vertex vtx : vertices) {
			if (vtx.element == v) {
				return vtx;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String tmp = "Directed Weighted Graph";
		return tmp;
	}

	@Override
	public void insertVertex(V v) {
		if (!contains(v)) {
			Vertex vtx = new Vertex(v);
			vertices.add(vtx);
		}
	}

	@Override
	public V removeVertex(V v) {
		for (Vertex vtx : vertices) {
			if (vtx.element == v) {
				for (Edge edg : vtx.outgoingEdges) {
					edg.destination.incomingEdges.remove(edg);
				}
				vertices.remove(vtx);
				return v;
			}
		}
		return null;
	}

	@Override
	public boolean areAdjacent(V v1, V v2) {
		if (contains(v1) && contains(v2)) {
			Vertex vtx1 = getVertex(v1);
			Vertex vtx2 = getVertex(v2);
			for (Edge edg : vtx1.outgoingEdges) {
				if (edg.destination == vtx2)
					return true;
			}
		}
		return false;
	}

	@Override
	public void insertEdge(V source, V target) {
		if(!contains(source))
			insertVertex(source);
		if(!contains(target))
			insertVertex(target);
		Vertex vtxSource = getVertex(source);
		Vertex vtxTarget = getVertex(target);
		for (Edge edg : vtxSource.outgoingEdges){
			if (edg.destination == vtxTarget)
				return;
		}
		Edge edg = new Edge(vtxSource, vtxTarget);
		vtxSource.outgoingEdges.add(edg);
		vtxTarget.incomingEdges.add(edg);
	}

	@Override
	public void insertEdge(V source, V target, float weight) {
		if(!contains(source))
			insertVertex(source);
		if(!contains(target))
			insertVertex(target);
		Vertex vtxSource = getVertex(source);
		Vertex vtxTarget = getVertex(target);
		for (Edge edg : vtxSource.outgoingEdges){
			if (edg.destination == vtxTarget && edg.weight == weight)
				return;
			if (edg.destination == vtxTarget && edg.weight != weight) {
				edg.weight=weight;
				return;
			}
		}
		Edge edg = new Edge(vtxSource, vtxTarget, weight);
		vtxSource.outgoingEdges.add(edg);
		vtxTarget.incomingEdges.add(edg);
	}

	@Override
	public boolean removeEdge(V source, V target) {
		if(!contains(source))
			insertVertex(source);
		if(!contains(target))
			insertVertex(target);
		Vertex vtxSource = getVertex(source);
		Vertex vtxTarget = getVertex(target);
		for (Edge edg : vtxSource.outgoingEdges) {
			if (edg.destination == vtxTarget){
				vtxSource.outgoingEdges.remove(edg);
				vtxTarget.incomingEdges.remove(edg);
				return true;
			}
		}
		return false;
	}

	@Override
	public float getEdgeWeight(V source, V target) {
		if(!contains(source) || !contains(target))
			return Float.MAX_VALUE;
		Vertex vtxSource = getVertex(source);
		Vertex vtxTarget = getVertex(target);
		for (Edge edg : vtxSource.outgoingEdges){
			if (edg.destination == vtxTarget)
				return edg.weight;
		}
		return Float.MAX_VALUE;
	}

	@Override
	public int numVertices() {
		return vertices.size();
	}

	@Override
	public Iterable<V> vertices() {
		ArrayList<V> A = new ArrayList<V>();
		for (Vertex vtx : vertices) {
			A.add(vtx.element);
		}
		return A;
	}

	@Override
	public int numEdges() {
		int cnt = 0;
		for (Vertex vtx : vertices) {
			cnt+=vtx.incomingEdges.size();
		}
		return cnt;
	}

	@Override
	public boolean isDirected() {
		return true;
	}

	@Override
	public boolean isWeighted() {
		return true;
	}

	@Override
	public int outDegree(V v) {
		if (v == null || !contains(v)){
			return -1;
		}else{
			Vertex vtx = getVertex(v);
			return vtx.outgoingEdges.size();
		}
	}

	@Override
	public int inDegree(V v) {
		if (v == null || !contains(v)){
			return -1;
		}else{
			Vertex vtx = getVertex(v);
			return vtx.incomingEdges.size();
		}
	}

	@Override
	public Iterable<V> outgoingNeighbors(V v) {
		if (v == null || !contains(v))
			return null;
		ArrayList<V> A = new ArrayList<V>();
		Vertex vtx = getVertex(v);
		for (Edge edg : vtx.outgoingEdges){
			A.add(edg.destination.element);
		}
		return A;
	}

	@Override
	public Iterable<V> incomingNeighbors(V v) {
		if (v == null || !contains(v))
			return null;
		ArrayList<V> A = new ArrayList<V>();
		Vertex vtx = getVertex(v);
		for (Edge edg : vtx.incomingEdges){
			A.add(edg.origin.element);
		}
		return A;
	}
}
