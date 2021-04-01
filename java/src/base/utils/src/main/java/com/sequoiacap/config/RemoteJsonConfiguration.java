package com.sequoiacap.config;

import java.time.Duration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;

public class RemoteJsonConfiguration
{
	private String url;
	private long interval = -1;

	private RemoteJsonConfigurer configurer = new RemoteJsonConfigurer(); 

	public class RefreshJsonTask
		implements InitializingBean
	{
		@Autowired
		private TaskScheduler tasksScheduler;

		@Override
		public void afterPropertiesSet()
			throws Exception
		{
			if (interval != -1 && tasksScheduler != null)
			{
				tasksScheduler.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run()
					{
						configurer.refresh();
					}
				}, Duration.ofSeconds(interval));
			}			
		}		
	}

	@Bean
	public RemoteJsonConfigurer remoteJsonCongiurer()
	{
		configurer.setUrl(url);

		return configurer;
	}

	@Bean
	public RefreshJsonTask jsonTask()
	{
		return new RefreshJsonTask();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}
