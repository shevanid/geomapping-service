package com.flipkart.geomapping.resources;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
@Path("/locations")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class LocationResource {
	
	@GET
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getAllLocations() {
		return Location.all();
	}
	
	@GET
	@Path("/{code}")
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Location getLocationForCode(@PathParam("code")String code) {
		Location location = null;
		if (code != null && !code.isEmpty()) {
			List<Location> locations = Location.where("code", code);
			if (locations.isEmpty()) {
				return null;
			}
			location = locations.get(0);
		}
		return location;
	}
	
	@GET
	@Path("/id/{id}")
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Location getLocationForId(@PathParam("id")Long id) {
		return Location.findById(id);
	}

}
