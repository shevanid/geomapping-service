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

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

@Entity
@Table(name="location_tags")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class LocationTag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private String name;
    
    private String value;
    
    private String group;
    
    @ManyToOne
    @JoinColumn(name="locationId")
    @JsonBackReference("tag")
    private Location location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
    
}
