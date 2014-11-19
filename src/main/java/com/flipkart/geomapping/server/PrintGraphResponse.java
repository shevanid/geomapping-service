package com.flipkart.geomapping.server;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author deepak.shevani on Nov 19, 2014
 *
 */

@Getter
@Setter
@ToString
@JsonSnakeCase
public class PrintGraphResponse {
	
	String name;
	
	String tags;
	
	Set<PrintGraphResponse> children = new HashSet<PrintGraphResponse>();
	
	public void addPrintGraphResponse(PrintGraphResponse child) {
		children.add(child);
	}
	
}
