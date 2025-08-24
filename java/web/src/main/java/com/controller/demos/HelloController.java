package com.controller.demos;

import com.beans.ResultObj;
import com.service.ShortUrlService;
import com.utils.Constant;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "api")
@RestController
@RequestMapping("/api")
public class HelloController {

    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private ShortUrlService shortUrlService ;

    @ApiOperation(value = "createUrl")
    @ApiImplicitParams({
            @ApiImplicitParam(name="url",value = "url地址", paramType = "query",dataType="String",required = true)
    })
    @PostMapping(value = "createUrl")
    public ResultObj<String> createUrl(@RequestParam(required = true) String url){
        String shortUrl = null ;
        if(StringUtils.isEmpty(url)){
            return new ResultObj(Constant.RESULT_OBJ_STATUS_FAIL,"url为必填项，请输入。");
        }
        try {
            shortUrl = shortUrlService.createUrl(url) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObj(Constant.RESULT_OBJ_STATUS_SUCCESS, Constant.RESULT_OBJ_STATUS_SUCCESS_MSG,shortUrl) ;
    }


    @ApiOperation(value = "getUrlservice")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shortUrl",value = "url地址", paramType = "query",dataType="String",required = true)
    })
    @PostMapping("getUrlservice")
    public ResultObj<String> getUrlservice(@RequestParam(required = true) String shortUrl){
        String url = null ;
        if(StringUtils.isEmpty(shortUrl)){
            return new ResultObj(Constant.RESULT_OBJ_STATUS_FAIL,"shortUrl为必填项，请输入。");
        }
        try {
            Map<String,String> item = Constant.URL_SHORTURL ;
            if(item.containsValue(shortUrl)){
                url = item.entrySet().stream().filter(e -> shortUrl.equals(e.getValue())).map(Map.Entry::getKey).findFirst().get();
            }else{
                return new ResultObj(Constant.RESULT_OBJ_STATUS_SUCCESS, Constant.RESULT_OBJ_STATUS_SUCCESS_NOTFIND_MSG) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObj(Constant.RESULT_OBJ_STATUS_SUCCESS, Constant.RESULT_OBJ_STATUS_SUCCESS_MSG,url) ;
    }

}
