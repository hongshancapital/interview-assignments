package springboot.bench.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springboot.bench.consts.Constants;
import springboot.bench.service.BiMapBasedDomainPool;
import springboot.bench.vo.GeneralResponse;
import springboot.bench.vo.QueryShortDomainRequest;
import springboot.bench.vo.SaveLongDomainRequest;

@RestController
@Api(value = "DomainController", tags = "短链接口服务")
public class DomainController {

    /**
     * ping 测试接口
     * @return
     */
    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "ping", notes = "ping检查", httpMethod = "GET")
    public String ping() {
        return "pong";
    }

    // curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"longDomain": "http://abc.com"}' http://127.0.0.1:11000/saveDomain
    
    /**
     * 保存长链，返回短链
     * @param longDomainReq
     * @return
     */
    @RequestMapping(path = "/saveDomain", method = RequestMethod.POST, produces = "application/json", name = "保存长链返回短链")
    @ResponseBody    
    @ApiOperation(value = "saveDomain", notes = "保存长链，返回短链", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "longDomainReq", value = "longDomainReq", required = true, dataType = "SaveLongDomainRequest", paramType = "body")
    })
    public GeneralResponse saveDomain(@RequestBody SaveLongDomainRequest longDomainReq) {
        String longDomain = longDomainReq.getLongDomain();
        String shortDomain = null;
        try {
            shortDomain = BiMapBasedDomainPool.get().saveLongDomain(longDomain);
            return new GeneralResponse(shortDomain);
        } catch (Exception e) {
            return new GeneralResponse(Constants.REVOKE_NOK, e.getMessage());
        }
    }

    // curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"shortDomain": "http://KsLlKglK"}' http://127.0.0.1:11000/getDomain
    
    @RequestMapping(path = "/getDomain", method = RequestMethod.POST, produces = "application/json", name = "按短链获取长链")
    @ResponseBody
    @ApiOperation(value = "getDomain", notes = "根据短链查询长链", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "shortDomainReq", value = "shortDomainReq", required = true, dataType = "QueryShortDomainRequest", paramType = "body")
    })
    public GeneralResponse getDomain(@RequestBody QueryShortDomainRequest shortDomainReq) {
        String shortDomain = shortDomainReq.getShortDomain();
        String longDomain = BiMapBasedDomainPool.get().getOriginalDomain(shortDomain);
        if (longDomain != null) {
            return new GeneralResponse(longDomain);
        } else {
            return new GeneralResponse(Constants.REVOKE_NOK, Constants.ERR_SHORT_DOMAIN_MAPPING_NOT_EXISTS);
        }
    }

}
