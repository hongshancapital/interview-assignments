package org.pp.dubbo.filter;

import java.util.concurrent.Future;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.InvokeMode;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;

public class AsyncControlFilter
	implements Filter
{
	public static final String ASYNC_KEY = "org.pp.dubbo.async";

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
		throws RpcException
	{
		RpcContext rpcContext = RpcContext.getContext();

		if ("async".equals(rpcContext.getAttachment(ASYNC_KEY)))
		{
			invocation = new RpcInvocation(invocation) {
				public InvokeMode getInvokeMode() {
					return InvokeMode.ASYNC;
				}
			};
		}

		return invoker.invoke(invocation);
	}

	public static void setAsyncMode()
	{
		RpcContext rpcContext = RpcContext.getContext();

		rpcContext.setAttachment(ASYNC_KEY, "async");
	}

	public static <T> Future<T> returnFuture()
	{
		RpcContext rpcContext = RpcContext.getContext();

		return rpcContext.getFuture();
	}
}
