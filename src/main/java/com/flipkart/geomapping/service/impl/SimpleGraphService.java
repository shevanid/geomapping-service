package com.flipkart.geomapping.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.flipkart.geomapping.domain.Location;
import com.flipkart.geomapping.domain.LocationRelation;
import com.flipkart.geomapping.service.GeoGraphService;
import com.google.inject.Inject;

/**
 * @author deepak.shevani on Nov 12, 2014
 *
 */

public class SimpleGraphService implements GeoGraphService {
	
	private Node<Location> root;
	
	private Map<Long, Node<Location>> locationMap = new HashMap<Long, Node<Location>>();
	
	@Inject
	public SimpleGraphService() {
		initializeGraph();
	}
	
	public String printGraph() {
		StringBuffer sb = new StringBuffer();
		sb.append("(" + root.toString()  + ")\n\n");
		printSubTree(root, sb);
		return sb.toString();
	}
	
	public Map<String, Location> getParentsForLocationId(Long id) {
		Map<String, Location> parents = new HashMap<String, Location>();
		Node<Location> node = locationMap.get(id);
		while (node.getParent() != null) {
			parents.put(node.getParent().getNode().getType().getType(), node.getParent().getNode());
			node = node.getParent();
		}
		return parents;
	}
	
	public List<Location> getLocationsByName(String name) {
		return Location.where("name", name);
	}
	
	public List<Location> getLocationsByCode(String code) {
		return Location.where("code", code);
	}
	
	public Location getLocationById(Long id) {
		List<Location> locations = Location.where("id", id);
		if (locations.isEmpty()) {
			return null;
		}
		return locations.get(0);
	}
	
	public List<Location> getChildrenForLocationId(Long id) {
		List<Location> locations = new ArrayList<Location>();		
		Node<Location> node = locationMap.get(id);
		if (node != null) {
			for (Node<Location> child : node.getChildren()) {
				locations.add(child.getNode());
				locations.addAll(getChildrenForLocationId(child.getNode().getId()));
			}
		}
		return locations;
	}
	
	public List<Location> getSpecificChildrenForLocationId(Long id, String name) {
		List<Location> locations = getChildrenForLocationId(id);
		Iterator<Location> it = locations.iterator();
		while (it.hasNext()) {
			if (!it.next().getType().getType().equalsIgnoreCase(name)) {
				it.remove();
			}
		}
		return locations;
	}
	
	private void printSubTree(Node<Location> node, StringBuffer sb) {
		List<Node<Location>> children = new ArrayList<Node<Location>>();
		if (node.getChildren().isEmpty()) {
			return;
		}
		sb.append("Children of " + node + " => [");
		for (Node<Location> child : node.getChildren()) {
			sb.append(child.toString()  + " ");
			children.add(child);
		}
		sb.append("]\n");
		for (Node<Location> child : children) {
			printSubTree(child, sb);
		}
	}
	
	private void initializeGraph() {
		List<Location> country = Location.where("name", "India");
		if (!country.isEmpty()) {
			root = new Node<Location>(country.get(0));
			locationMap.put(country.get(0).getId(), root);
			generateSubTreeFor(root);
		}		
	}
	
	private void generateSubTreeFor(Node<Location> node) {
		Set<LocationRelation> childRelations = node.getNode().getToLocations();
		for (LocationRelation r : childRelations) {
			Node<Location> childNode = new Node<Location>(r.getFromLocation());
			locationMap.put(r.getFromLocation().getId(), childNode);
			childNode.setParent(node);
			node.addChild(childNode);
			generateSubTreeFor(childNode);
		}
	}
		
	class Node<T> {
		private T node;
		private Map<String, Map<String, Object>> attributes = new HashMap<String, Map<String, Object>>();
		private Node<T> parent = null;
		private List<Node<T>> children = new ArrayList<Node<T>>();
		public Node(T node) {
			this.node = node;
		}
		public void addChild(Node<T> child) {
			children.add(child);
		}
		public List<Node<T>> getChildren() {
			return children;
		}
		public Node<T> getParent() {
			return parent;
		}
		public void setParent(Node<T> parent) {
			this.parent = parent;
		}
		public T getNode() {
			return node;
		}
		public String toString() {
			return node.toString();
		}
		public Map<String, Map<String, Object>> getAttributes() {
			return attributes;
		}
		public Map<String, Object> getAttributesForGroup(String group) {
			return attributes.get(group);
		}
		public void addAttribute(String group, String key, Object value) {
			if (!attributes.containsKey(group)) {
				attributes.put(group, new HashMap<String, Object>());
			}
			attributes.get(group).put(key, value);
		}
	}
}
