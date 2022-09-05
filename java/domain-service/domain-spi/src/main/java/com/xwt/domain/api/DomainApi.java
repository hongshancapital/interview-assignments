package com.xwt.domain.api;


import com.xwt.domain.vo.DomainNameRequest;
import com.xwt.domain.vo.DomainNameResponse;
import com.xwt.domain.vo.ResponseModel;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 域名服务
 *
 * @author xiwentao
 * @date: 2021-07-21
 */
@Api(value = "域名服务")
@RequestMapping(value = "/service/v1", produces = {"application/json"})
public interface DomainApi {


    /**
     * 长域名域名存储并返回短域名
     *
     * @param request 请求对象
     * @date: 2021-07-21
     * @return: ResponseModel<DomainNameResponse>
     * * @since 1.0
     * * @version 1.0
     */
    @ApiOperation(value = "短域名存储接口")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "结果")})
    @RequestMapping(method = RequestMethod.POST, path = "/domain")
    ResponseModel<DomainNameResponse> save(
            @ApiParam(required = true, value = "add And update") @Valid @RequestBody DomainNameRequest request);

    /**
     * 长域名查询
     *
     * @param shortUrl 短域名
     * @date: 2021-07-21
     * @return: ResponseModel<DomainNameResponse>
     * @version 1.0
     * @since 1.0
     */
    @ApiOperation(value = "短域名读取接口")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "结果")})
    @RequestMapping(method = RequestMethod.GET, path = "/domain/{shortUrl}")
    ResponseModel<DomainNameResponse> search(@ApiParam(required = true, value = "长域名url") @NotBlank @PathVariable("shortUrl") String shortUrl);


}
