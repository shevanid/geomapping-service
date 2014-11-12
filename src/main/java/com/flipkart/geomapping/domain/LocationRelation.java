package com.flipkart.geomapping.domain;

import io.dropwizard.jackson.JsonSnakeCase;

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

import org.activejpa.entity.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

@Entity
@Table(name = "location_relations")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class LocationRelation extends Model {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private Long fromLocationId;

    private Long toLocationId;
    
    @OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="locationTagId")
	private Set<LocationRelationTag> tags;
    
	@ManyToOne
	@JoinColumn(name="fromLocationId", insertable = false, updatable = false)
	@JsonManagedReference("fromLocation")
	private Location fromLocation;
	
	@ManyToOne
	@JoinColumn(name="toLocationId", insertable = false, updatable = false)
	@JsonManagedReference("toLocation")
	private Location toLocation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<LocationRelationTag> getTags() {
		return tags;
	}

	public void setTags(Set<LocationRelationTag> tags) {
		this.tags = tags;
	}

	public Location getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(Location fromLocation) {
		this.fromLocation = fromLocation;
	}

	public Location getToLocation() {
		return toLocation;
	}

	public void setToLocation(Location toLocation) {
		this.toLocation = toLocation;
	}

	public Long getFromLocationId() {
		return fromLocationId;
	}

	public void setFromLocationId(Long fromLocationId) {
		this.fromLocationId = fromLocationId;
	}

	public Long getToLocationId() {
		return toLocationId;
	}

	public void setToLocationId(Long toLocationId) {
		this.toLocationId = toLocationId;
	}

}
