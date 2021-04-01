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

public class StatConsumerFilter
	implements Filter
{
	private static final Logger log = Logger.getLogger(StatConsumerFilter.class);

	public static final String FRAME_KEY = "org.pp.dubbo.stat.frame";

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
		throws RpcException
	{
		StatFrame frame = StatFrame.valueOf(invoker, invocation);

		long start = System.currentTimeMillis();
		
		frame.setStart(start);

		RpcContext.getContext().setAttachment(
			FRAME_KEY, JsonHelper.formatJson(frame));
		
		try
		{
			Result result = null;

			result = invoker.invoke(invocation);

			frame.setJson(result.getAttachment(FRAME_KEY));

			frame.setStatus(0);
			frame.setException(result.getException());

			return result;
		} catch(RpcException e)
		{
			frame.setException(e);
			frame.setStatus(2);

			throw e;
		} finally
		{
			if (frame.getAsync() == 0)
			{
				frame.setDonetime(System.currentTimeMillis() - start);

				StatBroadCast cast = StatBroadCast.instance();
				if (cast != null)
					cast.addFrame(frame);
			}
		}
	}

}
