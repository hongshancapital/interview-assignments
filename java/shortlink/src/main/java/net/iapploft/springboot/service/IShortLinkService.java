package net.iapploft.springboot.service;

public interface IShortLinkService {

    /**
     * 根据源连接获取短连接
     * @param link
     * @return
     */
    String getShortLinkByLink(String link);

    /**
     * 根据短连接获取源连接
     * @param shortLink
     * @return
     */
    String getLinkByShortLink(String shortLink) throws Exception;


}
