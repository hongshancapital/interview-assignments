package com.sequoiacap.session;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer.CookieValue;

public class TokenHttpSessionIdResolver
	implements HttpSessionIdResolver
{
	private static final String WRITTEN_SESSION_ID_ATTR =
		TokenHttpSessionIdResolver.class.getName().concat(".WRITTEN_SESSION_ID_ATTR");

	private CookieSerializer cookieSerializer = new DefaultCookieSerializer();

	@Override
	public List<String> resolveSessionIds(HttpServletRequest request)
	{
		ArrayList<String> sessionIds = new ArrayList<String>();

		sessionIds.addAll(this.cookieSerializer.readCookieValues(request));

		String token = request.getParameter("token");
		if (StringUtils.isNoneBlank(token))
		{
			sessionIds.add(token);
		}

		token = request.getHeader("X-TOKEN");
		if (StringUtils.isNoneBlank(token))
		{
			sessionIds.add(token);
		}

		return sessionIds;
	}

	@Override
	public void setSessionId(
		HttpServletRequest request, HttpServletResponse response,
		String sessionId)
	{
		if (sessionId.equals(request.getAttribute(WRITTEN_SESSION_ID_ATTR)))
		{
			return;
		}

		request.setAttribute(WRITTEN_SESSION_ID_ATTR, sessionId);

		this.cookieSerializer
			.writeCookieValue(new CookieValue(request, response, sessionId));
	}

	@Override
	public void expireSession(HttpServletRequest request, HttpServletResponse response)
	{
		this.cookieSerializer.writeCookieValue(
			new CookieValue(request, response, ""));
	}

	/**
	 * Sets the {@link CookieSerializer} to be used.
	 *
	 * @param cookieSerializer the cookieSerializer to set. Cannot be null.
	 */
	public void setCookieSerializer(CookieSerializer cookieSerializer)
	{
		if (cookieSerializer == null)
		{
			throw new IllegalArgumentException("cookieSerializer cannot be null");
		}

		this.cookieSerializer = cookieSerializer;
	}
}
