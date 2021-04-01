package org.pp.dubbo.filter;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.log4j.Logger;
import com.sequoiacap.i18n.ThreadAwareLocaleProviderFactory;

public class LocaleProviderFilter
	implements Filter
{
	private static final Logger log = Logger.getLogger(LocaleProviderFilter.class);

	public LocaleProviderFilter()
	{
		System.out.println(LocaleConsumerFilter.LOCALE_KEY);
	}
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
		throws RpcException
	{
		String langTag =
			RpcContext.getContext().getAttachment(
				LocaleConsumerFilter.LOCALE_KEY);
		if (StringUtils.isNotBlank(langTag))
		{
			try
			{
				Locale locale = Locale.forLanguageTag(langTag);

				ThreadAwareLocaleProviderFactory
					.getLocaleProvider().setLocale(locale);
			} catch(Exception e)
			{
				log.error("", e);
			}
		}

		return invoker.invoke(invocation);
	}

}
