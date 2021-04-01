package org.pp.dubbo.stat;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.InvokeMode;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.protocol.dubbo.DecodeableRpcInvocation;
import com.sequoiacap.utils.JsonHelper;

public class StatFrame
	implements Serializable
{
	private static final long serialVersionUID = -938346226373203331L;

	/** 接口名称 及 方法名称 */
	private String interfaceName;
	private String methodName;

	private String nodeId, nextNodeId;
	private String callId;
	private String callPath;

	/** 调用者地址 */
	private String callerAddress;
	/** 服务地址 */
	private String calleeAddress;

	/** 服务完成时间 */
	private long duration = 0;
	/** 调用完成时间 */
	private long donetime = 0;

	private long start = 0;
	
	/** 是否异步调用 */
	private int async = 0;
	
	/** 调用成功状态, 0 成功, 1 业务失败, 2 通讯失败 */
	private int status = 0;
	/** 异常名称 */
	private String exception;

	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getCallPath() {
		return callPath;
	}
	public void setCallPath(String callPath) {
		this.callPath = callPath;
	}
	public String getCallerAddress() {
		return callerAddress;
	}
	public void setCallerAddress(String callerAddress) {
		this.callerAddress = callerAddress;
	}
	public String getCalleeAddress() {
		return calleeAddress;
	}
	public void setCalleeAddress(String calleeAddress) {
		this.calleeAddress = calleeAddress;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getDonetime() {
		return donetime;
	}
	public void setDonetime(long donetime) {
		this.donetime = donetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public void setException(Throwable e)
	{
		if (e == null)
			return;

		if (e.getCause() != null)
		{
			exception = e.getCause().getClass().getName();
		} else
		exception = e.getClass().getName();
		
		status = 1;
	}

	public StatFrame setJson(String json)
	{
		if (StringUtils.isBlank(json))
			return this;
		
		StatFrame copy = JsonHelper.convert(json, StatFrame.class);
		if (copy == null)
			return this;

		this.setCallerAddress(copy.getCallerAddress());
		this.setCalleeAddress(copy.getCalleeAddress());
		this.setDuration(copy.getDuration());

		return this;
	}
	
	public String toString()
	{
		String message = 
			String.format("%s|%s|%s|%s|%d|%d|%d|%d|%s|%s|%s|%s|%d|%s",
				callId, callPath, nodeId, nextNodeId,
				start, async, duration, donetime,
				interfaceName, methodName,
				callerAddress, calleeAddress,
				status,
				exception);
		
		return message;
	}
	
	public static StatFrame valueOf(String json, Invocation invocation)
	{
		StatNode node = StatNode.instance();

		StatFrame frame = JsonHelper.convert(json, StatFrame.class);
		if (frame == null)
			return null;

		frame.setNextNodeId(node.getNodeId());
		node.setCallId(frame.getCallId());
		node.setCallPath(frame.getCallPath());

		if (invocation instanceof DecodeableRpcInvocation)
		{
			try
			{
				Field field =
					DecodeableRpcInvocation.class.getDeclaredField("channel");
				
				field.setAccessible(true);
				
				Channel channel =(Channel) field.get(invocation);
				
				frame.setCallerAddress(channel.getRemoteAddress().getAddress().getHostAddress());
				frame.setCalleeAddress(channel.getLocalAddress().getAddress().getHostAddress());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return frame;
	}
	public static StatFrame valueOf(Invoker<?> invoker, Invocation invocation)
	{
		StatNode node = StatNode.instance();

		StatFrame frame = new StatFrame();

		frame.setNodeId(node.getNodeId());
		frame.setCallId(node.getCallId());
		frame.setCallPath(node.getCallPath() + "." + node.getNodeId());

		frame.setInterfaceName(invoker.getInterface().getName());
		frame.setMethodName(invocation.getMethodName());

		if (invocation instanceof RpcInvocation &&
			((RpcInvocation) invocation).getInvokeMode() == InvokeMode.ASYNC)
			frame.setAsync(1);

		return frame;
	}
	public int getAsync() {
		return async;
	}
	public void setAsync(int async) {
		this.async = async;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public String getNextNodeId() {
		return nextNodeId;
	}
	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}
}
