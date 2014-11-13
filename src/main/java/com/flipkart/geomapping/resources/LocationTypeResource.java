package com.flipkart.geomapping.resources;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.geomapping.domain.LocationType;
import com.google.inject.Singleton;

/**
 * @author deepak.shevani on Nov 10, 2014
 *
 */

@Path("/location_types")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class LocationTypeResource {

	@GET
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<LocationType> getAllLocations() {
		return LocationType.all();
	}
	
}
