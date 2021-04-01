package org.pp.dubbo.stat.filter;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.log4j.Logger;
import org.pp.dubbo.stat.StatBroadCast;
import org.pp.dubbo.stat.StatFrame;
import com.sequoiacap.utils.JsonHelper;

public class StatProviderFilter
	implements Filter
{
	private static final Logger log = Logger.getLogger(StatConsumerFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
		throws RpcException
	{
		StatFrame frame = StatFrame.valueOf(
			RpcContext.getContext().getAttachment(
				StatConsumerFilter.FRAME_KEY), invocation);

		long start = System.currentTimeMillis();
		
		Result result = invoker.invoke(invocation);

		frame.setDuration(System.currentTimeMillis() - start);

		result.setAttachment(
			StatConsumerFilter.FRAME_KEY, JsonHelper.formatJson(frame));

		if (frame.getAsync() == 1)
		{
			StatBroadCast cast = StatBroadCast.instance();
			if (cast != null)
				cast.addFrame(frame);
		}
		
		return result;
	}

}
