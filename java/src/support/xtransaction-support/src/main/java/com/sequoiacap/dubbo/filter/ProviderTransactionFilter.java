package com.sequoiacap.dubbo.filter;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.sequoiacap.orm.transaction.DistributedTransactionManager;

@Activate(group = Constants.PROVIDER)
public class ProviderTransactionFilter
	implements Filter
{
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
		throws RpcException
	{
		String transactionId =
			invocation.getAttachment("xtransaction-id");

		if (StringUtils.isNotBlank(transactionId))
			DistributedTransactionManager.setTransactionId(transactionId);

		Result result = invoker.invoke(invocation);

		if (StringUtils.isNotBlank(transactionId))
			DistributedTransactionManager.closeTransactionId();
		
		return result;
	}

}
