package com.xwt.controller;

import com.xwt.domain.api.DomainApi;
import com.xwt.domain.vo.DomainNameRequest;
import com.xwt.domain.vo.DomainNameResponse;
import com.xwt.domain.vo.ResponseModel;
import com.xwt.excpetion.DomainException;
import com.xwt.service.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 域名服务控制层
 *
 * @author xiwentao
 * @date: 2021-07-21
 */
@Slf4j
@RestController
public class DomainController implements DomainApi {

    @Autowired
    private DomainService domainService;

    /**
     * 长域名域名存储并返回短域名
     *
     * @param request 域名保存请求对象
     * @date: 2021-07-21
     * @return: ResponseModel<DomainNameResponse>
     */
    @Override
    public ResponseModel<DomainNameResponse> save(DomainNameRequest request) {
        try {
            return domainService.save(request.getLongUrl());
        } catch (DomainException d) {
            log.info("域名保存处理失败,[{}]", d.getMessage());

        } catch (Exception e) {
            log.error("域名保存处理异常,[{}]", e.getMessage(), e);
        }
        return ResponseModel.fail("处理失败");
    }

    /**
     * 长域名查询
     *
     * @param shortUrl
     * @date: 2021-07-21
     * @return: ResponseModel<DomainNameResponse>
     */
    @Override
    public ResponseModel<DomainNameResponse> search(String shortUrl) {

        try {
            return domainService.search(shortUrl);
        } catch (DomainException d) {
            log.info("域名查询失败,[{}]", d.getMessage());

        } catch (Exception e) {
            log.error("域名查询异常,[{}]", e.getMessage(), e);
        }
        return ResponseModel.fail("查询失败");
    }
}
