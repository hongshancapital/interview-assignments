package com.sequoiacap.web.appframework.filter;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.jhorstmann.i18n.I18N;

import com.sequoiacap.i18n.ThreadAwareLocaleProviderFactory;

public class LocaleInterceptor
	extends HandlerInterceptorAdapter
{
	public static void ensureLocale(HttpServletRequest request)
	{
		ThreadAwareLocaleProviderFactory.getLocaleProvider().setLocale(request);
	}

	@Override
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response,
		Object handler)
		throws Exception
	{
		ensureLocale(request);
		
		Locale locale = I18N.getLocale();

		request.setAttribute("lang", locale.toLanguageTag());

		return true;
	}
	
	@Override
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler,
		@Nullable ModelAndView modelAndView)
		throws Exception
	{
		if (!ThreadAwareLocaleProviderFactory.getLocaleProvider().isNeedCookie())
			return;

		Locale locale = I18N.getLocale();

		Cookie cookie =
			new Cookie(
				ThreadAwareLocaleProviderFactory.LANGTAG,
				locale.toLanguageTag());
        
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath("/");

        response.addCookie(cookie);

        response.setLocale(locale);
	}
}
