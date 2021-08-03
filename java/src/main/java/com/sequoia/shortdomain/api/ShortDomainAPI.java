package com.sequoia.shortdomain.api;

import com.sequoia.shortdomain.common.ResponseResult;
import com.sequoia.shortdomain.common.ShortDomainConst;
import com.sequoia.shortdomain.service.IShortDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="短域名接口")
@RestController
@RequestMapping("/shortdomain/service/api/")
public class ShortDomainAPI extends BaseAPI{

    @Autowired
    private IShortDomainService service;

    @ApiOperation("获取短域名接口")
    @RequestMapping(value = "getShortDomain", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult getShortDomain( @ApiParam(name = "长域名")@RequestParam(name = "longDomain") String longDomain){

        if(StringUtils.isBlank(longDomain)){
         return  this.failure(ShortDomainConst.GET_FAILURE,ShortDomainConst.GET_FAILURE_MSG,Strings.EMPTY);
        }

        String shortDomain=this.service.getShortDomainByLongDomain(longDomain);
        if(StringUtils.isBlank(shortDomain)){
            return  this.failure(ShortDomainConst.GET_FAILURE,ShortDomainConst.GET_FAILURE_MSG,Strings.EMPTY);
        }else{
            return this.success(ShortDomainConst.GET_SUCCESS,ShortDomainConst.GET_SUCCESS_MSG,shortDomain);
        }
    }

    @RequestMapping(value = "getLongDomain", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult getLongDomain(@RequestParam(name = "shortDomain")String shortDomain){

        if(StringUtils.isBlank(shortDomain)){
            return  this.failure(ShortDomainConst.GET_FAILURE,ShortDomainConst.GET_FAILURE_MSG,Strings.EMPTY);
        }

        String longDomain=this.service.getLongDomainByShortDomain(shortDomain);
        if(StringUtils.isBlank(longDomain)){
            return  this.failure(ShortDomainConst.GET_FAILURE,ShortDomainConst.GET_FAILURE_MSG,Strings.EMPTY);
        }else{
            return this.success(ShortDomainConst.GET_SUCCESS,ShortDomainConst.GET_SUCCESS_MSG,longDomain);
        }
    }
}
