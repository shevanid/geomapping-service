package com.flipkart.geomapping.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

public class JGraphTGeoGraphService implements GeoGraphService {
	
	private Node<Location> root;
	
	private Map<Long, Node<Location>> locationMap = new HashMap<Long, Node<Location>>();
	
	@Inject
	public JGraphTGeoGraphService() {
		initializeGraph();
	}
	
	public String printGraph() {
		StringBuffer sb = new StringBuffer();
		sb.append("(" + root.toString()  + ")\n\n");
		printSubTree(root, sb);
		return sb.toString();
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
			node.addChild(childNode);
			generateSubTreeFor(childNode);
		}
	}
		
	class Node<T> {
		private T node;
		private Map<String, Map<String, Object>> attributes = new HashMap<String, Map<String, Object>>();
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
