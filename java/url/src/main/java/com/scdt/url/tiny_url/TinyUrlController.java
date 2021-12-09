package com.scdt.url.tiny_url;

import com.scdt.url.tiny_url.model.TinyUrlId;
import com.scdt.url.tiny_url.representation.TinyUrlRepresentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "tiny_urls")
@Api("短链接服务控制器")
public class TinyUrlController {

    //region variables
    private final TinyUrlApplicationService applicationService;

    public TinyUrlController(TinyUrlApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    //endregion

    @ApiOperation(value = "创建短域名",notes = "接受创建命令参数，返回短域名id")
    @ResponseStatus(CREATED)
    @PostMapping
    public TinyUrlId createTinyUrl(@RequestBody @Valid CreateTinyUrlCommand command) {
       return applicationService.create(command);
    }
    @ApiOperation(value = "查询短域名",notes = "接受短域名id参数，返回短域名详情")
    @GetMapping("/{id}")
    public TinyUrlRepresentation byId(@PathVariable(name = "id") String id) {
       return applicationService.byId(id);
    }
}
