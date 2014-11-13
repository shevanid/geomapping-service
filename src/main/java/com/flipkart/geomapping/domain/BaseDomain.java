package com.flipkart.geomapping.domain;

/**
 * @author deepak.shevani on Nov 6, 2014
 *
 */

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import org.activejpa.entity.Model;

@MappedSuperclass
public abstract class BaseDomain extends Model {

	private Timestamp createdAt;

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	protected void preSave() {
		createdAt = new Timestamp(System.currentTimeMillis());
	}

}
