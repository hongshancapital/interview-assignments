package xiejin.java.interview.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import xiejin.java.interview.dto.request.LongUrlRequestDTO;
import xiejin.java.interview.dto.request.ShortUrlRequestDTO;
import xiejin.java.interview.dto.response.OriginalUrlResponseDTO;
import xiejin.java.interview.dto.response.ShortUrlResponseDTO;
import xiejin.java.interview.result.ResultJson;
import xiejin.java.interview.service.DomainService;

/**
 * <p>
 * 长短域名映射表 前端控制器
 * </p>
 *
 * @author xiejin
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/api/domain/")
@Slf4j
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping("/compress")
    public ResultJson<ShortUrlResponseDTO> compress(@RequestBody @Validated LongUrlRequestDTO requestDTO) {
        return ResultJson.success(domainService.saveShortUrl(requestDTO));
    }


    @PostMapping("/uncompress")
    public ResultJson<OriginalUrlResponseDTO> uncompress(@RequestBody @Validated ShortUrlRequestDTO requestDTO) {
        return ResultJson.success(domainService.getOriginalUrl(requestDTO));
    }

}

