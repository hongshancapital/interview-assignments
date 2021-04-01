package com.sequoiacap.shorturl.service;

import com.sequoiacap.exception.BusinessException;
import com.sequoiacap.shorturl.model.SUrl;
import com.sequoiacap.utils.service.GenericManager;

public interface SUrlManager
    extends GenericManager<SUrl, Long>
{
	public SUrl keepit(String shortUrl, SUrl surl);

	public SUrl set(String url, SUrl.Type type, String ip)
		throws BusinessException;
	
	public SUrl get(String shortUrl);
	
	public int refreshStatus();
}
