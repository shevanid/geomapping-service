	package com.flipkart.geomapping.services;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;

import com.flipkart.geomapping.configurations.GeoMappingConfiguration;
import com.flipkart.geomapping.resources.filters.JpaContextRequestFilter;
import com.flipkart.geomapping.resources.filters.JpaContextResponseFilter;
import com.hubspot.dropwizard.guice.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author deepak.shevani on Nov 7, 2014
 *
 */

public class GeoMappingService extends Application<GeoMappingConfiguration> {

	public static void main(String[] args) throws Exception {
		new GeoMappingService().run(args);
	}

	@Override
	public void initialize(Bootstrap<GeoMappingConfiguration> bootstrap) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		map.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/geomapping");
		map.put("hibernate.connection.username", "root");
		map.put("hibernate.connection.password", "");
		map.put("hibernate.show_sql","true");
		
		ActiveJpaAgentLoader.instance().loadAgent();
		JPA.instance.addPersistenceUnit("geomapping", map, true);
		
		GuiceBundle<GeoMappingConfiguration> guiceBundle = GuiceBundle
				.<GeoMappingConfiguration> newBuilder()
				.addModule(new GeoMappingModule())
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(GeoMappingConfiguration.class)
				.build();
		bootstrap.addBundle(guiceBundle);
		
		bootstrap.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		bootstrap.getObjectMapper().setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
				
	}

	@Override
	public void run(GeoMappingConfiguration configuration, Environment environment) throws Exception {
		environment.jersey().getResourceConfig().getContainerRequestFilters().add(new JpaContextRequestFilter());
		environment.jersey().getResourceConfig().getContainerResponseFilters().add(new JpaContextResponseFilter());
	}

}
