package com.sequoiacap.data;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

public abstract class AdapterFuture<S, T>
	implements Future<T>, Serializable
{
	protected Future<S> source;
	protected CompletableFuture<S> waitSource;

	private S result;
	private Throwable e;

	protected abstract T adapter(S res, Throwable e);

	public AdapterFuture(Future<S> source)
	{
		this.source = source;
		
		if (source instanceof CompletableFuture)
		{
			waitSource =(CompletableFuture<S>) source;

			waitSource.whenComplete(new BiConsumer<S, Throwable>() {
				@Override
				public void accept(S t, Throwable u)
				{
					result = t;
					e = u;
	
					AdapterFuture.this.notify();
				}
			});
		}
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return source.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled()
	{
		return source.isCancelled();
	}

	@Override
	public boolean isDone()
	{
		return source.isDone();
	}

	@Override
	public T get()
		throws InterruptedException, ExecutionException
	{
		T result = null;

		if (waitSource != null)
		{
			if (this.result != null || this.e != null)
				result = adapter(this.result, this.e);

			return result;
		}

		try
		{
			if (source.isDone())
			{
				S res = source.get();

				result = adapter(res, null);
			}
		} catch(InterruptedException e)
		{
			throw e;
		} catch(ExecutionException e)
		{
			result = adapter(null, e.getCause());
		} catch(Exception e)
		{
			result = adapter(null, e);
		}

		return result;
	}

	@Override
	public T get(long timeout, TimeUnit unit)
		throws InterruptedException, ExecutionException, TimeoutException
	{
		T result = null;

		if (waitSource != null)
		{
			synchronized(this)
			{
				this.wait(unit.toMillis(timeout));

				if (this.result != null || this.e != null)
					result = adapter(this.result, this.e);
	
				return result;
			}
		}

		try
		{
			S res = source.get(timeout, unit);

			result = adapter(res, null);
		} catch(InterruptedException e)
		{
			throw e;
		} catch(ExecutionException e)
		{
			throw e;
		} catch(TimeoutException e)
		{
			throw e;
		} catch(Exception e)
		{
			result = adapter(null, e);
		}

		return result;
	}

}
