package org.pp.dubbo.stat;

import java.io.Serializable;

import com.sequoiacap.utils.Utils;

public class StatNode
	implements Serializable
{
	private static final long serialVersionUID = -8162879841171793241L;

	private String nodeId;

	private ThreadLocal<String> callId = new ThreadLocal<String>();
	private ThreadLocal<String> callPath = new ThreadLocal<String>();

	public StatNode()
	{
		nodeId = Utils.getRandomStringByLength(16);
	}

	public String getNodeId()
	{
		return nodeId;
	}

	public String getCallId()
	{
		String id = callId.get();

		if (id == null)
			return Utils.getRandomStringByLength(16);

		return id;
	}

	public void setCallId(String callId)
	{
		this.callId.set(callId);
	}

	public String getCallPath()
	{
		String path = callPath.get();

		if (path == null)
			return "";

		return path;
	}

	public void setCallPath(String callPath)
	{
		this.callPath.set(callPath);
	}

	private static volatile StatNode statNode = null;

	public static synchronized StatNode instance()
	{
		if (statNode == null)
			statNode = new StatNode();

		return statNode;
	}
}
