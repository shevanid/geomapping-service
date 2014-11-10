package com.flipkart.geomapping.resources;

import java.util.List;

import io.dropwizard.jackson.JsonSnakeCase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.geomapping.domain.Location;
import com.google.inject.Singleton;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */
	
@Path("/location")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class LocationResource {
	
	@GET
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getFacilities() {
		return Location.all();
	}

}
