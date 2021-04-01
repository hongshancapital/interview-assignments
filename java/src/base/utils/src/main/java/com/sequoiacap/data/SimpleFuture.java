package com.sequoiacap.data;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SimpleFuture<T>
	implements Future<T>, Serializable
{
	protected static final int pending = 0;
	protected static final int done = 1;
	protected static final int cancel = 2;

	protected volatile int mode = 0;
	protected volatile T value = null;

	@Override
	public synchronized boolean cancel(boolean mayInterruptIfRunning)
	{
		if (mode == pending)
		{
			mode = cancel;
			return true;
		}
			
		return false;
	}

	@Override
	public synchronized boolean isCancelled()
	{
		return mode == cancel;
	}

	@Override
	public synchronized boolean isDone()
	{
		return mode == done;
	}

	public synchronized void set(T value)
	{
		if (mode == cancel && mode == done)
		{
			throw new RuntimeException();
		}

		this.mode = done;
		this.value = value;

		this.notify();
	}

	@Override
	public T get()
	{
		return value;
	}

	@Override
	public synchronized T get(long timeout, TimeUnit unit)
		throws InterruptedException, ExecutionException, TimeoutException
	{
		if (isDone())
			return value;

		this.wait(unit.toMillis(timeout));

		if (!isDone())
		{
			throw new TimeoutException();
		}

		return value;
	}
}
