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

import lombok.Getter;
import lombok.Setter;

/**
 * @author deepak.shevani on Nov 11, 2014
 *
 */

@Getter
@Setter

@Entity
@Table(name="tag_types")
@Access(AccessType.FIELD)
@JsonSnakeCase
public class TagType extends BaseDomain {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String type;

	@ManyToOne
	@JoinColumn(name="tagGroupId")
	private TagGroup group;
	
	public TagType() {
	}
	
	public TagType(String type) {
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public TagGroup getGroup() {
		return group;
	}

	public void setGroup(TagGroup group) {
		this.group = group;
	}

	public String toString() {
		return group + "_" + type;
	}
	
}
