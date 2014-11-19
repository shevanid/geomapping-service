package com.flipkart.geomapping.resources;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.geomapping.domain.Location;
import com.flipkart.geomapping.domain.LocationTagRelation;
import com.flipkart.geomapping.domain.Tag;
import com.flipkart.geomapping.domain.TagGroup;
import com.flipkart.geomapping.domain.TagType;
import com.flipkart.geomapping.server.AddTagRequest;
import com.flipkart.geomapping.server.AddTagResponse;
import com.flipkart.geomapping.service.GeoGraphService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author deepak.shevani on Nov 19, 2014
 *
 */

@Path("/tags")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class TagResource {
	
	private GeoGraphService graphService;
	
	@Inject
	public TagResource(GeoGraphService graphService) {
		this.graphService = graphService;
	}
	
	@GET
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public List<Tag> getAllTags() {
		return Tag.all();
	}
	
	@GET
	@Path("/{id}")
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public Tag getTagForId(@PathParam("id")Long id) {
		return Tag.findById(id);
	}
	
	@POST
	@Path("/new")
    @Timed
    @ExceptionMetered()
    @Produces(MediaType.APPLICATION_JSON)
	public AddTagResponse addTag(String body, @DefaultValue("false") @QueryParam("recursive") boolean recursive) {
		AddTagRequest request = null;
		AddTagResponse response = new AddTagResponse();
		try {
			request = AddTagRequest.deserializeFromJson(body);
			LocationTagRelation relation = null;
			Location location = null;
			TagGroup group = null;
			TagType tagType = null;
			Tag tag = null;
			String value = request.getValue();
			/** Check if location exists **/
			List<Location> locations = Location.where("code", request.getLocation());
			if (locations.isEmpty()) {
				response.setMessage("Location does not exist for code '" + request.getLocation() + "'");
				response.setStatus(false);
				return response;
			}
			else {
				 location = locations.get(0);
			}
			/** Check if tag group exists **/
			List<TagGroup> tagGroups = TagGroup.where("name", request.getGroup());
			if (tagGroups.isEmpty()) {
				response.setMessage("Tag Group does not exist for code '" + request.getGroup() + "'");
				response.setStatus(false);
				return response;
			}
			else {
				group = tagGroups.get(0);
			}
			/** Check if tag type exists for that group. If not create tagType **/
			List<TagType> tagTypes = TagType.where("type", request.getTagType(), "group", group);
			if (!tagTypes.isEmpty()) {
				tagType = tagTypes.get(0);
			}
			else {
				tagType = new TagType();
				tagType.setType(request.getTagType());
				tagType.setGroup(group);
				tagType.persist();
			}
			/** Check if tag exists for that tag_type and value. If not create tag **/
			List<Tag> tags = Tag.where("type", tagType, "value", value);
			if (!tags.isEmpty()) {
				tag = tags.get(0);
			}
			else {
				tag = new Tag();
				tag.setType(tagType);
				tag.setValue(value);
				tag.persist();
			}
			/** Create location tag relation **/
			List<LocationTagRelation> relations = LocationTagRelation.where("location", location, "tag", tag);
			if (relations.isEmpty()) {
				relation = new LocationTagRelation();
				relation.setLocationId(location.getId());
				relation.setTagId(tag.getId());
				relation.persist();
				if (recursive) {
					List<Location> children = graphService.getChildrenForLocationId(location.getId());
					for (Location child : children) {
						relation = new LocationTagRelation();
						relation.setLocationId(child.getId());
						relation.setTagId(tag.getId());
						relation.persist();
					}
				}
				response.setMessage("Tag added successfully to location");
				response.setStatus(true);
			}
			else {
				response.setMessage("Tag already associated with location");
				response.setStatus(false);
			}
		} 
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
			e.printStackTrace();
		}
		return response;
	}
	
}
