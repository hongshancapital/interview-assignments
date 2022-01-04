package me.huchao.sd.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.service.ShortDomainService;
import me.huchao.sd.web.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huchao36
 */
@RestController
@RequestMapping("/sd")
@Api(value = "short domain api")
public class ShortDomainController {

    private ShortDomainService shortDomainService;

    public ShortDomainController(@Autowired ShortDomainService shortDomainService) {
        super();
        this.shortDomainService = shortDomainService;
    }

    @GetMapping("/search")
    @ResponseBody
    @ApiOperation(value = "通过短域名查询原始域名")
    public Resp<String> search(@RequestParam("sd") String sd) {
        try {
            Node node = this.shortDomainService.getByShortDomain(sd);
            if (node != null) {
                return new Resp<String>(200).data(node.getOrigin()).msg("");
            } else {
                return new Resp<String>(200).data("").msg("");
            }
        } catch (Exception e) {
            return new Resp<String>(500).data("").msg(e.getMessage());
        }
    }

    @PostMapping("/mapping")
    @ResponseBody
    @ApiOperation(value = "获取原始域名对应的短域名")
    public Resp<String> mapping(@RequestBody String origin) {
        try {
            Node node = this.shortDomainService.getByOrigin(origin);
            return new Resp<String>(200).data(node.getCode()).msg("");
        } catch (Exception e) {
            return new Resp<String>(500).msg(e.getMessage());
        }
    }
}
