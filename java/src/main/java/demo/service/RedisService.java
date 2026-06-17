package demo.service;

/**
 * @ClassName: RedisService
 * @Description: redis 服务类
 * @author Xia
 * @version V1.0
 * @Date 2021/12/15
 */
public interface RedisService {

    /**
     * 存入Redis
     * @param key
     * @param value
     */
    void put(String key, String value);

    /**
     * 根据Key值去取出
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取序列
     * @param value
     * @return
     */
    long incr(String value);
}
