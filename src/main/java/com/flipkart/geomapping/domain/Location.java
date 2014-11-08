package com.flipkart.geomapping.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.dropwizard.jackson.JsonSnakeCase;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.activejpa.entity.Model;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

@Entity
@Table(name = "locations")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class Location extends Model {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private String name;
	
	private String code;
	
	@ManyToOne
	@JoinColumn(name="locationTypeId")
	private LocationType type;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="locationId")
	@JsonManagedReference("tag")
	private Set<LocationTag> tags = new HashSet<LocationTag>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="fromLocationId")
	@JsonManagedReference("fromLocation")
	private Set<LocationRelation> fromLocations = new HashSet<LocationRelation>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="toLocationId")
	@JsonManagedReference("toLocation")
	private Set<LocationRelation> toLocations = new HashSet<LocationRelation>();
	
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Set<LocationTag> getTags() {
		return tags;
	}
	
	public void setTags(Set<LocationTag> tags) {
		this.tags = tags;
	}

	public LocationType getType() {
		return type;
	}

	public void setType(LocationType type) {
		this.type = type;
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
