package controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import services.IChangeUrlService;
import utils.BaseResponse;
import utils.ResponseEnum;

/**转换URL长短链接的对外接口
 * @Description:
 * @Params
 * @Return
 * @Author Jun.Wong
 * @Date 2021/10/13 10:14
 */
public class ChangeUrlController {
    @Autowired
    IChangeUrlService iChangeUrlService;

    @ResponseBody
    @ApiOperation("根据长链返回短链的接口")
    @ApiImplicitParam(name = "url", value = "长链接地址")
    @RequestMapping(value = "/to_short_url",method = RequestMethod.POST)
    public BaseResponse<String> toShortUrl(@RequestParam(name = "url") String url){
        if (StringUtils.isEmpty(url)){
            return BaseResponse.error(ResponseEnum.PARAMETER_IS_NULL);
        }
        String value = iChangeUrlService.put(url);
        return BaseResponse.success(value);
    }

    @ResponseBody
    @ApiOperation("根据短链接返回长链接")
    @RequestMapping(value = "/get_url", method = RequestMethod.GET)
    @ApiImplicitParam(name = "url", value = "短链接参数")
    public BaseResponse<String> getUrl(@RequestParam(name = "url") String url){
        if (StringUtils.isEmpty(url)){
            return BaseResponse.error(ResponseEnum.PARAMETER_IS_NULL);
        }
        String value = iChangeUrlService.getValue(url);
        return StringUtils.isEmpty(value)?
                BaseResponse.error(ResponseEnum.DATA_IS_NULL):BaseResponse.success(value);
    }
}
