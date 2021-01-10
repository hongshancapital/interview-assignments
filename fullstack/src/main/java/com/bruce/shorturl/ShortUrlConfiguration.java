package com.bruce.shorturl;

import com.bruce.shorturl.storage.IShortUrlStorage;
import com.bruce.shorturl.storage.impl.ShortUrlRedisStorageImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author bruce
 */
@Configuration
public class ShortUrlConfiguration {


	@Primary
	@Bean
	public IShortUrlStorage shortUrlRediStorage(){
		return new ShortUrlRedisStorageImpl();
	}

}
