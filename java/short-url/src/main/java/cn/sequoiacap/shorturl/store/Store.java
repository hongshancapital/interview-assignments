package cn.sequoiacap.shorturl.store;

import cn.sequoiacap.shorturl.exception.StoreException;

public interface Store {
    /**
     * 将短链接 id 和长链接的映射关系写入存储
     *
     * @param shortUrlId  短链接 id
     * @param originalUrl 长链接
     * @return {@code true} 写入成功
     * <br>
     * {@code false} 短链接 id 有冲突, 可以重新生成再尝试写入
     * @throws StoreException 写存储出现异常时抛出, 无需重试, 接口调用方决定是否重试
     */
    boolean write(String shortUrlId, String originalUrl) throws StoreException;

    /**
     * 根据短链接 id 获取长链接
     *
     * @param shortUrlId 短链接 id
     * @return 长链接
     * @throws StoreException 读存储出现异常时抛出
     */
    String get(String shortUrlId) throws StoreException;
}
