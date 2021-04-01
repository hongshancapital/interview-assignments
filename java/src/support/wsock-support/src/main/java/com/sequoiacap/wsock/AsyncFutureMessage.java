package com.sequoiacap.wsock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncFutureMessage<T>
	implements Future<T>
{
	private String sessionId;
	private String type;
	private Future<T> future;

	public AsyncFutureMessage(
		String sessionId, String type, Future<T> future)
	{
		this.sessionId = sessionId;
		this.type = type;
		this.future = future;
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return false;
	}

	@Override
	public boolean isCancelled()
	{
		return future.isCancelled();
	}
	@Override
	public boolean isDone()
	{
		return future.isDone();
	}
	@Override
	public T get()
		throws InterruptedException, ExecutionException
	{
		return future.get();
	}
	@Override
	public T get(long timeout, TimeUnit unit)
		throws InterruptedException, ExecutionException, TimeoutException
	{
		return future.get(timeout, unit);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Future<T> getFuture() {
		return future;
	}

	public void setFuture(Future<T> future) {
		this.future = future;
	}
}
