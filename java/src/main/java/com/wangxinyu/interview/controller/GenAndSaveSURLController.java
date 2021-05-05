package com.wangxinyu.interview.controller;

import com.wangxinyu.interview.InterviewApplication;
import com.wangxinyu.interview.bean.CommonReturnType;
import com.wangxinyu.interview.bean.UrlDTO;
import com.wangxinyu.interview.cons.ErrorReason;
import com.wangxinyu.interview.service.GenAndSaveSURLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(tags = "根据长域名生成并保存短域名")
public class GenAndSaveSURLController {
    private GenAndSaveSURLService genAndSaveSURLService;
    @Value("${biz.maxSUrlLength}")
    private int maxSUrlLength;

    @Autowired
    public GenAndSaveSURLController(GenAndSaveSURLService genAndSaveSURLService) {
        this.genAndSaveSURLService = genAndSaveSURLService;
    }

    /**
     * 根据长域名生成并保存短域名
     * <p>
     * 具体生成规则请参考GenAndSaveSURLService -> genAndSaveSURL(String LURL)
     *
     * @param lUrl 长域名
     * @return CommonReturnType<UrlDTO>
     */
    @PostMapping("/genSUrl")
    @ApiOperation("根据长域名生成并保存短域名")
    public CommonReturnType<UrlDTO> genAndSaveSUrl(@RequestParam(value = "lUrl") @ApiParam("长域名") String lUrl) {
        if (lUrl.length() > 200) {
            return new CommonReturnType<>(ErrorReason.LURL_TOO_LONG.getCode(), ErrorReason.LURL_TOO_LONG.getReason(), null);
        }
        if (lUrl.length() <= maxSUrlLength) {
            return new CommonReturnType<>(ErrorReason.LURL_TOO_SHORT.getCode(), ErrorReason.LURL_TOO_SHORT.getReason(), null);
        }
        if (!InterviewApplication.acceptNewRequest.get()) {
            return new CommonReturnType<>(ErrorReason.NOT_ENOUGH_MEMORY.getCode(),ErrorReason.NOT_ENOUGH_MEMORY.getReason(),null);
        }
        String errorMsg = "";
        int code = 0;
        UrlDTO urlDTO = null;
        try {
            urlDTO = genAndSaveSURLService.genAndSaveSURL(lUrl);
        } catch (Exception e) {
            code = ErrorReason.OTHER_ERROR.getCode();
            errorMsg = ErrorReason.OTHER_ERROR.getReason() + e.getMessage();
            e.printStackTrace();
        }

        return new CommonReturnType<>(code, errorMsg, urlDTO);
    }

}
