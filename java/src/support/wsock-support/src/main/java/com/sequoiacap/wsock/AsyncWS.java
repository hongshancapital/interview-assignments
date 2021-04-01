package com.sequoiacap.wsock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator.OverflowStrategy;

import com.sequoiacap.utils.JsonHelper;

public class AsyncWS
{
	private static final Logger log = Logger.getLogger(AsyncWS.class);

	private HashMap<String, WebSocketSession> sessionMap =
		new HashMap<String, WebSocketSession>();

	private HashSet<AsyncFutureMessage> futures =
		new HashSet<AsyncFutureMessage>();

	@Resource(name = "redisTemplate")
	protected ListOperations<String, String> listOpts;

	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${asyncws.time:5000}")
	private Integer sendTimeLimit;
	
	@Value("${asyncws.buffer:102400}")
	private Integer bufferSizeLimit;

	@Value("${asyncws.keyformat:'asyncws-%s'}")
	private String keyformat;

	@Value("${asyncws.keytimeout:180000}")
	private Integer keytimeout;

	public synchronized
		void attachWebSocket(String key, WebSocketSession session)
	{
		sessionMap.put(key,
			new ConcurrentWebSocketSessionDecorator(
				session, sendTimeLimit, bufferSizeLimit,
				OverflowStrategy.DROP));
	}

	public synchronized void detachWebSocket(String key)
	{
		WebSocketSession session = sessionMap.get(key);
		
		if (session != null)
		{
			try
			{
				session.close();
			} catch(Exception e)
			{
				log.error("", e);
			}
			
			sessionMap.remove(key);
		}
	}

	public synchronized void scan()
	{
		LinkedList<AsyncFutureMessage> removes =
			new LinkedList<AsyncFutureMessage>();

		for(AsyncFutureMessage message: futures)
		{
			if (message.isCancelled())
			{
				removes.add(message);
				continue;
			}
			
			if (message.isDone())
			{
				try
				{
					send(
						message.getSessionId(),
						message.getType(), message.get());
				} catch(Exception e)
				{
					log.error("", e);
				}

				removes.add(message);
				continue;
			}
		}
	}
	
	public void send()
	{
		for(String sessionId: sessionMap.keySet())
		{
			String listId = String.format(keyformat, sessionId);

			String message = listOpts.leftPop(listId);
			if (message != null)
			{
				WebSocketSession session = sessionMap.get(sessionId);

				try
				{
					session.sendMessage(new TextMessage(message));
				} catch(Exception e)
				{
					log.error("", e);
				}

				expire(listId);
			}
		}
	}

	public <T> void send(String sessionId, String messageType, T object)
	{
		String listId = String.format(keyformat, sessionId);

		listOpts.rightPush(listId,
			JsonHelper.formatJson(new AsyncMessage<T>(messageType, object)));

		expire(listId);
	}
	
	public <T> void send(String messageType, T object)
	{
		String sessionId =
			RequestContextHolder.getRequestAttributes().getSessionId();

		send(sessionId, messageType, object);
	}

	public synchronized <T> void send(String messageType, Future<T> future)
	{
		String sessionId =
			RequestContextHolder.getRequestAttributes().getSessionId();

		if (future == null)
			return;

		futures.add(new AsyncFutureMessage<T>(sessionId, messageType, future));
	}

	private void expire(String id)
	{
		redisTemplate.expire(id, keytimeout, TimeUnit.MILLISECONDS);
	}
}
