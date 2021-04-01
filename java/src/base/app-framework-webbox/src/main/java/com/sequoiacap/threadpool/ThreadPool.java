package com.sequoiacap.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ThreadPool
	implements FactoryBean<ThreadPoolExecutor>, InitializingBean
{
	private ThreadPoolExecutor executor;

	public void afterPropertiesSet()
		throws Exception
	{
		executor = new ThreadPoolExecutor(
			200, 200, 10, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(),
			new ThreadPoolExecutor.DiscardOldestPolicy());
	}

	public ThreadPoolExecutor getObject()
		throws Exception
	{
		return executor;
	}

	public Class<?> getObjectType() {
		return ThreadPoolExecutor.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
