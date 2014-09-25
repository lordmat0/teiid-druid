/*
 * ${license}
 */
package com.druid.connector_druid;

import javax.resource.ResourceException;

import org.teiid.core.BundleUtil;
import org.teiid.resource.spi.BasicConnectionFactory;
import org.teiid.resource.spi.BasicManagedConnectionFactory;

public class DruidManagedConnectionFactory extends
		BasicManagedConnectionFactory {

	private static final long serialVersionUID = -1437528038325708535L;


	private String brokerNodeIp;
	private int brokerPort;

	private String coordinatorIp;
	private int coordinatorPort;

	private String proxyIp;
	private String proxyPort;

	@Override
	public BasicConnectionFactory<DruidConnectionImpl> createConnectionFactory()
			throws ResourceException {
		return new BasicConnectionFactory<DruidConnectionImpl>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -9208138578461088218L;

			@Override
			public DruidConnectionImpl getConnection() throws ResourceException {
				return new DruidConnectionImpl(
						DruidManagedConnectionFactory.this);
			}
		};
	}

	// ra.xml files getters and setters go here.

	public String getBrokerNodeIp() {
		return brokerNodeIp;
	}

	public void setBrokerNodeIp(String brokerNodeIp) {
		this.brokerNodeIp = brokerNodeIp;
	}

	public int getBrokerPort() {
		return brokerPort;
	}

	public void setBrokerPort(int brokerPort) {
		this.brokerPort = brokerPort;
	}

	public String getCoordinatorIp() {
		return coordinatorIp;
	}

	public void setCoordinatorIp(String coordinatorIp) {
		this.coordinatorIp = coordinatorIp;
	}

	public int getCoordinatorPort() {
		return coordinatorPort;
	}

	public void setCoordinatorPort(int coordinatorPort) {
		this.coordinatorPort = coordinatorPort;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

}
