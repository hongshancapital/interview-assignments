package com.xwt.service.impl;

import com.xwt.domain.vo.ResponseModel;
import com.xwt.service.DomainService;
import com.xwt.dependence.NumericConvertUtil;
import com.xwt.excpetion.DomainException;
import com.xwt.remote.DomainCacheService;
import com.xwt.remote.GlobalIndexService;
import com.xwt.domain.vo.DomainNameResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 域名业务层
 *
 * @author xiwentao
 * @date: 2021-07-21
 */
@Slf4j
@Service
public class DomainServiceImpl implements DomainService {


    /**
     * 域名存储
     *
     * @param longUrl 长域名
     * @date: 2021-07-21
     * @return: ResponseModel
     */
    @Override
    public ResponseModel save(String longUrl) {
        if (ObjectUtils.isEmpty(longUrl)) {
            throw new DomainException("url不能为空");
        }
        Long globalIndex = GlobalIndexService.getGlobalIndexProxy().longValue();

        String shortUrlEncode = NumericConvertUtil.toOtherNumberSystem(globalIndex);

        DomainCacheService.put(shortUrlEncode, longUrl);

        return ResponseModel.ok(buildDomainNameResponse(shortUrlEncode, longUrl), "处理成功");
    }

    /**
     * 长域名查询
     *
     * @param shortUrl 短域名
     * @date: 2021-07-21
     * @return: ResponseModel
     */
    @Override
    public ResponseModel search(String shortUrl) {
        String longUrl = DomainCacheService.get(shortUrl);
        if (StringUtils.isNotEmpty(longUrl)) {
            return ResponseModel.ok(buildDomainNameResponse(shortUrl, longUrl), "查询成功");
        }
        return ResponseModel.fail("查询失败");
    }

    /**
     * 返回值对象构造
     *
     * @param shortUrl 短连接
     * @param longUrl  长链接
     * @date: 2021-07-21
     * @return: com.xwt.domain.vo.DomainNameResponse
     */
    private DomainNameResponse buildDomainNameResponse(String shortUrl, String longUrl) {
        return DomainNameResponse
                .builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .build();
    }
}
