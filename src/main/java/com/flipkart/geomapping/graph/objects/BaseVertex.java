package com.flipkart.geomapping.graph.objects;

import java.util.HashMap;
import java.util.Map;

import com.flipkart.geomapping.graph.GraphException;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

public class BaseVertex<T> {

	/** Represents node **/
	
	private T node;
	
	/** Represents id **/
	
	private long id;
	
	/** Stores attributes specific to a group **/
	
	private Map<String, Map<String, Object>> attributes;
	
	/** Constructor **/
	
	public BaseVertex(T node, long id) {
        this.node = node;
        this.id = id;
    }

	public T getNode() {
		return node;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Map<String, Map<String, Object>> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String,Map<String, Object>> givenAttributes) {
		this.attributes = givenAttributes;
	}
	
	public void setAttributesForGroup(String group, Map<String, Object> givenAttributes) {
		this.attributes.put(group, new HashMap<String, Object>());
	}
	
	public void addAttributeIn(String group, String key, Object value) throws GraphException {
		if (this.attributes.containsKey(group)) {
			this.attributes.get(group).put(key, value);
		}
		else {
			throw new GraphException("No group configured for " + group);
		}
	}
	
	@Override
    public String toString() {
        return "BaseVertex {" +
                  "node=" + node + ", " +
                  "attributes=" + attributes + ", " +
                '}';
    }
	
}
