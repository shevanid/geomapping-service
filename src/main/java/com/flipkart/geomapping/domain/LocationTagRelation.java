package com.flipkart.geomapping.domain;

import io.dropwizard.jackson.JsonSnakeCase;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author deepak.shevani on Nov 18, 2014
 *
 */

@Entity
@Table(name = "location_tag_relations")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class LocationTagRelation extends BaseDomain {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private Long locationId;

    private Long tagId;

	@ManyToOne
	@JoinColumn(name="locationId", insertable = false, updatable = false)
	@JsonManagedReference("location")
	private Location location;
	
	@ManyToOne
	@JoinColumn(name="tagId", insertable = false, updatable = false)
	@JsonManagedReference("tag")
	private Tag tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
}
