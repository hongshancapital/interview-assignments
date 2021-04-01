package org.pp.dubbo.stat;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.dubbo.config.ApplicationConfig;

public class StatReport 
	implements Serializable
{
	private static final long serialVersionUID = -3942264587705254588L;

	public static final short magicCode =(short) 0xabcd;

	private String appname;

	private String address;
	
	private String nodeId;

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String toString()
	{
		String message = 
			String.format("%s|%s|%s", appname, nodeId, address);

		return message;
	}
	
	public static StatReport valueOf(ApplicationConfig config)
	{
		StatReport report = new StatReport();

		report.setAppname(config.getName());
		report.setNodeId(StatNode.instance().getNodeId());

		try
		{
			report.setAddress(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e)
		{ }

		return report;
	}
}
