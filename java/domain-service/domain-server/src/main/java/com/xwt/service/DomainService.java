package com.xwt.service;


import com.xwt.domain.vo.ResponseModel;

/**
 * 域名服务业务处理层
 *
 * @author xiwentao
 */
public interface DomainService {

    /**
     * 域名保存
     *
     * @param longUrl
     * @date: 2021-07-21
     * @return: ResponseModel
     */
    ResponseModel save(String longUrl);

    /**
     * 查询长域名
     *
     * @param shortUrl
     * @date: 2021-07-21
     * @return: ResponseModel
     */
    ResponseModel search(String shortUrl);

}
