package com.wyd.shoturl;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Bootstrap {


    public static void main(String[] args) throws Exception {
      //
        String arg = args[0];
        String generate = SHA1.generate(arg.getBytes());

        RedisURI redisURI = RedisURI.builder().withHost("localhost").withPort(6379).build();
        RedisClient client = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisCommands<String, String> sync = connect.sync();
        String set = sync.set(generate.substring(0,7),arg);
        System.out.println("得到的短链接地址"+"http://t."+set);


    }
}

