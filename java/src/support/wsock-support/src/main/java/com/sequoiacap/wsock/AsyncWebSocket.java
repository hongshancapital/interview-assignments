package com.sequoiacap.wsock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class AsyncWebSocket
	extends TextWebSocketHandler
{
	public static final String SESSIONKEY = "session-id";

	@Autowired
	private AsyncWS asyncWS;
	
	@Override
	public void afterConnectionEstablished(final WebSocketSession session)
		throws Exception
	{
		UriComponents uri =
			UriComponentsBuilder.fromUriString(
				session.getUri().toString()).build();

		if (uri.getQueryParams().containsKey(SESSIONKEY))
		{
			String sessionId = uri.getQueryParams().get(SESSIONKEY).get(0);

			session.getAttributes().put(SESSIONKEY, sessionId);
		} else
		{
			String sessionId = session.getId();

			session.getAttributes().put(SESSIONKEY, sessionId);
		}

		asyncWS.attachWebSocket(
			String.valueOf(session.getAttributes().get(SESSIONKEY)), session);
	}

	@Override
	public void afterConnectionClosed(
		WebSocketSession session, CloseStatus status)
		throws Exception
	{
		asyncWS.detachWebSocket(
			String.valueOf(session.getAttributes().get(SESSIONKEY)));
	}
}
