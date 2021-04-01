package org.pp.dubbo.filter;

import java.util.Locale;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import net.jhorstmann.i18n.I18N;

public class LocaleConsumerFilter
	implements Filter
{
	public static final String LOCALE_KEY = "org.pp.dubbo.locale";

	public LocaleConsumerFilter()
	{
		System.out.println(LOCALE_KEY);
	}
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation inv)
		throws RpcException
	{
		Locale locale = I18N.getLocale();

		RpcContext.getContext().setAttachment(LOCALE_KEY, locale.toLanguageTag());

		return invoker.invoke(inv);
	}

}
