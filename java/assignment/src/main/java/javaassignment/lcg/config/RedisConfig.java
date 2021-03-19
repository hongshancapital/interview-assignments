package javaassignment.lcg.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: 栾晨光
 * @Date: 2021-03-18 18:55
 * @Email 10136547@qq.com
 * @Description: Redis配置
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    @Value("${spring.redis.database}")
    private int dataBase;
    @Value(("${spring.redis.host}"))
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.timeout}")
    private int timeOut;
    @Value("${spring.redis.password}")
    private String passWord;

    @Bean
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxTotal(maxActive);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(false);
        return config;
    }
    @Bean(name="dB0")
    public RedisTemplate getdB0(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));

        stringRedisTemplate.setEnableTransactionSupport(false);

        return stringRedisTemplate;
    }
    @Bean(name="dB1")
    public RedisTemplate getdB1(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(1);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));

        stringRedisTemplate.setEnableTransactionSupport(false);

        return stringRedisTemplate;

    }
    @Bean(name="dB2")
    public RedisTemplate getdB2(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(2);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));

        stringRedisTemplate.setEnableTransactionSupport(false);


        return stringRedisTemplate;
    }
    @Bean(name="dB3")
    public RedisTemplate getdB3(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(3);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));

        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB4")
    public RedisTemplate getdB4(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(4);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB5")
    public RedisTemplate getdB5(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(5);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB6")
    public RedisTemplate getdB6(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(6);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB7")
    public RedisTemplate getdB7(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(7);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB8")
    public RedisTemplate getdB8(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(8);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB9")
    public RedisTemplate getdB9(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(9);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);



        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB10")
    public RedisTemplate getdB10(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(10);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB11")
    public RedisTemplate getdB11(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(11);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB12")
    public RedisTemplate getdB12(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(12);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB13")
    public RedisTemplate getdB13(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(13);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);



        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB14")
    public RedisTemplate getdB14(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(14);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
    @Bean(name="dB15")
    public RedisTemplate getdB15(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(15);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(passWord);


        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(new JedisConnectionFactory(redisStandaloneConfiguration));
        stringRedisTemplate.setEnableTransactionSupport(false);
        return stringRedisTemplate;
    }
}
