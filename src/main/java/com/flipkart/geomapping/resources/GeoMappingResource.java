package com.flipkart.geomapping.resources;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.geomapping.domain.Location;
import com.flipkart.geomapping.service.GeoGraphService;
import com.flipkart.geomapping.service.impl.JGraphTGeoGraphService;

/**
 * @author deepak.shevani on Nov 12, 2014
 *
 */

@Path("/graph")
@JsonSnakeCase
@Produces({"application/json"})
public class GeoMappingResource {
	
	private static GeoGraphService graphService = new JGraphTGeoGraphService();
	
	@GET
	@Timed
    @ExceptionMetered()
	public String test(String body) {
		return graphService.printGraph();
	}
	
	@GET
	@Path("/name/{location_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getLocationForName(@PathParam("location_name") String name) {
		return graphService.getLocationsByName(name);
	}
	
	@GET
	@Path("/code/{location_code}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getLocationForCode(@PathParam("location_code") String code) {
		return graphService.getLocationsByCode(code);
	}

	@GET
	@Path("/id/{location_id}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Location getLocationForCode(@PathParam("location_id") Long id) {
		return graphService.getLocationById(id);
	}
	
	@GET
	@Path("/children/{location_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getChildrenForLocationName(@PathParam("location_name") String name) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByName(name);
		for (Location l : possibleNodes) {
			locations.addAll(graphService.getChildrenForLocationId(l.getId()));
		}
		return locations;
	}
	
	@GET
	@Path("/pincodes/{location_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getPinCodesForLocationId(@PathParam("location_name") String name) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByName(name);
		for (Location l : possibleNodes) {
			locations.addAll(graphService.getSpecificChildrenForLocationId(l.getId(), "pincode"));
		}
		return locations;
	}
	
	@GET
	@Path("/states/{location_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getStatesForLocationId(@PathParam("location_name") String name) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByName(name);
		for (Location l : possibleNodes) {
			locations.addAll(graphService.getSpecificChildrenForLocationId(l.getId(), "state"));
		}
		return locations;
	}
	
	@GET
	@Path("/cities/{location_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getCitiesForLocationId(@PathParam("location_name") String name) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByName(name);
		for (Location l : possibleNodes) {
			locations.addAll(graphService.getSpecificChildrenForLocationId(l.getId(), "city"));
		}
		return locations;
	}
	
	@GET
	@Path("/hierarchy/{location_name}/{parent_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Location getParentForLocation(
			@PathParam("location_name") String location_name ,
			@PathParam("parent_name") String parent_name)
	{
		Map<String, Location> parents = new HashMap<String, Location>();
		List<Location> possibleNodes = graphService.getLocationsByName(location_name);
		if (possibleNodes.isEmpty()) {
			return null;
		}
		parents = graphService.getParentsForLocation(possibleNodes.get(0).getId());
		return parents.get(parent_name);
	}
	
	@GET
	@Path("/hierarchy/{location_code}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Location> getAllParentsForLocation(
			@PathParam("location_code") String location_code) 
	{
		Map<String, Location> parents = new HashMap<String, Location>();
		List<Location> possibleNodes = graphService.getLocationsByName(location_code);
		if (possibleNodes.isEmpty()) {
			return parents;
		}
		parents = graphService.getParentsForLocation(possibleNodes.get(0).getId());
		return parents;
	}
	
}
