package javaassignment.lcg.controller.v1;

import io.swagger.annotations.ApiOperation;
import javaassignment.lcg.constants.Constants;
import javaassignment.lcg.controller.BaseController;
import javaassignment.lcg.entity.Result;
import javaassignment.lcg.utils.ShortUrlUtils;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 栾晨光
 * @Date: 2021-03-18 18:55
 * @Email 10136547@qq.com
 * @Description: 短地址控制器
 */
@RestController
@RequestMapping("api/v1/url")
public class UrlController extends BaseController {

    private static final Logger log = (Logger) LoggerFactory.getLogger(UrlController.class);

    @Resource(name = "dB0")
    private RedisTemplate<Object, Object> dB0;

    /**
     *
     * @param url 长地址
     * @return 短地址封装实体
     * @throws Exception 异常抛出
     */
    @ApiOperation(value="长地址转短地址",notes="长地址转短地址")
    @GetMapping("longtoshort")
    public Result longtoshort(String url) throws Exception {

        //验证地址
        UrlValidator defaultValidator = new UrlValidator();
        if (!defaultValidator.isValid(url)) {
            return setResultError("非法地址");
        }

        //转换地址
        String shortUrl = ShortUrlUtils.shortUrl(url);

        //存储地址,超时时间根据业务情况决定是否设置
        dB0.opsForValue().set(Constants.SHORT_URL + shortUrl,url);

        //返回地址
        return setResultSuccess(shortUrl);
    }

    /**
     *
     * @param shortUrl 短地址
     * @param response 响应
     * @throws IOException 异常抛出
     */
    @ApiOperation(value="短地址转向",notes="短地址转向")
    @GetMapping(value = "/{shortUrl}")
    public void getInvoiceUrl(@PathVariable(required = false) String shortUrl,
                              HttpServletResponse response) throws IOException {

        //读取地址
        String url = (String) dB0.opsForValue().get(Constants.SHORT_URL + shortUrl);

        //跳转地址
        if(url != null)
        {
            response.sendRedirect(url);
        }
        else
        {
            response.sendRedirect("http//:www.过期或不存在应转向得地址.com");
        }

    }

}
