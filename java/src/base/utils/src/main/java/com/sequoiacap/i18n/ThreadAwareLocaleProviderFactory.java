package com.sequoiacap.i18n;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.jhorstmann.i18n.LocaleProvider;
import net.jhorstmann.i18n.LocaleProviderFactory;

public class ThreadAwareLocaleProviderFactory
	extends LocaleProviderFactory
{
	private static final Logger log = Logger.getLogger(ThreadAwareLocaleProviderFactory.class);

	public static final String LANGTAG = "lang-tag";
	
	public static String getCookie(String name, Cookie[] cookies)
	{
		String value = null;

		if (cookies == null)
			return value;

		for(Cookie cookie: cookies)
		{
			if (name.equalsIgnoreCase(cookie.getName()))
			{
				value = cookie.getValue();
				break;
			}
		}

		return value;
	}

	public static class ThreadAwareLocaleProvider
		implements LocaleProvider
	{
		private ThreadLocal<Locale> threadLocale = new ThreadLocal<Locale>();
		private ThreadLocal<Boolean> needCookie = new ThreadLocal<Boolean>();

		@Override
        public Locale getLocale()
        {
        	Locale locale = threadLocale.get();
        	if (locale != null)
        		return locale;

            return Locale.getDefault();
        }

		public boolean isNeedCookie()
		{
			return BooleanUtils.isTrue(needCookie.get());
		}
		
        public void setLocale(Locale locale)
        {
        	threadLocale.set(locale);
        }

        public void setLocale(HttpServletRequest request)
        {
        	needCookie.set(false);

        	Locale locale = request.getLocale();

    		String langTag = request.getParameter("lang");
    		
    		if (StringUtils.isBlank(langTag))
    		{
    			HttpSession session = request.getSession();
    			if (session != null)
    			{
    				langTag =(String) session.getAttribute(LANGTAG);
    			}
    		}
    		
    		if (StringUtils.isBlank(langTag))
    		{
    			langTag = request.getHeader("language");
    		}
    		
    		if (StringUtils.isBlank(langTag))
    		{
    			langTag = getCookie(LANGTAG, request.getCookies());
    		}

    		if (StringUtils.isNotBlank(langTag))
    		{
    			try
    			{
    				locale = Locale.forLanguageTag(langTag);
    			} catch(Exception e)
    			{
    				log.error("", e);
    			}

    			request.setAttribute("langtag", langTag);
    			request.getSession(true).setAttribute(LANGTAG, langTag);
    			
    			needCookie.set(true);
    		}

    		setLocale(locale);
        }
    }

	private static final ThreadAwareLocaleProvider PROVIDER = new ThreadAwareLocaleProvider();

	public static ThreadAwareLocaleProvider getLocaleProvider()
	{
		return PROVIDER;
	}

	@Override
	public boolean isEnvironmentSupported() {
		return true;
	}

	@Override
	public LocaleProvider newLocaleProvider() {
		return PROVIDER;
	}
}
