package com.flipkart.geomapping.service;

import java.util.List;
import java.util.Map;

import com.flipkart.geomapping.domain.Location;

/**
 * @author deepak.shevani on Nov 12, 2014
 *
 */

public interface GeoGraphService {
	
	String printGraph();
	
	List<Location> getLocationsByName(String name);
	
	List<Location> getLocationsByCode(String code);
	
	Location getLocationById(Long code);
	
	List<Location> getChildrenForLocationId(Long id);
	
	List<Location> getSpecificChildrenForLocationId(Long id, String name);
	
	Map<String, Location> getParentsForLocationId(Long id);
	
}
