package com.wangxiao.shortlink.domain.shortlink;

public interface LinkPairRepository {
    /**
     * 如果短链接不存在则存入
     *
     * @param linkPair 短链接与长链接对
     * @return 如果短链接已存在，返回对应的长链接，否则返回null
     */
    String saveIfAbsent(LinkPair linkPair);

    /**
     * 根据短链接获取长链接
     *
     * @param shortLink 短链接
     * @return 长链接
     */
    String getLongLink(String shortLink);
}
