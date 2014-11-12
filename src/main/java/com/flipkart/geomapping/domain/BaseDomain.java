package com.flipkart.geomapping.domain;

/**
 * @author deepak.shevani on Nov 6, 2014
 *
 */

import org.activejpa.entity.Model;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

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
