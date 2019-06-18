package code;

import java.util.ArrayList;

import given.iGraph;

/*
 * A class given to you to handle common operations. 
 * Intentionally left empty for you to fill as you like.
 * You do not have to use this at all!
 */

public abstract class BaseGraph<V> implements iGraph<V>{

/*
 * Fill as you like!
 *   
 */
	ArrayList<AbsVertex> vertices = new ArrayList<AbsVertex>();
	
	public abstract class AbsVertex {
		public V element;
		public Float cost = Float.MAX_VALUE;
		public AbsVertex path;
	}
	
	public void setCost(V v, float cst) {
		AbsVertex vtx = getVertex(v);
		vtx.cost = cst;
	}
	
	public AbsVertex getVertex(V v) {
		for (AbsVertex vtx : vertices) {
			if (vtx.element == v) {
				return vtx;
			}
		}
		return null;
	}
	
	public float cost(V v) {
		AbsVertex vtx = getVertex(v);
		return vtx.cost;
	}
	
	public void setPath(V v, V w) {
		AbsVertex vVtx = getVertex(v);
		vVtx.path = getVertex(w);
	}
	
}
