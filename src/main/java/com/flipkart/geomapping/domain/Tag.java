package com.flipkart.geomapping.domain;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author deepak.shevani on Nov 11, 2014
 *
 */

@Entity
@Table(name="tags")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class Tag extends BaseDomain {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String value;
	
	@ManyToOne
	@JoinColumn(name="tagTypeId")
	private TagType type;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="location_tag_relations", 
    	joinColumns={@JoinColumn(name="tagId")}, 
    	inverseJoinColumns={@JoinColumn(name="locationId")})
	@JsonManagedReference("location")
	@JsonIgnore
	private Set<Location> locations = new HashSet<Location>();

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	
	public TagType getType() {
		return type;
	}

	public void setType(TagType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "{" + type.getType() + "=" + value + "(" + type.getGroup().getName()  + ")" + "}";
	}
	
}
