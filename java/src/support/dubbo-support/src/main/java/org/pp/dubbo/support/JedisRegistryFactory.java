package org.pp.dubbo.support;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.registry.redis.RedisRegistry;

public class JedisRegistryFactory
	implements RegistryFactory
{
	@Override
	public Registry getRegistry(URL url)
	{
		return new JedisRegistry(url);
	}
}
