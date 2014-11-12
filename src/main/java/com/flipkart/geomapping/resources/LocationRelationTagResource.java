package com.flipkart.geomapping.resources;

import java.util.List;

import io.dropwizard.jackson.JsonSnakeCase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.geomapping.domain.LocationRelation;
import com.google.inject.Singleton;

/**
 * @author deepak.shevani on Nov 10, 2014
 *
 */

@Path("/location_relation_tags")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class LocationRelationTagResource {

	@GET
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<LocationRelation> getAllLocations() {
		return LocationRelation.all();
	}
	
}