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

	/*
	private String brokerNodeIp;
	private int brokerPort;

	private String coordinatorIp;
	private int coordinatorPort;

	private String proxyIp;
	private String proxyPort;
	*/

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
/*
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
	*/

	
	/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((brokerNodeIp == null) ? 0 : brokerNodeIp.hashCode());
		result = prime * result + brokerPort;
		result = prime * result
				+ ((coordinatorIp == null) ? 0 : coordinatorIp.hashCode());
		result = prime * result + coordinatorPort;
		result = prime * result + ((proxyIp == null) ? 0 : proxyIp.hashCode());
		result = prime * result
				+ ((proxyPort == null) ? 0 : proxyPort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DruidManagedConnectionFactory other = (DruidManagedConnectionFactory) obj;
		if (brokerNodeIp == null) {
			if (other.brokerNodeIp != null)
				return false;
		} else if (!brokerNodeIp.equals(other.brokerNodeIp))
			return false;
		if (brokerPort != other.brokerPort)
			return false;
		if (coordinatorIp == null) {
			if (other.coordinatorIp != null)
				return false;
		} else if (!coordinatorIp.equals(other.coordinatorIp))
			return false;
		if (coordinatorPort != other.coordinatorPort)
			return false;
		if (proxyIp == null) {
			if (other.proxyIp != null)
				return false;
		} else if (!proxyIp.equals(other.proxyIp))
			return false;
		if (proxyPort == null) {
			if (other.proxyPort != null)
				return false;
		} else if (!proxyPort.equals(other.proxyPort))
			return false;
		return true;
	}

*/
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	
	}
	
	
	@Override
	public int hashCode(){
		return super.hashCode();
	}
	
	
	
}
