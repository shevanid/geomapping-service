package com.flipkart.geomapping.configurations;

import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.bazaarvoice.dropwizard.assets.AssetsBundleConfiguration;
import com.bazaarvoice.dropwizard.assets.AssetsConfiguration;

/**
 * 
 * @author deepak.shevani on Nov 6, 2014
 *
 */

@Getter
@Setter
@NoArgsConstructor

public class GeoMappingConfiguration extends Configuration implements AssetsBundleConfiguration{
	
	String test;

	/* (non-Javadoc)
	 * @see com.bazaarvoice.dropwizard.assets.AssetsBundleConfiguration#getAssetsConfiguration()
	 */
	@Override
	public AssetsConfiguration getAssetsConfiguration() {
		// TODO Auto-generated method stub
		return new AssetsConfiguration();
	}
	
}
