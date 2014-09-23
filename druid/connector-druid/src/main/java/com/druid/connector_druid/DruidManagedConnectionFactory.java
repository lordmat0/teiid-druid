/*
 * ${license}
 */
package com.druid.connector_druid;

import javax.resource.ResourceException;
import javax.resource.spi.InvalidPropertyException;

import org.teiid.resource.spi.BasicConnectionFactory;
import org.teiid.resource.spi.BasicManagedConnectionFactory;

public class DruidManagedConnectionFactory extends BasicManagedConnectionFactory {
	
	@Override
	public BasicConnectionFactory<DruidConnectionImpl> createConnectionFactory() throws ResourceException {
		return new BasicConnectionFactory<DruidConnectionImpl>() {
			@Override
			public DruidConnectionImpl getConnection() throws ResourceException {
				return new DruidConnectionImpl(DruidManagedConnectionFactory.this);
			}
		};
	}	
	
	// ra.xml files getters and setters go here.

}
