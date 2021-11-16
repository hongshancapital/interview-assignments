package pers.jenche.convertdomain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.jenche.convertdomain.core.ResponseResult;
import pers.jenche.convertdomain.core.SystemException;
import pers.jenche.convertdomain.dto.ResponseResultDTO;
import pers.jenche.convertdomain.service.DomainConvertService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 12:12
 * @description 转换使用的Controller
 */
@Api(tags = "URL转换")
//启用RestController
@RestController
public class ConvertDomainController extends BaseController {

    final DomainConvertService domainConvertService;

    @Autowired
    public ConvertDomainController(DomainConvertService domainConvertService) {
        this.domainConvertService = domainConvertService;
    }

    /**
     * 通过原始长URL生成段域名
     *
     * @param strOriginalUri {@link String} 原始长URL
     * @return {@link ResponseResultDTO} 数据接收实体
     * @throws SystemException 系统异常
     */
    @ApiOperation("转换成短URL")
    @PostMapping(value = "/toshort")
    public ResponseResultDTO convertToShortDomain(@RequestBody String strOriginalUri) throws SystemException {

        /*
         * 系统预置的短域名
         */
        String shortDomain = "http://a.cn/";

        /*
         * 系统预先配置好短域名 也可以让前端选择,使用加盐的方式将预置好的域名一起进行生成。
         * 后期可以循环监测使用的哪个域名。如果保存在数据库中可以使用枚举值保存，避免循环检索
         */
        String shortDomainResult = domainConvertService.toShort(strOriginalUri, shortDomain);

        //这里的shortDomain.concat(shortDomainResult)是将配置的域名加到短链上面
        return new ResponseResult<>(shortDomainResult).send();
    }

    @ApiOperation("通过短URL返回原始长URL")
    @PostMapping(value = "/tooriginal")
    public ResponseResultDTO getOriginalDomain(@ApiParam(value = "短URL", name = "strShortUri", required = true) @RequestBody String strShortUri) throws SystemException {
        String strOriginalResult = domainConvertService.toOriginal(strShortUri);
        return new ResponseResult<>(strOriginalResult).send();
    }
}
