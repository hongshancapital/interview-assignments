package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.YumingService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/yuming")
@Api(value = "域名转换controller", tags = {"域名转换"})
public class YumingController {

    private static final Logger logger = LogManager.getLogger(YumingController.class);

    private YumingService yumingService = new YumingService();


    @ApiOperation(value = "长域名获得短域名", notes = "长域名获得短域名1")
    @GetMapping("/long_short")
    public ResultModel long2Short(final HttpServletRequest request,
                                     @RequestParam(value = "longUrl", required = true) String longUrl) {

        ResultModel resultModel = new ResultModel();
        try {
             resultModel.setData(this.yumingService.long2Short(longUrl));
        } catch (Exception e) {
            resultModel.setReturnCode(-1);
            resultModel.setReturnMsg(e.getMessage());
            logger.error("", e);
        }

        return resultModel;
    }

    @ApiOperation(value = "短域名获得长域名", notes = "短域名获得长域名1")
    @GetMapping("/short_long")
    public ResultModel short2Long(final HttpServletRequest request,
                                 @RequestParam(value = "shortUrl", required = true) String shortUrl) {
        ResultModel resultModel = new ResultModel();
        try {
           resultModel.setData(this.yumingService.short2Long(shortUrl));
        } catch (Exception e) {
            resultModel.setReturnCode(-1);
            resultModel.setReturnMsg(e.getMessage());
            logger.error("", e);
        }

        return resultModel;
    }
}
