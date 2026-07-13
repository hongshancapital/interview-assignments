package com.alexyuan.shortlink.controller;

import com.alexyuan.shortlink.common.variant.CacheVariant;
import com.alexyuan.shortlink.common.variant.ResultVariant;
import com.alexyuan.shortlink.exceptions.ImproperValueException;
import com.alexyuan.shortlink.exceptions.SystemErrorException;
import com.alexyuan.shortlink.service.LinkConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("短域名服务")
@RestController
public class ShortLinkController {

    private static final Logger logger = LoggerFactory.getLogger(ShortLinkController.class);

    private static final String IMPROPER_MSG = "Improper Value, please Check Input";

    private static final String SYSERROR_MSG = "System Error, Please Check Generator";

    private static final String NORERROR_MSG = "Running Failed";

    private static final String FAILED = "Failed";

    @Autowired
    LinkConvertService linkConvertService;

    @ApiOperation("短域名转换服务")
    @GetMapping("/genShortLink")
    public ResultVariant genShortLink(@ApiParam("长域名") String long_link) {
        try {
            CacheVariant shortLinkVar = linkConvertService.shortLinkGenerator(long_link, true);
            String uniq_code = shortLinkVar.getShort_url();
            String generate_time = shortLinkVar.getGenerate_time();
            return new ResultVariant(uniq_code, long_link, generate_time, "No Extra Message");
        } catch (ImproperValueException ive) {
            logger.error("Improper Value, Please Check Input");
            return new ResultVariant("300", "", long_link, "", IMPROPER_MSG, FAILED);
        } catch (SystemErrorException sye) {
            logger.error("System Error, Please Check Generator");
            return new ResultVariant("400", "", long_link, "", SYSERROR_MSG, FAILED);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            return new ResultVariant("500", "", long_link, "", NORERROR_MSG, FAILED);
        }
    }

    @ApiOperation("短域名转换服务压测用")
    @GetMapping("/genShortLinkPress")
    public ResultVariant genShortLinkPress(@ApiParam("长域名") String long_link) {
        try {
            CacheVariant shortLinkVar = linkConvertService.shortLinkGenerator(long_link, false);
            String uniq_code = shortLinkVar.getShort_url();
            String generate_time = shortLinkVar.getGenerate_time();
            return new ResultVariant(uniq_code, long_link, generate_time, "No Extra Message");
        } catch (ImproperValueException ive) {
            logger.error("Improper Value, Please Check Input");
            return new ResultVariant("300", "", long_link, "", IMPROPER_MSG, FAILED);
        } catch (SystemErrorException sye) {
            logger.error("System Error, Please Check Generator");
            return new ResultVariant("400", "", long_link, "", SYSERROR_MSG, FAILED);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            return new ResultVariant("500", "", long_link, "", NORERROR_MSG, FAILED);
        }
    }

    @ApiOperation("长域名查询服务")
    @GetMapping("/getLongLink")
    public ResultVariant getLongLink(@ApiParam("短域名") String short_link) {
        try {
            CacheVariant shortLinkVar = linkConvertService.longLinkSearcher(short_link);
            String uniq_code = shortLinkVar.getShort_url();
            String generate_time = shortLinkVar.getGenerate_time();
            return new ResultVariant(uniq_code, shortLinkVar.getLong_url(), generate_time, "No Extra Message");
        } catch (ImproperValueException ive) {
            logger.error("Improper Value, Please Check Input");
            return new ResultVariant("300", short_link, "", "", IMPROPER_MSG, FAILED);
        } catch (SystemErrorException sye) {
            logger.error("System Error, Please Check Generator");
            return new ResultVariant("400", short_link, "", "", SYSERROR_MSG, FAILED);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            return new ResultVariant("500", short_link, "", "", NORERROR_MSG, FAILED);
        }
    }
}
