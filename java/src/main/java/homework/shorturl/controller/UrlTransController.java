package homework.shorturl.controller;

import homework.shorturl.model.dto.ResponseDTO;
import homework.shorturl.model.dto.UrlTransDTO;
import homework.shorturl.service.UrlTransService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
 import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags="长短链转换")
@RequestMapping("/urlTrans")
public class UrlTransController {
    @Resource
    UrlTransService urlTransService;

    @ApiOperation("长链转短链")
    @PostMapping("/transShort")
    public ResponseDTO<UrlTransDTO> transShort(@RequestBody UrlTransDTO dto) {
        try {
            if (!StringUtils.hasText(dto.getUrl())) {
                return ResponseDTO.parmaError("长链接不可为空");
            }
            UrlTransDTO res = urlTransService.transShort(dto);
            return ResponseDTO.success(res);
        } catch (Exception e) {
            log.error("transShort error: {}, {}", dto, e.getMessage(), e);
            return ResponseDTO.error("长链转短链失败：" + e.getMessage());
        }
    }

    @ApiOperation("短链转长链")
    @PostMapping("/transLong")
    public ResponseDTO<UrlTransDTO> transLong(@RequestBody UrlTransDTO dto) {
        try {
            if (!StringUtils.hasText(dto.getShortUrl())) {
                return ResponseDTO.parmaError("短链接不可为空");
            }
            UrlTransDTO res = urlTransService.transLong(dto);
            return ResponseDTO.success(res);
        } catch (Exception e) {
            log.error("transLong error: {}, {}", dto, e.getMessage(), e);
            return ResponseDTO.error("根据短链获取长链失败：" + e.getMessage());
        }
    }
}
