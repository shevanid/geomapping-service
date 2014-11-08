package com.flipkart.geomapping.graph;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

public class GraphException extends Exception {

	private static final long serialVersionUID = 1L;

	public GraphException(String message) {
		super(message);
	}

	public GraphException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
