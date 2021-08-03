package com.sequoia.shortdomain.api;

import com.sequoia.shortdomain.common.ResponseResult;
import com.sequoia.shortdomain.common.ShortDomainConst;
import com.sequoia.shortdomain.service.IShortDomainService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortdomain/service/api/")
public class ShortDomainAPI extends BaseAPI{

    @Autowired
    private IShortDomainService service;

    @RequestMapping(value = "getShortDomain", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult getShortDomain(@RequestParam(name = "longDomain") String longDomain){

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
    public ResponseResult getLongDomain(String shortDomain){

        if(StringUtils.isBlank(shortDomain)){
            return  this.failure(ShortDomainConst.GET_FAILURE,ShortDomainConst.GET_FAILURE_MSG,Strings.EMPTY);
        }

        String longDomain=this.service.getLongDomainByShortDomain(shortDomain);
        if(StringUtils.isBlank(shortDomain)){
            return  this.failure(ShortDomainConst.GET_FAILURE,ShortDomainConst.GET_FAILURE_MSG,Strings.EMPTY);
        }else{
            return this.success(ShortDomainConst.GET_SUCCESS,ShortDomainConst.GET_SUCCESS_MSG,longDomain);
        }
    }
}
