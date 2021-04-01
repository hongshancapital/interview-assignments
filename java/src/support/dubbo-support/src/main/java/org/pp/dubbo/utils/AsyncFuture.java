package org.pp.dubbo.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.log4j.Logger;
import com.sequoiacap.data.AsyncCallback;
import com.sequoiacap.data.SimpleFuture;

public class AsyncFuture<T>
	extends SimpleFuture<T>
	implements AsyncCallback<T>
{
	private static final Logger log = Logger.getLogger(AsyncFuture.class);

	protected static final int excep = 3;

	protected AsyncContext asyncContext = null;
	protected Throwable e = null;

	public AsyncFuture()
	{
		final String dubboPackname = "org.apache.dubbo.";
		final String businessPackname = "org.pp.smarket.";

		boolean async = false;

		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();

		for(int index = 3; index != stacks.length; ++index)
		{
			StackTraceElement frame = stacks[index];

			if (frame.getClassName().startsWith(dubboPackname))
			{
				log.info(String.format("%s %s->%s:%s(%s)",
					index, frame.getClassName(), frame.getMethodName(),
					frame.getFileName(), frame.getLineNumber()));

				async = true;
				break;
			} else
			if (frame.getClassName().startsWith(businessPackname))
			{
				log.info(String.format("%s %s->%s:%s(%s)",
					index, frame.getClassName(), frame.getMethodName(),
					frame.getFileName(), frame.getLineNumber()));

				async = false;
				break;
			}
		}

		if (async)
		{
			asyncContext = RpcContext.startAsync();
		}
	}

	public synchronized boolean isExcep()
	{
		return mode == excep;
	}
	
	public synchronized void set(T value)
	{
		super.set(value);

		if (asyncContext != null && asyncContext.isAsyncStarted())
		{
			asyncContext.write(value);
		}
	}

	public synchronized void throwe(Throwable e)
	{
		if (mode == cancel && mode == done)
		{
			throw new RuntimeException();
		}

		this.mode = excep;
		this.e = e;

		if (asyncContext != null && asyncContext.isAsyncStarted())
		{
			asyncContext.write(e);
		}

		this.notify();
	}

	public T waitVal()
	{
		if (asyncContext != null && asyncContext.isAsyncStarted())
			return null;

		try
		{
			return get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch(Exception e)
		{
			log.error("", e);
		}
		
		return null;
	}
	
	@Override
	public T get()
	{
		if (isDone())
			return value;

		if (isExcep())
			throw new RuntimeException(e);

		return null;
	}
	
	@Override
	public synchronized T get(long timeout, TimeUnit unit)
		throws InterruptedException, ExecutionException, TimeoutException
	{
		if (isDone())
			return value;

		if (isExcep())
			throw new RuntimeException(e);

		this.wait(unit.toMillis(timeout));

		if (!isDone())
		{
			throw new TimeoutException();
		}
		
		if (isDone())
			return value;

		if (isExcep())
			throw new RuntimeException(e);

		return null;
	}
}
