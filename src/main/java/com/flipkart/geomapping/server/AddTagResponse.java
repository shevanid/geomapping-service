package com.flipkart.geomapping.server;

import io.dropwizard.jackson.JsonSnakeCase;
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
public class AddTagResponse {
	
	boolean status;
	
	String message;

}
