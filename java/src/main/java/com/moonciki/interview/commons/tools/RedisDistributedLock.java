package com.moonciki.interview.commons.tools;

import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import com.moonciki.interview.commons.exception.RedisLockTimeOutException;
import com.moonciki.interview.utils.CommonUtil;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Date;
import java.util.UUID;

/**
 * redis 分布式事务锁
 * 不支持集群模式
 **/
@Slf4j
public class RedisDistributedLock {
    /** 成功 **/
    private static final String LOCK_SUCCESS = "OK";

    /** 默认过期时间 1 小时 **/
    private static final long default_expire_time = 60 * 60 * 1000;

    /** 默认等待时间 10 秒钟，防止前端超时 **/
    private static final long default_wait_time = 10 * 1000;

    /** lua redis 解锁脚本，保证原子性 **/
    public static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Getter
    @Setter
    private RedisCacheUtil redisCacheUtil;

    @Getter
    @Setter
    private String lockKeyPrefix = "redisLock";

    /**
     * 拼接分布式事务锁 key
     * @param lockKey
     * @return
     */
    public String createLockKey(String lockKey){
        String fullKey = CommonUtil.joinRedisKey(lockKeyPrefix, lockKey);
        return fullKey;
    }

    private boolean isSuccess(String result){
        boolean success = false;
        if(StringUtils.isNotBlank(result)){
            if(LOCK_SUCCESS.equalsIgnoreCase(result)){
                success = true;
            }
        }
        return success;
    }

    /**
     * 获取分布式事务锁
     * @param lockKey
     * @param expireTime 过期时间秒
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private String lockKey(String lockKey, Long expireTime) throws Exception{
        String requireId = null;

        RedisTemplate<String, Object> redisTemplate = redisCacheUtil.getRedisTemplate();

        String fullKey = createLockKey(lockKey);
        final long setExtTime = expireTime;
        final String uuid = UUID.randomUUID().toString();

        log.debug("Redis Lock Try To Get : " + fullKey);

        RedisCallback<Boolean> callback = (connection) -> {
            SetArgs letArgs = new SetArgs();
            letArgs.nx();
            letArgs.px(setExtTime);

            RedisSerializer<String> stringSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();

            byte[] keyByte = stringSerializer.serialize(fullKey);
            byte[] valueByte = stringSerializer.serialize(uuid);

            Object nativeConnection = connection.getNativeConnection();
            String redisResult = "";



            if (nativeConnection instanceof RedisAsyncCommands) {
                //lettuce单机模式

                RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
                //同步方法执行、setnx禁止异步
                redisResult = commands
                        .getStatefulConnection()
                        .sync()
                        .set(keyByte, valueByte, letArgs);

            }else if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                //lettuce集群模式
                RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                redisResult = clusterAsyncCommands
                        .getStatefulConnection()
                        .sync()
                        .set(keyByte, keyByte, letArgs);
            }else{
                throw CustomException.createException(ResponseEnum.sys_error.info());
            }

            //返回加锁结果
            return isSuccess(redisResult);

        };

        Boolean result = redisTemplate.execute(callback);

        if (result != null && result) {
            requireId = uuid;
        }

        return requireId;
    }

    /**
     * 使用默认的超时时间，默认的等待时间
     * @param lockKey
     * @return
     * @throws Exception
     */
    public String lock(String lockKey) throws Exception{
        String requireId = lock(lockKey, null, null);

        return requireId;
    }

    /**
     * 使用默认的等待时间
     * @param lockKey
     * @param expTime
     * @return
     * @throws Exception
     */
    public String lock(String lockKey, Long expTime) throws Exception {
        String requireId = lock(lockKey, expTime, null);
        return requireId;
    }

    /**
     * redis 分布式加锁
     * @param lockKey    加锁key
     * @param expireTime 过期时间
     * @param waitTime   等待时间， -1 ：一直等待；0：仅执行一次；大于0： 毫秒值
     * @return
     */
    public String lock(String lockKey, Long expireTime, Long waitTime) throws Exception{

        if(expireTime == null){
            expireTime = default_expire_time;
        }

        if(waitTime == null){
            waitTime = default_wait_time;
        }

        Long currentTime = new Date().getTime();

        Long waitMaxTime = currentTime + waitTime;

        String requireId = null;

        String fullKey = createLockKey(lockKey);

        log.info("Redis Lock Start Get : " + fullKey);
        while(true){
            requireId = lockKey(lockKey, expireTime);

            if(StringUtils.isNotBlank(requireId)){
                log.info("Redis Lock Success : " + fullKey);
                break;
            }

            if(waitTime == -1){
                //nothing to do
            }else if(waitTime == 0){
                //只获取一次
                break;
            }else{
                Long nowTime = new Date().getTime();

                if(nowTime >= waitMaxTime){
                    log.info("Redis Lock Get Timeout : " + fullKey);
                    throw RedisLockTimeOutException.createException();
                }
            }

            Thread.sleep(1000);
        }

        return requireId;
    }

    public String get(String lockKey) {
        String fullKey = createLockKey(lockKey);

        String value = (String)redisCacheUtil.get(fullKey);

        return value;
    }

    /**
     * 解锁
     * @param lockKey
     * @param requireId
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean unlock(String lockKey, String requireId) {
        RedisTemplate<String, Object> redisTemplate = redisCacheUtil.getRedisTemplate();

        boolean unlockSuc = false;

        String fullKey = createLockKey(lockKey);

        //释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            RedisSerializer<String> stringSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();

            byte[] keyBytes = stringSerializer.serialize(fullKey);
            byte[] valueBytes = stringSerializer.serialize(requireId);

            Object[] keyByteArray = {keyBytes};

            Object[] argByteArray = {valueBytes};

            // 使用lua脚本删除redis中匹配value的key，保证原子性，并且可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();

                Long unlockResult = null;


                if (nativeConnection instanceof RedisAsyncCommands) {
                    //lettuce单机模式

                    RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
                    //同步方法执行、setnx禁止异步
                    unlockResult = (Long) commands
                            .getStatefulConnection()
                            .sync()
                            .eval(UNLOCK_LUA, ScriptOutputType.INTEGER, keyByteArray, argByteArray);

                }else if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                    //lettuce集群模式
                    RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                    unlockResult = (Long) clusterAsyncCommands
                            .getStatefulConnection()
                            .sync()
                            .eval(UNLOCK_LUA, ScriptOutputType.INTEGER, keyByteArray, argByteArray);
                }else{
                    throw CustomException.createException(ResponseEnum.sys_error.info());
                }

                return unlockResult;
            };

            Long executeResult = redisTemplate.execute(callback);

            if(executeResult != null && executeResult > 0){
                unlockSuc = true;
            }

        } catch (Exception e) {
            log.error("Unlock Redis exception : " + lockKey, e);
        }

        return unlockSuc;
    }

    /**
     * 强制解锁
     * @param lockKey
     * @return
     */
    public void unlockDirect(String lockKey) {
        String fullKey = createLockKey(lockKey);
        redisCacheUtil.del(fullKey);
    }

}
