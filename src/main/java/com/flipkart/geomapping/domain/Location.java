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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

@Entity
@Table(name = "locations")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class Location extends BaseDomain {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private String name;
	
	private String displayName;
	
	private String code;
	
	private int isActive;
	
	@ManyToOne
	@JoinColumn(name="locationTypeId")
	private LocationType type;
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="fromLocationId")
	@JsonManagedReference("fromLocation")
	@JsonIgnoreProperties
	private Set<LocationRelation> fromLocations = new HashSet<LocationRelation>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="toLocationId")
	@JsonManagedReference("toLocation")
	@JsonIgnoreProperties
	private Set<LocationRelation> toLocations = new HashSet<LocationRelation>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="location_tag_relations", 
    	joinColumns={@JoinColumn(name="locationId")}, 
    	inverseJoinColumns={@JoinColumn(name="tagId")})
	@JsonManagedReference("tag")
	private Set<Tag> tags = new HashSet<Tag>();
	
	@Override
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
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public LocationType getType() {
		return type;
	}

	public void setType(LocationType type) {
		this.type = type;
	}
	
	@JsonIgnore
	public Set<LocationRelation> getFromLocations() {
		return fromLocations;
	}

	public void setFromLocations(Set<LocationRelation> fromLocations) {
		this.fromLocations = fromLocations;
	}

	@JsonIgnore
	public Set<LocationRelation> getToLocations() {
		return toLocations;
	}

	public void setToLocations(Set<LocationRelation> toLocations) {
		this.toLocations = toLocations;
	}
	
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public String toString() {
		return "{" + id + ":" + name + "}";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Location other = (Location) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} 
		else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}
	
}
