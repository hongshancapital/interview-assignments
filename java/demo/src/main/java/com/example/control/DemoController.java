package com.example.control;

import com.example.bean.Result;
import com.example.constant.CodeDef;
import com.example.service.ShortUrlCreateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * demo
 *   @author wenbin
 */
@Api(tags={"短连接操作"})
@Controller
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private ShortUrlCreateService service;

    /**
     * 生成短域名
     * @param lurl
     * @return
     */
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功",response= String.class),
            @ApiResponse(code = 500, message = "服务器内部异常",response=String.class)     })
    @ApiOperation(value = "crtShortUrl", notes = "创建短链接",consumes="application/json",             produces="application/json",httpMethod="POST")
    @RequestMapping("crtShortUrl")
    @ResponseBody
    public Result crtShorUrl(@RequestParam String lurl) {
        Result ret = new Result();
        String shorturl = "";
        if (StringUtils.hasLength(lurl)) {
            shorturl = service.createShortUrl(lurl);
        } else {
            ret.setCode(CodeDef.ErrCode002.CODE);
            ret.setMessage(CodeDef.ErrCode002.MASSAGE);
            return ret;
        }
        Map bean = new HashMap<>();
        bean.put("shortUrl", shorturl);
        ret.setBean(bean);
        return ret;
    }

    /**
     * 通过短域名获取长域名
     * @param surl
     * @return
     */
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功",response= Result.class),
            @ApiResponse(code = 500, message = "服务器内部异常",response=Result.class)     })
    @ApiOperation(value = "queryLurlBySurl", notes = "创建短链接",consumes="application/json",             produces="application/json",httpMethod="POST")
    @RequestMapping("queryLurlBySurl")
    @ResponseBody
    public Result queryLurlBySurl(@RequestParam String surl) {
        Result ret = new Result();
        String longurl = "";
        if (!StringUtils.hasLength(surl)) {
            ret.setCode(CodeDef.ErrorCode001.CODE);
            ret.setMessage(CodeDef.ErrorCode001.MASSAGE);
            return ret;
        }
        longurl = service.getLongUrl(surl);
        if (!StringUtils.hasLength(longurl)) {
            ret.setCode(CodeDef.ErrCode003.CODE);
            ret.setMessage(CodeDef.ErrCode003.MASSAGE);
            return ret;
        }
        Map bean = new HashMap<>();
        bean.put("longUrl", longurl);
        ret.setBean(bean);
        return ret;
    }
}
