package com.alice.shortdomain.service;

import com.alice.shortdomain.dto.RequestDTO;
import com.alice.shortdomain.dto.ResponseDTO;

/**
 * 域名转换
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/14 9:50
 */
public interface DomainTransferService {

    /**
     * 长域名转短域名
     *
     * @param request
     * @return
     */
    ResponseDTO<String> transferShort(RequestDTO request);


    /**
     * 通过短域名查询
     *
     * @param request
     * @return
     */
    ResponseDTO<String> search(RequestDTO request);


}
