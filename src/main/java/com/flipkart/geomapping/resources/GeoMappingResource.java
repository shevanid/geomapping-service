package com.flipkart.geomapping.resources;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.geomapping.domain.Location;
import com.flipkart.geomapping.domain.Tag;
import com.flipkart.geomapping.domain.TagGroup;
import com.flipkart.geomapping.domain.TagType;
import com.flipkart.geomapping.service.GeoGraphService;
import com.google.inject.Inject;

/**
 * @author deepak.shevani on Nov 12, 2014
 *
 */

@Path("/geomapping")
@JsonSnakeCase
@Produces({"application/json"})
public class GeoMappingResource {
	
	private GeoGraphService graphService;
	
	@Inject
	public GeoMappingResource(GeoGraphService graphService) {
		this.graphService = graphService;
	}
	
	@GET
	@Path("/graph")
	@Timed
    @ExceptionMetered()
	public String test(String body) {
		return graphService.printGraph();
	}
	
	@GET
	@Path("/children/{location_code}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getChildrenForLocationName(@PathParam("location_code") String code) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByCode(code);
		if (!possibleNodes.isEmpty()) {
			Location location = possibleNodes.get(0);
			locations = graphService.getChildrenForLocationId(location.getId());
		}
		return locations;
	}
	
	@GET
	@Path("/children/{location_code}/pincodes")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getPinCodesForLocationCode(@PathParam("location_code") String code) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByCode(code);
		if (!possibleNodes.isEmpty()) {
			Location location = possibleNodes.get(0);
			locations = graphService.getSpecificChildrenForLocationId(location.getId(), "pincode");
		}
		return locations;
	}
	
	@GET
	@Path("/children/{location_code}/states")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getStatesForLocationCode(@PathParam("location_code") String code) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByCode(code);
		if (!possibleNodes.isEmpty()) {
			Location location = possibleNodes.get(0);
			locations = graphService.getSpecificChildrenForLocationId(location.getId(), "state");
		}
		return locations;
	}

	@GET
	@Path("/children/{location_code}/cities")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Location> getCitiesForLocationCode(@PathParam("location_code") String code) {
		List<Location> locations = new ArrayList<Location>();
		List<Location> possibleNodes = graphService.getLocationsByCode(code);
		if (!possibleNodes.isEmpty()) {
			Location location = possibleNodes.get(0);
			locations = graphService.getSpecificChildrenForLocationId(location.getId(), "city");
		}
		return locations;
	}
	
	
	@GET
	@Path("/hierarchy/{location_code}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Map<String, Location>> getAllParentsForLocationCode(
			@PathParam("location_code") String location_code) 
	{
		Map<String, Map<String, Location>> hierarchy = new HashMap<String, Map<String, Location>>();
		String[] codes = location_code.split(",");
		if (codes.length > 0) {
			for (String code : codes) {
				List<Location> possibleNodes = graphService.getLocationsByCode(code);
				if (!possibleNodes.isEmpty()) {
					hierarchy.put(code, graphService.getParentsForLocationId(possibleNodes.get(0).getId()));
				}
			}
		}
		return hierarchy;
	}
	
	@GET
	@Path("/tags/{location_code}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Map<TagGroup, Set<Tag>>> getAllTagsForLocationCode(
			@PathParam("location_code") String location_code) 
	{
		Map<String, Map<TagGroup, Set<Tag>>> tags = new HashMap<String, Map<TagGroup, Set<Tag>>>();
		String[] codes = location_code.split(",");
		if (codes.length > 0) {
			for (String code : codes) {
				List<Location> possibleNodes = graphService.getLocationsByCode(code);
				if (!possibleNodes.isEmpty()) {
					tags.put(code, possibleNodes.get(0).getGroupedTags());
				}	
			}
		}
		return tags;
	}
	
	@GET
	@Path("/tags/{location_code}/{group_name}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Set<Tag>> getSpecificTagsForLocationCode(
			@PathParam("location_code") String location_code,
			@PathParam("group_name") String group_name) 
	{
		Map<String, Set<Tag>> tags = new HashMap<String, Set<Tag>>();
		List<TagGroup> groups = TagGroup.where("name", group_name);
		if (groups.isEmpty()) {
			return tags;
		}
		String[] codes = location_code.split(",");
		if (codes.length > 0) {
			for (String code : codes) {
				List<Location> possibleNodes = graphService.getLocationsByCode(code);
				Set<Tag> possibleTags = new HashSet<Tag>();
				if (!possibleNodes.isEmpty() && possibleNodes.get(0).getGroupedTags().containsKey(groups.get(0))) {
					possibleTags.addAll(possibleNodes.get(0).getGroupedTags().get(groups.get(0)));
				}
				tags.put(code, possibleTags);
			}
		}
		return tags;
	}
	
	@GET
	@Path("/locationtags/{tag_key}/{tag_value}")
	@Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Set<Location> getLocationForTag(
			@PathParam("tag_key") String tagKey,
			@PathParam("tag_value") String tagValue,
			@DefaultValue("common") @QueryParam("group") String groupName) 
	{
		Set<Location> taggedLocations = new HashSet<Location>();
		
		List<TagGroup> groups = TagGroup.where("name", groupName);
		if (groups.isEmpty()) {
			return taggedLocations;
		}
		TagGroup group = groups.get(0);
		
		List<TagType> tagTypes = TagType.where("type", tagKey, "group", group);
		if (tagTypes.isEmpty()) {
			return taggedLocations;
		}
		TagType tagType = tagTypes.get(0);
		
		List<Tag> tags = Tag.where("value", tagValue, "type", tagType);
		if (tags.isEmpty()) {
			return taggedLocations;
		}
		Tag tag = tags.get(0);
		
		taggedLocations.addAll(tag.getLocations());
		
		return taggedLocations;
	}
	
}
