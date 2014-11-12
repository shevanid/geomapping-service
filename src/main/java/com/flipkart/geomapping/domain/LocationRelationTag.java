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

import org.activejpa.entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

@Entity
@Table(name = "location_relation_tags")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class LocationRelationTag extends Model {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private String name;
    
    private String value;

    private String group;
	
	@ManyToOne
	@JoinColumn(name="locationRelationId")
	@JsonIgnore
	private LocationRelation locationRelation;

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

	public LocationRelation getLocationRelation() {
		return locationRelation;
	}

	public void setLocationRelation(LocationRelation locationRelation) {
		this.locationRelation = locationRelation;
	}

}
