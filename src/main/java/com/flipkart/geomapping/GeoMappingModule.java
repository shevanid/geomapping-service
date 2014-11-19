package com.flipkart.geomapping;

import com.flipkart.geomapping.service.GeoGraphService;
import com.flipkart.geomapping.service.impl.SimpleGraphService;
import com.google.inject.AbstractModule;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

public class GeoMappingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GeoGraphService.class).to(SimpleGraphService.class).asEagerSingleton();
	}		

}
