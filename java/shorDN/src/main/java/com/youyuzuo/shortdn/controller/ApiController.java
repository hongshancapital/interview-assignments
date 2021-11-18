package com.youyuzuo.shortdn.controller;
import com.youyuzuo.shortdn.bo.DataResp;
import com.youyuzuo.shortdn.bo.QueryResult;
import com.youyuzuo.shortdn.bo.SaveResult;
import com.youyuzuo.shortdn.dto.DomainNameDto;
import com.youyuzuo.shortdn.service.DomainNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "短域名API")
@RestController
@RequestMapping("/api")
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private DomainNameService domainNameService;

    @ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息")
    @ResponseBody
    @RequestMapping("/save")
    public String save(@ApiParam(value = "长域名的值" , required=true ) @RequestParam String longDn){

        try {
            if(null == longDn){
                return DataResp.systemError();
            }
            DomainNameDto dto = domainNameService.save(longDn);
            if (dto == null) {
                DataResp<SaveResult> resp = new DataResp<>(DataResp.ERROR, "存储失败", null);
                return resp.toJsonString();
            }
            DataResp<SaveResult> resp = new DataResp<>(DataResp.SUCCESS, "成功", dto.toSaveResult());
            return resp.toJsonString();
        } catch (Exception e) {
            return DataResp.systemError();
        }

    }

    @ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息")
    @ResponseBody
    @RequestMapping("/query")
    public String query(@ApiParam(value = "短域名的值" , required=true ) @RequestParam String shortDn){
        if(null == shortDn){
            return DataResp.systemError();
        }
        try {
            DomainNameDto dto = domainNameService.queryLongDn(shortDn);;
            if (dto == null) {
                DataResp<QueryResult> resp = new DataResp<>(DataResp.ERROR, "查询失败", null);
                return resp.toJsonString();
            }
            DataResp<QueryResult> resp = new DataResp<>(DataResp.SUCCESS, "成功", dto.toQueryResult());
            return resp.toJsonString();
        } catch (Exception e) {
            return DataResp.systemError();
        }
    }
}
