package org.pp.dubbo.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.common.utils.UrlUtils;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.redis.RedisRegistry;
import org.apache.dubbo.registry.support.FailbackRegistry;
import org.apache.dubbo.rpc.RpcException;

public class JedisRegistry
	extends FailbackRegistry
{
    private static final Logger logger = LoggerFactory.getLogger(JedisRegistry.class);

    private static final int DEFAULT_REDIS_PORT = 6379;

    private final static String DEFAULT_ROOT = "dubbo";

    private final ScheduledExecutorService expireExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("DubboRegistryExpireTimer", true));

    private final ScheduledFuture<?> expireFuture;
    
    private final String root;

    private final Map<String, JedisPool> jedisPools = new ConcurrentHashMap<String, JedisPool>();

    private final ConcurrentMap<String, Notifier> notifiers =
    	new ConcurrentHashMap<String, Notifier>();
    
    private final int reconnectPeriod;

    private final int expirePeriod;
    
    private volatile boolean admin = false;
    
    private boolean replicate;

	public JedisRegistry(URL url)
	{
		super(url);
		
		if (url.isAnyHost())
    		throw new IllegalStateException("registry address == null");

		GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setTestOnBorrow(url.getParameter("test.on.borrow", true));
        config.setTestOnReturn(url.getParameter("test.on.return", false));
        config.setTestWhileIdle(url.getParameter("test.while.idle", false));

        String password = null;
        
        if (url.getParameter("max.idle", 0) > 0)
            config.setMaxIdle(url.getParameter("max.idle", 0));
        if (url.getParameter("min.idle", 0) > 0)
            config.setMinIdle(url.getParameter("min.idle", 0));
        if (url.getParameter("num.tests.per.eviction.run", 0) > 0)
            config.setNumTestsPerEvictionRun(url.getParameter("num.tests.per.eviction.run", 0));
        if (url.getParameter("time.between.eviction.runs.millis", 0) > 0)
            config.setTimeBetweenEvictionRunsMillis(url.getParameter("time.between.eviction.runs.millis", 0));
        if (url.getParameter("min.evictable.idle.time.millis", 0) > 0)
            config.setMinEvictableIdleTimeMillis(url.getParameter("min.evictable.idle.time.millis", 0));
        if (StringUtils.isNotBlank(url.getParameter("password")))
        	password = url.getParameter("password");
        
        String cluster = url.getParameter("cluster", "failover");
        if (! "failover".equals(cluster) && ! "replicate".equals(cluster))
        {
        	throw new IllegalArgumentException("Unsupported redis cluster: " +
        		cluster + ". The redis cluster only supported failover or replicate.");
        }

        replicate = "replicate".equals(cluster);
        
        List<String> addresses = new ArrayList<String>();
        addresses.add(url.getAddress());

        String[] backups =
        	url.getParameter(Constants.BACKUP_KEY, new String[0]);
        
        if (backups != null && backups.length > 0)
        {
            addresses.addAll(Arrays.asList(backups));
        }

        for(String address: addresses)
        {
            int i = address.indexOf(':');

            String host;
            int port;
            
            if (i > 0)
            {
                host = address.substring(0, i);
                port = Integer.parseInt(address.substring(i + 1));
            } else
            {
                host = address;
                port = DEFAULT_REDIS_PORT;
            }
            this.jedisPools.put(address, new JedisPool(config, host, port, 
                url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT),
                password));
        }
        
        this.reconnectPeriod =url.getParameter(
    		Constants.REGISTRY_RECONNECT_PERIOD_KEY,
    		Constants.DEFAULT_REGISTRY_RECONNECT_PERIOD);
        
        String group = url.getParameter(Constants.GROUP_KEY, DEFAULT_ROOT);
        
        if (!group.startsWith(Constants.PATH_SEPARATOR))
        {
            group = Constants.PATH_SEPARATOR + group;
        }

        if (!group.endsWith(Constants.PATH_SEPARATOR))
        {
            group = group + Constants.PATH_SEPARATOR;
        }
        this.root = group;
        
        this.expirePeriod = url.getParameter(
        	Constants.SESSION_TIMEOUT_KEY, Constants.DEFAULT_SESSION_TIMEOUT);
        this.expireFuture = expireExecutor.scheduleWithFixedDelay(new Runnable() {
            public void run()
            {
                try
                {
                    deferExpired(); // 延长过期时间
                } catch (Throwable t)
                { // 防御性容错
                    logger.error("Unexpected exception occur at defer expire time, cause: " + t.getMessage(), t);
                }
            }
        }, expirePeriod / 2, expirePeriod / 2, TimeUnit.MILLISECONDS);
	}
	
	private void deferExpired()
	{
        for(Map.Entry<String, JedisPool> entry: jedisPools.entrySet())
        {
            JedisPool jedisPool = entry.getValue();
            try
            {
                Jedis jedis = jedisPool.getResource();
                try
                {
                    for (URL url: new HashSet<URL>(getRegistered()))
                    {
                        if (url.getParameter(Constants.DYNAMIC_KEY, true))
                        {
                            String key = toCategoryPath(url);
                            if (jedis.hset(key, url.toFullString(),
                            		String.valueOf(System.currentTimeMillis() + expirePeriod)) == 1)
                            {
                                jedis.publish(key, Constants.REGISTER);
                            }
                        }
                    }

                    if (admin)
                    {
                        clean(jedis);
                    }
                    
                    if (!replicate)
                    {
                    	break;//  如果服务器端已同步数据，只需写入单台机器
                    }
                } finally
                {
                    jedisPool.returnResource(jedis);
                }
            } catch(Throwable t)
            {
                logger.warn("Failed to write provider heartbeat to redis registry. registry: " +
                	entry.getKey() + ", cause: " + t.getMessage(), t);
            }
        }
    }
    
    // 监控中心负责删除过期脏数据
    private void clean(Jedis jedis)
    {
        Set<String> keys = jedis.keys(root + Constants.ANY_VALUE);
        if (keys != null && keys.size() > 0)
        {
            for(String key: keys)
            {
                Map<String, String> values = jedis.hgetAll(key);
                
                if (values != null && values.size() > 0)
                {
                    boolean delete = false;
                    long now = System.currentTimeMillis();

                    for(Map.Entry<String, String> entry: values.entrySet())
                    {
                        URL url = URL.valueOf(entry.getKey());
                        if (url.getParameter(Constants.DYNAMIC_KEY, true))
                        {
                            long expire = Long.parseLong(entry.getValue());
                            if (expire < now)
                            {
                                jedis.hdel(key, entry.getKey());
                                delete = true;
                                if (logger.isWarnEnabled())
                                {
                                    logger.warn("Delete expired key: " + key +
                                    	" -> value: " + entry.getKey() +
                                    	", expire: " + new Date(expire) +
                                    	", now: " + new Date(now));
                                }
                            }
                        }
                    }

                    if (delete)
                    {
                        jedis.publish(key, Constants.UNREGISTER);
                    }
                }
            }
        }
    }

    public boolean isAvailable()
    {
        for(JedisPool jedisPool: jedisPools.values())
        {
            try
            {
                Jedis jedis = jedisPool.getResource();
                try
                {
                	if (jedis.isConnected())
                	{
                        return true; // 至少需单台机器可用
                    }
                } finally
                {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t)
            {
            	logger.warn(t.getMessage(), t);
            }
        }
        return false;
    }

    @Override
    public void destroy()
    {
        super.destroy();
        
        try
        {
            expireFuture.cancel(true);
        } catch (Throwable t)
        {
            logger.warn(t.getMessage(), t);
        }
        
        try
        {
            for(Notifier notifier: notifiers.values())
            {
                notifier.shutdown();
            }
        } catch (Throwable t)
        {
            logger.warn(t.getMessage(), t);
        }
        
        for(Map.Entry<String, JedisPool> entry: jedisPools.entrySet())
        {
            JedisPool jedisPool = entry.getValue();
            try
            {
                jedisPool.destroy();
            } catch(Throwable t)
            {
                logger.warn("Failed to destroy the redis registry client. registry: " +
                	entry.getKey() + ", cause: " + t.getMessage(), t);
            }
        }
    }

    @Override
    public void doRegister(URL url)
    {
        String key = toCategoryPath(url);
        String value = url.toFullString();
        String expire = String.valueOf(System.currentTimeMillis() + expirePeriod);

        boolean success = false;
        RpcException exception = null;

        for(Map.Entry<String, JedisPool> entry: jedisPools.entrySet())
        {
            JedisPool jedisPool = entry.getValue();
            try
            {
                Jedis jedis = jedisPool.getResource();
                try
                {
                    jedis.hset(key, value, expire);
                    jedis.publish(key, Constants.REGISTER);
                    success = true;
                    if (!replicate)
                    {//如果服务器端已同步数据，只需写入单台机器
                    	break; 
                    }
                } finally
                {
                    jedisPool.returnResource(jedis);
                }
            } catch(Throwable t)
            {
                exception = new RpcException("Failed to register service to redis registry. registry: " +
                	entry.getKey() + ", service: " + url +
                	", cause: " + t.getMessage(), t);
            }
        }
        
        if (exception != null)
        {
            if (success)
            {
                logger.warn(exception.getMessage(), exception);
            } else
            throw exception;
        }
    }

    @Override
    public void doUnregister(URL url)
    {
        String key = toCategoryPath(url);
        String value = url.toFullString();
        RpcException exception = null;
        
        boolean success = false;
        
        for(Map.Entry<String, JedisPool> entry: jedisPools.entrySet())
        {
            JedisPool jedisPool = entry.getValue();
            try
            {
                Jedis jedis = jedisPool.getResource();
                
                try
                {
                    jedis.hdel(key, value);
                    jedis.publish(key, Constants.UNREGISTER);
                    success = true;
                    if (!replicate)
                    {
                    	break; //  如果服务器端已同步数据，只需写入单台机器
                    }
                } finally
                {
                    jedisPool.returnResource(jedis);
                }
            } catch(Throwable t)
            {
                exception = new RpcException(
                	"Failed to unregister service to redis registry. registry: " +
        			entry.getKey() + ", service: " + url +
        			", cause: " + t.getMessage(), t);
            }
        }
        
        if (exception != null)
        {
            if (success)
            {
                logger.warn(exception.getMessage(), exception);
            } else
            throw exception;
        }
    }
    
    @Override
    public void doSubscribe(final URL url, final NotifyListener listener)
    {
        String service = toServicePath(url);
        Notifier notifier = notifiers.get(service);
        if (notifier == null)
        {
            Notifier newNotifier = new Notifier(service);
            notifiers.putIfAbsent(service, newNotifier);
            notifier = notifiers.get(service);

            if (notifier == newNotifier)
            {
                notifier.start();
            }
        }

        boolean success = false;
        RpcException exception = null;
        
        for(Map.Entry<String, JedisPool> entry: jedisPools.entrySet())
        {
            JedisPool jedisPool = entry.getValue();
            
            try
            {
                Jedis jedis = jedisPool.getResource();
                try
                {
                    if (service.endsWith(Constants.ANY_VALUE))
                    {
                        admin = true;
                        Set<String> keys = jedis.keys(service);
                        if (keys != null && keys.size() > 0)
                        {
                            Map<String, Set<String>> serviceKeys =
                            	new HashMap<String, Set<String>>();
                            for(String key: keys)
                            {
                                String serviceKey = toServicePath(key);
                                Set<String> sk = serviceKeys.get(serviceKey);
                                if (sk == null)
                                {
                                    sk = new HashSet<String>();
                                    serviceKeys.put(serviceKey, sk);
                                }
                                
                                sk.add(key);
                            }

                            for(Set<String> sk: serviceKeys.values())
                            {
                                doNotify(jedis, sk, url, Arrays.asList(listener));
                            }
                        }
                    } else
                    {
                        doNotify(jedis, jedis.keys(
                        	service + Constants.PATH_SEPARATOR + Constants.ANY_VALUE),
                        	url, Arrays.asList(listener));
                    }
                    success = true;
                    break; // 只需读一个服务器的数据
                } finally
                {
                    jedisPool.returnResource(jedis);
                }
            } catch(Throwable t)
            { // 尝试下一个服务器
                exception = new RpcException(
                	"Failed to subscribe service from redis registry. registry: " +
        			entry.getKey() + ", service: " +
                	url + ", cause: " + t.getMessage(), t);
            }
        }
        
        if (exception != null)
        {
            if (success)
            {
                logger.warn(exception.getMessage(), exception);
            } else
            throw exception;
        }
    }

    @Override
    public void doUnsubscribe(URL url, NotifyListener listener)
    {
    }

    private void doNotify(Jedis jedis, String key)
    {
        for(Map.Entry<URL, Set<NotifyListener>> entry:
        		new HashMap<URL, Set<NotifyListener>>(getSubscribed()).entrySet())
        {
            doNotify(jedis, Arrays.asList(key),
            	entry.getKey(), new HashSet<NotifyListener>(entry.getValue()));
        }
    }

    private void doNotify(Jedis jedis,
    	Collection<String> keys, URL url, Collection<NotifyListener> listeners)
    {
        if (keys == null || keys.size() == 0 ||
        	listeners == null || listeners.size() == 0)
        {
            return;
        }

        long now = System.currentTimeMillis();
        List<URL> result = new ArrayList<URL>();
        List<String> categories = Arrays.asList(
        	url.getParameter(Constants.CATEGORY_KEY, new String[0]));
        String consumerService = url.getServiceInterface();

        for(String key: keys)
        {
            if (!Constants.ANY_VALUE.equals(consumerService))
            {
                String prvoiderService = toServiceName(key);
                if (!prvoiderService.equals(consumerService))
                {
                    continue;
                }
            }

            String category = toCategoryName(key);
            if (!categories.contains(Constants.ANY_VALUE) &&
            	!categories.contains(category))
            {
                continue;
            }

            List<URL> urls = new ArrayList<URL>();
            Map<String, String> values = jedis.hgetAll(key);

            if (values != null && values.size() > 0)
            {
                for(Map.Entry<String, String> entry: values.entrySet())
                {
                    URL u = URL.valueOf(entry.getKey());
                    if (!u.getParameter(Constants.DYNAMIC_KEY, true) ||
                    	Long.parseLong(entry.getValue()) >= now)
                    {
                        if (UrlUtils.isMatch(url, u))
                        {
                            urls.add(u);
                        }
                    }
                }
            }
            
            if (urls.isEmpty())
            {
                urls.add(
                	url.setProtocol(Constants.EMPTY_PROTOCOL)
                        .setAddress(Constants.ANYHOST_VALUE)
                        .setPath(toServiceName(key))
                        .addParameter(Constants.CATEGORY_KEY, category));
            }
            
            result.addAll(urls);
            
            if (logger.isInfoEnabled())
            {
                logger.info("redis notify: " + key + " = " + urls);
            }
        }
        
        if (result == null || result.size() == 0)
        {
            return;
        }
        
        for(NotifyListener listener: listeners)
        {
            notify(url, listener, result);
        }
    }

    private String toServiceName(String categoryPath)
    {
        String servicePath = toServicePath(categoryPath);
        
        return servicePath.startsWith(root)?
        	servicePath.substring(root.length()): servicePath;
    }

    private String toCategoryName(String categoryPath)
    {
        int i = categoryPath.lastIndexOf(Constants.PATH_SEPARATOR);
        
        return i > 0? categoryPath.substring(i + 1): categoryPath;
    }

    private String toServicePath(String categoryPath)
    {
        int i;
        
        if (categoryPath.startsWith(root))
        {
            i = categoryPath.indexOf(Constants.PATH_SEPARATOR, root.length());
        } else
        {
            i = categoryPath.indexOf(Constants.PATH_SEPARATOR);
        }

        return i > 0? categoryPath.substring(0, i): categoryPath;
    }

    private String toServicePath(URL url)
    {
        return root + url.getServiceInterface();
    }

    private String toCategoryPath(URL url)
    {
        return toServicePath(url) + Constants.PATH_SEPARATOR +
        	url.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
    }
	
	private class NotifySub
		extends JedisPubSub
	{    
        private final JedisPool jedisPool;

        public NotifySub(JedisPool jedisPool)
        {
            this.jedisPool = jedisPool;
        }

        @Override
        public void onMessage(String key, String msg)
        {
            if (logger.isInfoEnabled())
            {
                logger.info("redis event: " + key + " = " + msg);
            }
            
            if (msg.equals(Constants.REGISTER) ||
            	msg.equals(Constants.UNREGISTER))
            {
                try
                {
                    Jedis jedis = jedisPool.getResource();
                    try
                    {
                        doNotify(jedis, key);
                    } finally
                    {
                        jedisPool.returnResource(jedis);
                    }
                } catch(Throwable t)
                {//TODO 通知失败没有恢复机制保障
                    logger.error(t.getMessage(), t);
                }
            }
        }

        @Override
        public void onPMessage(String pattern, String key, String msg)
        {
            onMessage(key, msg);
        }

        @Override
        public void onSubscribe(String key, int num)
        { }

        @Override
        public void onPSubscribe(String pattern, int num)
        { }

        @Override
        public void onUnsubscribe(String key, int num)
        { }

        @Override
        public void onPUnsubscribe(String pattern, int num)
        { }
    }

    private class Notifier
    	extends Thread
    {
        private final String service;

        private volatile Jedis jedis;

        private volatile boolean first = true;
        
        private volatile boolean running = true;
        
        private final AtomicInteger connectSkip = new AtomicInteger();
        private final AtomicInteger connectSkiped = new AtomicInteger();

        private final Random random = new Random();
        
        private volatile int connectRandom;

        private void resetSkip()
        {
            connectSkip.set(0);
            connectSkiped.set(0);
            connectRandom = 0;
        }
        
        private boolean isSkip()
        {
            int skip = connectSkip.get(); // 跳过次数增长
            
            if (skip >= 10)
            { // 如果跳过次数增长超过10，取随机数
                if (connectRandom == 0)
                {
                    connectRandom = random.nextInt(10);
                }

                skip = 10 + connectRandom;
            }
            if (connectSkiped.getAndIncrement() < skip)
            { // 检查跳过次数
                return true;
            }
            
            connectSkip.incrementAndGet();
            connectSkiped.set(0);
            connectRandom = 0;
            
            return false;
        }
        
        public Notifier(String service)
        {
            super.setDaemon(true);
            super.setName("DubboRedisSubscribe");
            this.service = service;
        }
        
        @Override
        public void run()
        {
            while (running)
            {
                try
                {
                    if (!isSkip())
                    {
                        try
                        {
                            for(Map.Entry<String, JedisPool> entry: jedisPools.entrySet())
                            {
                                JedisPool jedisPool = entry.getValue();
                                
                                try
                                {
                                    jedis = jedisPool.getResource();
                                    
                                    try
                                    {
                                        if (service.endsWith(Constants.ANY_VALUE))
                                        {
                                            if (!first)
                                            {
                                                first = false;
                                                Set<String> keys = jedis.keys(service);
                                                if (keys != null && keys.size() > 0)
                                                {
                                                    for(String key: keys)
                                                    {
                                                        doNotify(jedis, key);
                                                    }
                                                }
                                                resetSkip();
                                            }
                                            jedis.psubscribe(
                                            	new NotifySub(jedisPool), service); // 阻塞
                                        } else
                                        {
                                            if (!first)
                                            {
                                                first = false;
                                                doNotify(jedis, service);
                                                resetSkip();
                                            }

                                            jedis.psubscribe(
                                            	new NotifySub(jedisPool),
                                            	service + Constants.PATH_SEPARATOR + Constants.ANY_VALUE); // 阻塞
                                        }

                                        break;
                                    } finally
                                    {
                                        jedisPool.returnBrokenResource(jedis);
                                    }
                                } catch(Throwable t)
                                { // 重试另一台
                                    logger.warn(
                                    	"Failed to subscribe service from redis registry. registry: " + entry.getKey() +
                                    	", cause: " + t.getMessage(), t);
                                }
                            }
                        } catch(Throwable t)
                        {
                            logger.error(t.getMessage(), t);
                            sleep(reconnectPeriod);
                        }
                    }
                } catch(Throwable t)
                {
                    logger.error(t.getMessage(), t);
                }
            }
        }
        
        public void shutdown()
        {
            try
            {
                running = false;
                jedis.disconnect();
            } catch(Throwable t)
            {
                logger.warn(t.getMessage(), t);
            }
        }   
    }
}
