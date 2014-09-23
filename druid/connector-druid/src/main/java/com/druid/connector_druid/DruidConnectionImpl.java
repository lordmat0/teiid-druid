/*
 * ${license}
 */
package com.druid.connector_druid;


import javax.resource.ResourceException;

import org.teiid.resource.spi.BasicConnection;

import com.druid.translator_druid.DruidConnection;

/**
 * Connection to the resource. You must define druidConnection interface, that 
 * extends the "javax.resource.cci.Connection"
 */
public class DruidConnectionImpl extends BasicConnection implements DruidConnection {

    private DruidManagedConnectionFactory config;

    public DruidConnectionImpl(DruidManagedConnectionFactory env) {
        this.config = env;
        // todo: connect to your source here
    }
    
    public void close() {
    	
    }

}