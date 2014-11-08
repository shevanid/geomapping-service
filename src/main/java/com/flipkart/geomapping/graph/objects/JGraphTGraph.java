package com.flipkart.geomapping.graph.objects;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class JGraphTGraph<V,E> {

	DirectedGraph<BaseVertex<V>, BaseEdge<E>> directedGraph;

	@SuppressWarnings("unchecked")
	public JGraphTGraph() {
		BaseEdge<E> e = new BaseEdge<E>();
		Class<? extends BaseEdge<E>> edgeClass = (Class<? extends BaseEdge<E>>) e.getClass();
		directedGraph = new DefaultDirectedGraph<BaseVertex<V>, BaseEdge<E>>(edgeClass);
	}

	public boolean addVertex(BaseVertex<V> vertex){
		return directedGraph.addVertex(vertex);
	}

	public boolean addEdge(BaseVertex<V> fromVertice, BaseVertex<V> toVertice, BaseEdge<E> edge){
        return directedGraph.addEdge(fromVertice, toVertice, edge);
    }
	
	public boolean containsVertex(BaseVertex<V> vertex) {
        return directedGraph.containsVertex(vertex);
    }
	
	public boolean containsEdge(BaseEdge<E> edge) {
        return directedGraph.containsEdge(edge);
    }
	
}
