package com.flipkart.geomapping.graph.objects;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class JGraphTGraph<V,E> {

	DirectedGraph<BaseVertex<V>, BaseEdge<E>> directedGraph;
	
	Map<Long, BaseVertex<V>> idNodeMap = new HashMap<Long, BaseVertex<V>>();

	@SuppressWarnings("unchecked")
	public JGraphTGraph() {
		BaseEdge<E> e = new BaseEdge<E>();
		Class<? extends BaseEdge<E>> edgeClass = (Class<? extends BaseEdge<E>>) e.getClass();
		directedGraph = new DefaultDirectedGraph<BaseVertex<V>, BaseEdge<E>>(edgeClass);
	}
	
	private List<V> getAllChildrenFor(Long id) {
		List<V> children = new ArrayList<V>();
        BaseVertex<V> node = idNodeMap.get(id);
        if (id != null) {
        	// TODO
        } 
        return children;
    }
	
	

	public boolean addVertex(BaseVertex<V> vertex){
		idNodeMap.put(vertex.getId(), vertex);
		return directedGraph.addVertex(vertex);
	}

	public boolean addVertex(V node, long id){
		BaseVertex<V> vertex = new BaseVertex<V>(node, id);
		idNodeMap.put(id, vertex);
		return directedGraph.addVertex(vertex);
	}
	
	public boolean addEdge(BaseVertex<V> fromVertex, BaseVertex<V> toVertex, BaseEdge<E> edge){
        return directedGraph.addEdge(fromVertex, toVertex, edge);
    }
	
	public boolean addEdge(long fromId, long toId, E connection, long connectionId){
		BaseVertex<V> fromVertex = idNodeMap.get(fromId);
		BaseVertex<V> toVertex = idNodeMap.get(toId);
		BaseEdge<E> edge = new BaseEdge<E>(connection, connectionId);
        return directedGraph.addEdge(fromVertex, toVertex, edge);
    }
	
	public boolean containsVertex(BaseVertex<V> vertex) {
        return directedGraph.containsVertex(vertex);
    }
	
	public boolean containsEdge(BaseEdge<E> edge) {
        return directedGraph.containsEdge(edge);
    }
	
}
