package com.sequoiacap.shorturl.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.sequoiacap.shorturl.service.SUrlManager;

public class ShortUrlTask
{
	@Autowired
	private SUrlManager sUrlManager;

	public void updateStatus()
	{
		sUrlManager.refreshStatus();
	}
}
