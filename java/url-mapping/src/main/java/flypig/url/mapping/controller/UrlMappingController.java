package flypig.url.mapping.controller;


import flypig.url.mapping.bean.GenerateShortUrlRequest;
import flypig.url.mapping.core.UrlMapperContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url_mapping")
public class UrlMappingController {

    UrlMapperContext urlMapper = UrlMapperContext.getInstance();

    @PostMapping("/long2short")
    @ApiOperation(value = "长域名生成短域名", notes = "长域名生成短域名")
    public ResponseEntity<String> long2short(@RequestBody GenerateShortUrlRequest request) {
        return ResponseEntity.ok(urlMapper.long2short(request.getUrl()));
    }


    @GetMapping("/short2long/{shortUrl}")
    @ApiOperation(value = "短域名查询对应长域名", notes = "短域名查询对应长域名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短域名", required = true),
    })
    public ResponseEntity<String> short2long(@PathVariable(value = "shortUrl", required = true) String shorUrl) {
        return ResponseEntity.ok(urlMapper.short2long(shorUrl));
    }
}
