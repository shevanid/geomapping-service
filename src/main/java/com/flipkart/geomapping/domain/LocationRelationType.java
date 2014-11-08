package com.flipkart.geomapping.domain;

import io.dropwizard.jackson.JsonSnakeCase;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

@Entity
@Table(name="location_relation_types")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class LocationRelationType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private String type;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}