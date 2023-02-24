package translation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import translation.model.ThePreTreeWrapBridge;
import translation.model.UrlMapBean;
import translation.util.Constants;
import translation.util.ShortLongUrlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 长短域名
 *
 * @author: hello
 * @since: 2023/2/21
 */

@RestController
@RequestMapping("url")
@Api("长短区域互换")
public class UrlTranslationController {
    Map<String, Object> map = new HashMap<String, Object>(Constants.INDEX_4);
    /**
     * 上面是采用map 存储,不建议 1个是容量不好控制
     * <br/>
     * 然后是 hash数组 容易hash碰撞与扩容 都会造成时间复杂度的消耗 即便为 O(1)+对数阶 也很慢
     * 限制：
     * 1.短域名长度最大为 8 个字符
     * 2.采用SpringBoot，集成Swagger API文档；
     * 3.JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
     * 4.映射数据存储在JVM内存即可，防止内存溢出；
     * 下面 满足第4点 和第1点; 我这边最打6位就可以保证
     * 64^6次方的数据,因为采用Trie的方式 内存占用不高;不会有OOM的情况
     * 当然如果我也提供了相关的 策略 UrlMapBean 有相关的 熟悉可以满足LRU或者LFU的 清理策略 store.clearClear()//清理缓存
     */
    @Autowired
    ThePreTreeWrapBridge store;
    private final static Logger log = LoggerFactory.getLogger(UrlTranslationController.class);

    public UrlTranslationController() {
        System.out.println("UrlTranslation - 构造器完成");
    }

    @ApiOperation(value = "将长域名变为短域名,并返回短域名,可以压测自己测试用例10W次完全OK_可用uuid模拟域名", httpMethod = "GET")
    @ApiImplicitParam(name = "url", paramType = "query", value = "长域名", dataType = "String")
    @GetMapping("/longToShort")
    public String longToShort(@RequestParam("url") String longUrl) {
        String shortUrl = null;
        log.info("longToShort param:longUrl:{}", longUrl);
        shortUrl = ShortLongUrlUtils.longToShort(longUrl, null);
        UrlMapBean urlMapBean = new UrlMapBean(longUrl, shortUrl);
        this.store.addOrSet(urlMapBean);
        shortUrl = urlMapBean.getShortUrl();
        return shortUrl;
    }

    /**
     * 前端调用,将之前后端返回的短链接传
     */
    @ApiOperation(value = "根据之前返回的短域名传入,在得到之前对应的长域名_采用PathVariable拼接url", httpMethod = "GET")
    @ApiImplicitParam(name = "url", paramType = "path", value = "短域名", dataType = "String")
    @RequestMapping("/shortToLong/{url}")
    @ResponseBody
    public String shortToLong(HttpServletRequest request, @PathVariable("url") String shortUrl) {
        log.info("shortToLong param:shortUrl:{}", shortUrl);
        UrlMapBean urlMapBean = this.store.get(shortUrl);
        String result = null;
        if (urlMapBean == null || urlMapBean.getLongUrl() == null) {
            return "无映射";
        }
        result = urlMapBean.getLongUrl();
        return result;
    }
}
