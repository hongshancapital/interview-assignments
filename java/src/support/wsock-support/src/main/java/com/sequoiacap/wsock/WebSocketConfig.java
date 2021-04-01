package com.sequoiacap.wsock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration("webSocketConfig")
public class WebSocketConfig
	implements WebSocketConfigurer
{
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
	{
		registry.addHandler(newAsyncWebSocket(), "/websock/asyncwsock.fn");
	}

	
	@Bean
	public AsyncWebSocket newAsyncWebSocket()
	{
		return new AsyncWebSocket();
	}
}
