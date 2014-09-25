/*
 * ${license}
 */
package com.druid.connector_druid;

import javax.resource.ResourceException;

import org.teiid.resource.spi.BasicConnection;

import com.druid.translator_druid.DruidConnection;

import com.yahoo.sql4d.sql4ddriver.DDataSource;

/**
 * Connection to the resource. You must define druidConnection interface, that
 * extends the "javax.resource.cci.Connection"
 */
public class DruidConnectionImpl extends BasicConnection implements
		DruidConnection {

	private DruidManagedConnectionFactory config;
	private DDataSource connection;

	public DruidConnectionImpl(DruidManagedConnectionFactory env) {
		this.config = env;

		String brokerIp = env.getBrokerNodeIp();
		int brokerPort = env.getBrokerPort();

		String coordinatorIp = env.getCoordinatorIp();
		int coordinatorPort = env.getCoordinatorPort();

		String proxyIp = env.getProxyIp();
		int proxyPort = env.getBrokerPort();

		if (proxyIp == null || proxyIp.equals("") || proxyPort == 0) {
			// No proxy
			this.connection = new DDataSource(brokerIp, brokerPort,
					coordinatorIp, coordinatorPort);
		} else {
			// Use the proxy
			this.connection = new DDataSource(brokerIp, brokerPort,
					coordinatorIp, coordinatorPort, proxyIp, proxyPort);
		}

		// Example connection
		// this.connection = new DDataSource("192.168.30.170", 8080, "192.168.30.170", 8085);
	}

	public DDataSource getConnection() {
		return connection;
	}

	public void close() {

	}

}
