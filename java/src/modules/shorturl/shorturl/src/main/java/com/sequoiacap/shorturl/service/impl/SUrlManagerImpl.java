package com.sequoiacap.shorturl.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sequoiacap.exception.BusinessException;
import com.sequoiacap.shorturl.model.SUrl;
import com.sequoiacap.shorturl.model.SUrl.Status;
import com.sequoiacap.shorturl.model.SUrl.Type;
import com.sequoiacap.shorturl.repository.SUrlRepository;
import com.sequoiacap.shorturl.service.SUrlManager;
import com.sequoiacap.utils.CryptoHelper;
import com.sequoiacap.utils.Utils;
import com.sequoiacap.utils.service.AbstractManagerImpl;
import com.sequoiacap.errorcode.ErrorCode;
import com.sequoiacap.errorcode.ErrorCodeStr;

import net.jhorstmann.i18n.I18N;

@Service("sUrlManager")
public class SUrlManagerImpl
    extends AbstractManagerImpl<SUrl, Long>
    implements SUrlManager
{
    private static final Logger log = Logger.getLogger(SUrlManagerImpl.class);
    
    @Autowired
    protected SUrlRepository sUrlRepository;

    @Resource(name = "redisTemplate")
    protected ValueOperations<String, SUrl> urlVals;

    @Value("${surl.cache:3600}")
    protected Integer cacheTime;

    @Value("${surl.max:65535}")
    protected Integer maxLength;
    
    @Value("${surl.length:8}")
    protected Integer surlLength;
    
    @Value("${surl.tries:7}")
    protected Integer maxTires;
    
    @Value("${surl.expire:3}")
    protected Integer expire;
    
    public void afterPropertiesSet() throws Exception {
        setRepository(sUrlRepository);
    }

	@Override
	public SUrl set(String url, Type type, String ip)
		throws BusinessException
	{
		if (StringUtils.isBlank(url))
		{
			throw new BusinessException(
				ErrorCode.INVALID_URL,
				I18N.tr(ErrorCodeStr.STR_INVALID_URL));
		}
		
		if (type == null)
		{
			throw new BusinessException(
				ErrorCode.INVALID_TYPE,
				I18N.tr(ErrorCodeStr.STR_INVALID_TYPE));
		}

		if (url.length() >= maxLength.intValue())
		{
			throw new BusinessException(
				ErrorCode.TOO_LONG_URL,
				I18N.tr(ErrorCodeStr.STR_TOO_LONG_URL, maxLength));
		}

		String md5 = "md5:" + CryptoHelper.digest(url, "utf-8", "md5");

		SUrl surl = new SUrl(), ret = null;

		surl.setUrl(url);
		surl.setType(type);
		surl.setIp(ip);
		surl.setTimestamp(Utils.now());

		/** 尝试抢占缓存。如果失败，说明已有相同请求到达 */
		if (!urlVals.setIfAbsent(md5, surl, cacheTime.intValue(), TimeUnit.SECONDS))
		{
			surl = urlVals.get(md5);

			/** 如果状态已入库，则直接返回 */
			if (surl != null && surl.getStatus() != null)
			{
				/** 如果新类型升级了，则修改记录 */
				if (surl.getType().ordinal() < type.ordinal())
				{
					SUrlManager pthis =(SUrlManager) AopContext.currentProxy();

					surl.setType(type);
					surl.setStatus(SUrl.Status.normal);

					pthis.save(surl);

					urlVals.set(md5, surl, cacheTime.intValue(), TimeUnit.SECONDS);
					urlVals.set("surl:" + surl.getShortUrl(), surl, cacheTime.intValue(), TimeUnit.SECONDS);
				}

				return surl;
			}

			throw new BusinessException(
				ErrorCode.MANY_REQUEST_SAME_URL,
				I18N.tr(ErrorCodeStr.STR_MANY_REQUEST_SAME_URL));
		}

		SUrlManager pthis =(SUrlManager) AopContext.currentProxy();

		int tryAgain = 0, max = maxTires.intValue(), length = surlLength.intValue();

		Random random = new Random();
		char[] sb = new char[length];

		while(tryAgain++ < max)
		{
			String shortUrl = Utils.getRandomStringByLength(length, random, sb);

			if (!urlVals.setIfAbsent("surl:" + shortUrl, surl, cacheTime.intValue(), TimeUnit.SECONDS))
				continue;

			if ((ret = pthis.keepit(shortUrl, surl)) == null)
				continue;

			break;
		}

		if (ret == null)
		{
			throw new BusinessException(
				ErrorCode.FAILED_SURL,
				I18N.tr(ErrorCodeStr.STR_FAILED_SURL));
		}

		urlVals.set(md5, ret, cacheTime.intValue(), TimeUnit.SECONDS);
		urlVals.set("surl:" + ret.getShortUrl(), ret, cacheTime.intValue(), TimeUnit.SECONDS);

		return ret;
	}

	@Override
	public SUrl get(String shortUrl)
	{
		String key = "surl:" + shortUrl;
		
		SUrl surl = urlVals.get(key);
		if (surl != null && surl.getStatus() != null)
			return surl;

		List<SUrl> rows =
			sUrlRepository.findShorturl(
				shortUrl, SUrl.Status.normal,
				PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "id")));
		if (CollectionUtils.isEmpty(rows))
			return null;

		surl = rows.get(0);
		
		return surl;
	}

	@Override
	@Transactional
	public SUrl keepit(String shortUrl, SUrl surl)
	{
		if (sUrlRepository.countShorturl(shortUrl, SUrl.Status.normal) > 0)
			return null;

		surl.setShortUrl(shortUrl);

		/** 如果只是临时的，则只在缓存中有效 */
		surl.setStatus(Type.temporary == surl.getType()? Status.invalid: Status.normal);

		surl = save(surl);

		log.info(String.format("generatie %s to %s", shortUrl, surl));

		return surl;
	}

	@Override
	@Transactional
	public int refreshStatus()
	{
		Timestamp now = Utils.now();
		
		Timestamp thatDay =
			Utils.offsetTimestamp(
				now, -expire.intValue(), Calendar.DATE); 

		return sUrlRepository.refreshStatus(
			SUrl.Type.short_period, SUrl.Status.normal,
			thatDay, SUrl.Status.invalid);
	}
}

