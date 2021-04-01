package com.sequoiacap.dubbo.filter;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.sequoiacap.orm.transaction.DistributedTransactionManager;

@Activate(group = Constants.CONSUMER)
public class ConsumeTransactionFilter
	implements Filter
{
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
		throws RpcException
	{
		String transactionId = DistributedTransactionManager.transactionId();

		if (StringUtils.isNoneBlank(transactionId))
		{
			RpcInvocation rpcInvocation =(RpcInvocation) invocation;

			rpcInvocation.setAttachment(
				"xtransaction-id", transactionId);
		}

		return invoker.invoke(invocation);
	}

}
