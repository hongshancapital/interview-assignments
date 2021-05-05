package com.wangxinyu.interview.controller;

import com.wangxinyu.interview.bean.CommonReturnType;
import com.wangxinyu.interview.bean.UrlDTO;
import com.wangxinyu.interview.cons.ErrorReason;
import com.wangxinyu.interview.model.MemData;
import com.wangxinyu.interview.service.GenAndSaveSURLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(tags = "根据短域名搜索长域名")
public class SearchForLURLController {
    private GenAndSaveSURLService genAndSaveSURLService;
    @Value("${biz.maxSUrlLength}")
    private int maxSUrlLength;

    @Autowired
    public SearchForLURLController(GenAndSaveSURLService genAndSaveSURLService) {
        this.genAndSaveSURLService = genAndSaveSURLService;
    }

    @GetMapping("/getLUrl")
    @ApiOperation("根据短域名搜索长域名")
    public CommonReturnType<UrlDTO> searchForLURL(@RequestParam(value = "sUrl") @ApiParam("短域名") String sUrl) {
        if (sUrl.length() > maxSUrlLength) {
            return new CommonReturnType<>(ErrorReason.SURL_TOO_LONG.getCode(), ErrorReason.SURL_TOO_LONG.getReason(), null);
        }
        long sUrlDecimalNumber;
        try {
            sUrlDecimalNumber = genAndSaveSURLService.decimalConvertToNumber(sUrl, GenAndSaveSURLService.ARRAY.length);
        } catch (Exception e) {
            return new CommonReturnType<>(ErrorReason.OTHER_ERROR.getCode(), e.getMessage(), null);
        }
        String longURL = MemData.DB.get(sUrlDecimalNumber);
        if (longURL != null) {
            return new CommonReturnType<>(0, null, new UrlDTO(longURL, sUrl));
        } else {
            return new CommonReturnType<>(ErrorReason.NOT_FOUND.getCode(), ErrorReason.NOT_FOUND.getReason(), null);
        }

    }
}
